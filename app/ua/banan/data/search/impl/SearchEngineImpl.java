package ua.banan.data.search.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.text.WordUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;

import com.google.common.base.Predicate;

import ua.banan.data.model.City;
import ua.banan.data.model.Comment;
import ua.banan.data.model.Country;
import ua.banan.data.model.Pair;
import ua.banan.data.model.Photo;
import ua.banan.data.model.Tour;
import ua.banan.data.model.TourFull;
import ua.banan.data.model.TourOp;
import ua.banan.data.model.interfaces.Named;
import ua.banan.data.model.presentation.CountPresentation;
import ua.banan.data.search.SearchEngine;
import ua.banan.web.PageInfo;
import ua.banan.web.Routes;

public class SearchEngineImpl implements SearchEngine
{	
	private static final int TOP_COUNTRIES_COUNT = 9;
	private static final int TOP_TOUROPS_COUNT   = 5;
	
	private static final String imgInHtmlPathPathStart 	= "/assets/images/bnnPht/";
	private static final String pathStart 			    = "public" + File.separator + "images" + File.separator + "bnnPht" + File.separator;
	
	private static Connection 		  connToToursDb;
	
	private Map<Integer, List<TourFull>>  countriesToursMap 	= new HashMap<Integer, List<TourFull>>();
	private Map<Integer, List<TourFull>>  touropsToursMap 		= new HashMap<Integer, List<TourFull>>();
	private List<TourFull> 				  nearestTours 			= new ArrayList<>();
	private List<TourFull> 				  seaTours 				= new ArrayList<>();
	private List<TourFull> 				  busTours 				= new ArrayList<>();
		
	private int daysBeforeLeave; //used to find nearest tours	
	
	
	private Map<Integer, List<Integer>> 		 continentCountriesMap 		= new HashMap<Integer, List<Integer>>();
	
	private Map<Integer, Country>				 countriesMap   			= new HashMap<>();
	private Map<Integer, City>				 	 citiesMap   				= new HashMap<>();
	private Map<Integer, TourOp>			 	 tourOpsMap   				= new HashMap<>();
	
	private List<CountPresentation> topCountries							= new ArrayList<CountPresentation>();
	private List<CountPresentation> topTourOps   							= new ArrayList<CountPresentation>();	
	
	private Map<Integer, PageInfo>				 pageInfoTourOps   			= new HashMap<>();
	private Map<Integer, PageInfo>				 pageInfoCountries 			= new HashMap<>();
	private Map<Integer, PageInfo>				 pageInfoCities   			= new HashMap<>();
	
	
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	private Object lock = new Object();
	private Random random = new Random(new java.util.Date().getTime());
		
	public SearchEngineImpl(int daysBeforeLeave)
	{
		this.daysBeforeLeave = daysBeforeLeave;
		
		fillContinentCountriesMap();
		fillCountriesMap();
		fillCitiesMapAndCountryCityMap();
		fillTourOpsMap();					
		fillPageInfoMaps();
				
		scheduler.scheduleAtFixedRate(new Runnable()
		{		
			@Override
			public void run()
			{				
				toursUpdate();					        			        			    			        		        		
			}
		}, 0, 2, TimeUnit.HOURS);
	}		
	
	private void fillPageInfoMap(Map<Integer, PageInfo> map, String statement)
	{		
		try 
        {                    	                        		        		           
			PreparedStatement select = connToToursDb.prepareStatement(statement); 			           
			ResultSet result = select.executeQuery();
            
            while (result.next())
            { 
            	int id = result.getInt(1);     
            	
            	PageInfo pageInfo = new PageInfo();
        		
        		pageInfo.setTitle(result.getString(2));
        		pageInfo.setDescription(result.getString(3));
        		pageInfo.setKeywords(result.getString(4));
        		pageInfo.setUserTitle(result.getString(5));
            	
            	map.put(id, pageInfo);
            }            	
        } 
        catch (Exception e) 
        {
            System.err.println("SQLException: " + e.getMessage());            
        }	
	}		
	
	private void fillPageInfoMaps() 
	{
		fillPageInfoMap(pageInfoCities,    "SELECT CITY_ID, 	TITLE, DESCRIPTION, KEYWORDS, USER_TITLE FROM PAGE_INFO_CITIES");
		fillPageInfoMap(pageInfoCountries, "SELECT COUNTRY_ID,  TITLE, DESCRIPTION, KEYWORDS, USER_TITLE FROM PAGE_INFO_COUNTRIES");
		fillPageInfoMap(pageInfoTourOps,   "SELECT OPERATOR_ID, TITLE, DESCRIPTION, KEYWORDS, USER_TITLE FROM PAGE_INFO_TOUR_OPERATORS");		
	}
		
	private void fillTourOpsMap() 
	{
		try 
        {                    	
            PreparedStatement select;
            		        		            	
            select = connToToursDb.prepareStatement("SELECT SOURCE_ID, SOURCE_NAME, DESCRIPTION, IMG_URL, URL FROM SOURCE ");	
            			            
            ResultSet result = select.executeQuery();

            while (result.next())
            { 
            	TourOp tourOp = new TourOp();
            	
            	tourOp.setId(result.getInt(1));
            	tourOp.setName(result.getString(2));            	
            	tourOp.setDescription(result.getString(3));
            	tourOp.setPhotoUrl(result.getString(4));
            	tourOp.setUrl(result.getString(5));   
            	
            	tourOpsMap.put(tourOp.getId(), tourOp);
            }                                                                 
        } 
        catch (Exception e) 
        {
            System.err.println("SQLException: " + e.getMessage());            
        }	
	}

	private void toursUpdate()
	{		
      synchronized (lock)
	  {
//			System.out.println("Tours update starts.... ");
			
			Map<Integer, List<TourFull>> countriesToursMapNew = new HashMap<Integer, List<TourFull>>();
			Map<Integer, List<TourFull>> touropsToursMapNew = new HashMap<Integer, List<TourFull>>();
			List<TourFull> 				 nearestToursNew = new ArrayList<>();
			List<TourFull> 				 seaToursNew = new ArrayList<>();
			List<TourFull> 				 busToursNew = new ArrayList<>();
							
	    	List<Tour> notFullTours = selectAllTours(); 
	    	
	    	System.out.println("all tours count : " + notFullTours.size());
	    	
	    	Map<Integer, List<Integer>> toursToCountries = selectAllToursToCountries();
	    	
//	    	System.out.println("toursToCountries size: " + toursToCountries.size());
	    	
	    	Map<Integer, List<Integer>> toursToCities = selectAllToursToCities();
	    	
//	    	System.out.println("toursToCities size: " + toursToCities.size());

	    	
	    	Map<Integer, Pair<String, Integer>> hotels = selectAllHotels();
	    	
//	    	System.out.println("hotels size: " + hotels.size());
    	
	    	if (notFullTours != null && !notFullTours.isEmpty() && toursToCountries != null && !toursToCountries.isEmpty())
	    	{
	    		for(Tour tour : notFullTours)
	    		{
//	    			System.out.println("tour: " + tour.toString());
	    			
	    			TourFull tf = new TourFull(tour);
	    				    			
	    			tf.setDaysBeforeLeave(Days.daysBetween(new DateTime(new java.util.Date()), new DateTime(tour.getFlightDate())).getDays());
	    			
	    			tf.setCityIds(toursToCities.get(tf.getId()));
	    			tf.setCountryIds(toursToCountries.get(tf.getId()));
	    			
	    			tf.setCityNames(findInMap(citiesMap, tf.getCityIds()));
	    			tf.setCountryNames(findInMap(countriesMap, tf.getCountryIds()));
	    			
	    			TourOp to = tourOpsMap.get(tour.getSourceId());
	    			tf.setSourceName(to != null ? to.getUrl() : null);
	 	
	    			Pair<String, Integer> hotelInfo = hotels.get(tf.getHotelId());
	    			if (hotelInfo != null)
	    			{
	    				tf.setHotelName(hotelInfo.getFirst());
	    				tf.setStars(hotelInfo.getSecond());
	    			}
	    			
//	    			System.out.println("Set photo starts");
	    			tf.setPhoto(findPhoto(tf.getCountryIds(), tf.getCityIds()));
//	    			System.out.println("Set photo ends");
	    			
	    			addToMap(countriesToursMapNew, tf.getCountryIds(), tf);

	    			addToMap(touropsToursMapNew, Arrays.asList(new Integer[]{tour.getSourceId()}), tf);   
	    			
	    			Calendar c = Calendar.getInstance();
	    			c.add(Calendar.DAY_OF_YEAR, daysBeforeLeave);
	    			
	    			if (tf.getFlightDate().before(c.getTime()))
	    			{
	    				nearestToursNew.add(tf);
	    			}
	    			
	    			//in turkey, egypt
	    			if ((tf.getCountryIds().contains(109) || tf.getCountryIds().contains(61)) && tf.getCountryIds().size() == 1)
	    			{
	    				seaToursNew.add(tf);
	    			}
	    			else if ((tf.getCityIds() != null && tf.getCityIds().size() > 2) || (tf.getCountryIds().size() > 1) || tf.getCountryIds().contains(0))
	    			{
	    				busToursNew.add(tf);
	    			}    	
	    			
//	    			System.out.println("tour processing edned. Index: " + notFullTours.indexOf(tour));
	    		}    
	    		
//		    	System.out.println("Sorting starts");	    		
    		
	    		sortMapOfLists(countriesToursMapNew);
	    		sortMapOfLists(touropsToursMapNew);
	    		
	    		safeListSort(nearestToursNew);	
	    		safeListSort(seaToursNew);	    		
	    		safeListSort(busToursNew);	    
	    		
//	    		System.out.println("Sorting ended. Top countries starts");
	    		
	    		topCountries = createTopList(countriesToursMapNew, countriesMap, Routes.countriesRoutes, TOP_COUNTRIES_COUNT, false);
	    		//topTourOps   = createTopList(touropsToursMapNew,   tourOpsMap,   Routes.tourOpsRoutes,   TOP_TOUROPS_COUNT, true);	    		
	    		
//	    		System.out.println("Top countries ended. TO starts");
	    		
	    		topTourOps.clear();
	    		updateTopTourOps(topTourOps, touropsToursMapNew);
	    		
//	    		System.out.println("TO ended. Tour end strtas");
	    		
		        countriesToursMap = countriesToursMapNew;
		    	touropsToursMap = touropsToursMapNew;
		    	nearestTours = nearestToursNew;
		    	seaTours = seaToursNew;
		    	busTours = busToursNew;			        			        			       
			}	
	    	
	    	
	    	System.out.println("Tours update's been ended.... ");
    	}		       		
	}	

	private CountPresentation createTourOpCount(Integer id, Map<Integer, List<TourFull>> toursMap)
	{
		CountPresentation cp = new CountPresentation();
		
		List l = toursMap.get(id);
		cp.setCount(l != null ? l.size() : 0);
		
		TourOp named = tourOpsMap.get(id);
		cp.setName(named != null ? named.getName() : null);
		
		cp.setPhotoImgUrl(named.getPhotoUrl());
		
		cp.setUrl(Routes.tourOpsRoutes.get(id));
		
		return cp;
	}
	
	private void updateTopTourOps(List<CountPresentation> topTourOps, Map<Integer, List<TourFull>> toursMap) 
	{
		if (topTourOps != null)
		{
			topTourOps.clear();
		}
		else
		{
			topTourOps = new ArrayList<CountPresentation>();
		}			
		
		topTourOps.add(createTourOpCount(1, toursMap));
		topTourOps.add(createTourOpCount(5, toursMap));
		topTourOps.add(createTourOpCount(8, toursMap));
		topTourOps.add(createTourOpCount(11, toursMap));
		topTourOps.add(createTourOpCount(12, toursMap));
	}

	private <T extends Named> List<CountPresentation> createTopList(Map<Integer, List<TourFull>> map, Map<Integer, T> namesMap, Map<Integer, String> routesMap, int topCount, boolean isTourOp)
	{
		if (map != null && !map.isEmpty())
		{
			List<CountPresentation> res = new ArrayList<CountPresentation>();
			
			List<Pair<Integer, Integer>> counts = new ArrayList<Pair<Integer,Integer>>();
			for(Integer id : map.keySet())
			{
				List<TourFull> tfs = map.get(id);
				
				counts.add(new Pair<Integer, Integer>(id, tfs != null ? tfs.size() : 0));
			}
			
			Collections.sort(counts, new Comparator<Pair<Integer, Integer>>()
			{
				@Override
				public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2)
				{					
					return -o1.getSecond().compareTo(o2.getSecond());
				}
					
			});
			
			
			for(int i = 0; i < Math.min(map.size(), topCount); i++)
			{
				Pair<Integer, Integer> count = counts.get(i);
				CountPresentation cp = new CountPresentation();
				
				Integer id = count.getFirst();
								
				cp.setCount(count.getSecond());
				
				T named = namesMap.get(id);
				cp.setName(named != null ? named.getName() : null);
				
				cp.setPhotoImgUrl(isTourOp ? 
						((TourOp) named).getPhotoUrl() :
						findPhoto(Arrays.asList(new Integer[] {id}), null).getUrl()
				);
				
				cp.setUrl(routesMap.get(id));
				
				res.add(cp);
			}
								
			return res;
		}
		
		return null;
	}
	
	private void safeListSort(List<TourFull> list) 
	{		
		if (list != null && !list.isEmpty())
		{
			Collections.sort(list);
		}			
	}
	
	private void sortMapOfLists(Map<Integer, List<TourFull>> map) 
	{
		if (map != null && !map.isEmpty())
		{
			for(List<TourFull> list : map.values())
			{
				safeListSort(list);
			}
		}
	}

	private void addToMap(Map<Integer, List<TourFull>> map, List<Integer> keys, TourFull tf)
	{
		if (map != null && tf != null && keys != null && !keys.isEmpty())
		{
			for(Integer key : keys)
			{
				List<TourFull> list = map.get(key);
				if (list == null)
				{
					list = new ArrayList<TourFull>();
					map.put(key, list);
				}
				
				list.add(tf);
			}
		}
	}

	private Map<Integer, Pair<String, Integer>> selectAllHotels()
	{
		try 
        {            
			//Map<HotelId, Pair<Name, Stars>>
			Map<Integer, Pair<String, Integer>> res = new HashMap<>();
        	
            PreparedStatement select;
            		        		            	
            select = connToToursDb.prepareStatement("SELECT HOTEL_ID, HOTEL_NAME, STARS FROM HOTEL");	
            			            
            ResultSet result = select.executeQuery();

            while (result.next())
            { 
            	res.put(result.getInt(1), new Pair<String, Integer>(result.getString(2), result.getInt(3)));            	
            }                                                     
            
            return res;                        
        } 
        catch (Exception e) 
        {
            System.err.println("SQLException: " + e.getMessage());
            
            return null;
        }		
	}

	private <T extends Named> List<String> findInMap(Map<Integer, T> namesMap, List<Integer> ids)
	{
		if (namesMap != null && !namesMap.isEmpty() && ids != null && !ids.isEmpty())
		{
			List<String> res = new ArrayList<String>(); 
			for(Integer id : ids)
			{
				T obj = namesMap.get(id);
				String name = obj != null ? obj.getName() : null;
				if (name != null)
				{
					res.add(name);
				}
			}
			
			return res;
		}
		
		return null;
	}

	private Map<Integer, List<Integer>> selectAllToursToCities()
	{
		try 
        {            
			//Map<TourId, List<citiesIds>>
			Map<Integer, List<Integer>> res = new HashMap<>();
        	
            PreparedStatement select;
            		        		            	
            select = connToToursDb.prepareStatement("SELECT TOUR_ID, CITY_ID FROM TOURS_TO_CITIES");	
            			            
            ResultSet result = select.executeQuery();

            while (result.next()) 
            { 
            	int tourId = result.getInt(1);
                List<Integer> cities = res.get(tourId);
                if (cities == null)
                {
                	cities = new ArrayList<Integer>();
                	res.put(tourId, cities);
                }
                
                cities.add(result.getInt(2));
            }                                                     
            
            return res;                        
        } 
        catch (Exception e) 
        {
            System.err.println("SQLException: " + e.getMessage());
            
            return null;
        }
	}	
	
	private Map<Integer, List<Integer>> selectAllToursToCountries()
	{
//		System.out.println("selectAllToursToCountries starts.... ");
		
		try 
        {            
			//Map<TourId, List<CountriesIds>>
			Map<Integer, List<Integer>> res = new HashMap<>();
        	
            PreparedStatement select;
            		        		            	
            select = connToToursDb.prepareStatement("SELECT TOUR_ID, COUNTRY_ID FROM TOURS_TO_COUNTRIES");	
            			            
            ResultSet result = select.executeQuery();

            while (result.next()) 
            { 
            	int tourId = result.getInt(1);
                List<Integer> countries = res.get(tourId);
                if (countries == null)
                {
                	countries = new ArrayList<Integer>();
                	res.put(tourId, countries);
                }
                
                countries.add(result.getInt(2));
            }                                                     
            
            return res;                        
        } 
        catch (Exception e) 
        {
            System.err.println("SQLException: " + e.getMessage());
            
            return null;
        }
	}
	
	
	private List<Tour> selectAllTours()
	{
//		System.out.println("selectAllTours starts.... ");		
		try 
        {            
        	List<Tour> notFullTours = new ArrayList<Tour>();
        	
            PreparedStatement select;
            		        		            		           
            select = connToToursDb.prepareStatement("SELECT URL, NUTRITION, ROOM_TYPE, FLIGHT_DATE, PRICE, DURATION, DESCRIPTION, HOTEL_ID, TOUR_ID, SOURCE_ID, DEPART_CITY FROM TOUR");	
                        			            
            ResultSet result = select.executeQuery();

            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_YEAR, 1);
            java.util.Date tomorrow = c.getTime();
            
            while (result.next()) 
            { 
                Tour t = new Tour();
                t.setUrl(result.getString(1));
                t.setFeedPlan(result.getString(2));
                t.setRoomType(result.getString(3));
                t.setFlightDateDate(result.getDate(4));
                t.setPrice(result.getInt(5));
                t.setNightsCount(result.getInt(6));			                			                			              
                t.setDescription(result.getString(7));
                t.setHotelId(result.getInt(8));
                t.setId(result.getInt(9));	                
                t.setSourceId(result.getInt(10));
                
                String departCity = result.getString(11);
                if (departCity != null && departCity.equalsIgnoreCase("КИЕВ"))
                {
                	t.setDepartCityId(49713);
                } 
                else if (departCity != null && departCity.equalsIgnoreCase("ХАРЬКОВ"))
                {                	
                	t.setDepartCityId(54521);
                }
                else if (departCity != null && departCity.equalsIgnoreCase("ЗАПОРОЖЬЕ"))
                {                	
                	t.setDepartCityId(52182);
                }
                else if (departCity != null && departCity.equalsIgnoreCase("ДОНЕЦК"))
                {                	
                	t.setDepartCityId(52123);
                }
                else if (departCity != null && departCity.equalsIgnoreCase("ЛЬВОВ"))
                {                	
                	t.setDepartCityId(50345);
                }
                
                if (t.getFlightDate() != null && t.getFlightDate().after(tomorrow))
                {
                	notFullTours.add(t);
                }
            }                                                     
            
            return notFullTours;                        
        } 
        catch (Exception e) 
        {
            System.err.println("SQLException: " + e.getMessage());
            
            return Collections.EMPTY_LIST;
        }			  
	}

	private void fillCitiesMapAndCountryCityMap()
	{
		try 
        {            
            PreparedStatement select;
            
            String sqlString = "SELECT CITY_ID, CITY_NAME_RU, CITY_NAME_EN, COUNTRY_ID, DESCRIPTION FROM CITY";
            
            select = connToToursDb.prepareStatement(sqlString);
                        
            ResultSet result = select.executeQuery();

            while (result.next()) 
            {                                
                int cityId = result.getInt(1);
                                                                          
                City city = new City();
                
                city.setId(cityId);
                city.setName(normalizeGeographicalName(result.getString(2), false));
                city.setNameEn(result.getString(3));
                city.setCountryId(result.getInt(4));
                city.setDescription(result.getString(5));
                
                citiesMap.put(cityId, city);
            }            
        } 
        catch (Exception e) 
        {
            System.err.println("SQLException: " + e.getMessage());
        } 
	}



	private String normalizeGeographicalName(String name, boolean isCountry)
	{
		if (name != null && !name.isEmpty())
        {			
			if (!(isCountry && name.length() <= 3))
			{
				name = WordUtils.capitalizeFully(name);
				
				for(int i = 0; i < name.length(); i++)
				{
					if (name.charAt(i) == '-' && i != (name.length() - 1)) 
					{						
						name = name.replace("-" + name.charAt(i + 1), "-" + Character.toUpperCase(name.charAt(i + 1)));						
					}
					else if (name.charAt(i) == '\'' && i != (name.length() - 1)) 
					{						
						name = name.replace("\'" + name.charAt(i + 1), "\'" + Character.toUpperCase(name.charAt(i + 1)));						
					}
				}							
			}
			
			return name;
        }     
		
		return "";
	}

	private void fillCountriesMap()
	{        
        try 
        {            
            PreparedStatement select;
            
            String sqlString = "SELECT COUNTRY_ID, COUNTRY_NAME_RU, COUNTRY_NAME_EN, DESCRIPTION, CONTINENT_ID FROM COUNTRY";
            
            select = connToToursDb.prepareStatement(sqlString);
                        
            ResultSet result = select.executeQuery();

            while (result.next()) 
            {                                
                int countryId = result.getInt(1);
                
                Country country = new Country();
                
                country.setId(countryId);
                country.setName(normalizeGeographicalName(result.getString(2), true));
                country.setNameEn(result.getString(3));
                country.setDescription(result.getString(4));
                country.setContinentId(result.getShort(5));
                
                countriesMap.put(countryId, country);
            }            
        } 
        catch (Exception e) 
        {
            System.err.println("SQLException: " + e.getMessage());
        } 
	}



	private void fillContinentCountriesMap()
	{
		continentCountriesMap.put(1, readCountriesIdsByContinent(1));
		continentCountriesMap.put(2, readCountriesIdsByContinent(2));
		continentCountriesMap.put(3, readCountriesIdsByContinent(3));
		continentCountriesMap.put(4, readCountriesIdsByContinent(4));
		continentCountriesMap.put(5, readCountriesIdsByContinent(5));
		continentCountriesMap.put(6, readCountriesIdsByContinent(6));
	}



	private List<Integer> readCountriesIdsByContinent(int continentId)
	{
		List<Integer> res = new ArrayList<Integer>();
        
        try 
        {            
            PreparedStatement select;
            
            String sqlString = "SELECT COUNTRY_ID FROM COUNTRY WHERE CONTINENT_ID = ?";
            
            select = connToToursDb.prepareStatement(sqlString);
                        
            select.setInt(1, continentId);               
            ResultSet result = select.executeQuery();

            while (result.next()) 
            {                                
                res.add(result.getInt(1));
            }            
        } 
        catch (Exception e) 
        {
            System.err.println("SQLException: " + e.getMessage());
        }
 
        return res;
	}

	public static Connection getConnToToursDb()
	{
		return connToToursDb;
	}
	public static void setConnToToursDb(Connection connToToursDb)
	{
		SearchEngineImpl.connToToursDb = connToToursDb;
	}

	@Override
	public List<TourFull> getTours(List<Integer> countries, Integer moneyEnd)
	{
    	//250 - ASIA (ID == 2 in DB); 251 - North America (ID == 4 in DB); 252 - South America (ID == 6 in DB); 253 - Africa (ID == 1 in DB); 134 - Europe (ID == 3 in DB); 255 - Oceania (ID == 5 in DB)
				
		List<Integer> countriesCopy = null;
		if (countries != null && !countries.isEmpty())
		{
			countriesCopy = new ArrayList<Integer>();
			countriesCopy.addAll(countries);
		}
		
		
		if (countries != null && countries.contains(250))
		{
			countriesCopy.addAll(continentCountriesMap.get(2));
		}
		else if (countries != null && countries.contains(251))
		{
			countriesCopy.addAll(continentCountriesMap.get(4));
		}
		else if (countries != null && countries.contains(252))
		{
			countriesCopy.addAll(continentCountriesMap.get(6));
		}
		else if (countries != null && countries.contains(253))
		{
			countriesCopy.addAll(continentCountriesMap.get(1));
		}
		else if (countries != null && countries.contains(134))
		{
			countriesCopy.addAll(continentCountriesMap.get(3));
		}
		else if (countries != null && countries.contains(255))
		{
			countriesCopy.addAll(continentCountriesMap.get(5));
		}
		
		Map<Integer, Tour> tours = queryTours(countriesCopy, null, moneyEnd, null, null);
		if (tours != null && !tours.isEmpty())
		{
			List<TourFull> res = new ArrayList<TourFull>();
			
			for (Tour t : tours.values())
			{
				TourFull tf = new  TourFull(t);
				
				fillTourFull(tf);
								
				//TODO add photo search by all countries and cities, not only by first one
				tf.setPhoto(findPhoto(tf.getCountryIds(), tf.getCityIds()));
//				System.out.println(tf.getImgUrl());
				
				res.add(tf);
			}
				
			return res;
		}
		
		return null;
	}
		

	

	private void fillTourFull(TourFull tf)
	{
		if (tf != null)
		{
			int tourId = tf.getId();
			
			//fill countries
			List<Integer> countriesIds = new ArrayList<Integer>();
	        
	        try 
	        {            
	            PreparedStatement select;
	            
	            String sqlString = "SELECT COUNTRY_ID FROM TOURS_TO_COUNTRIES WHERE TOUR_ID = ?";
	            
	            select = connToToursDb.prepareStatement(sqlString);
	                        
	            select.setInt(1, tourId);               
	            ResultSet result = select.executeQuery();

	            while (result.next()) 
	            {                                
	                countriesIds.add(result.getInt(1));
	            }            
	        } 
	        catch (Exception e) 
	        {
	            System.err.println("SQLException: " + e.getMessage());
	        }
	 
	        if (!countriesIds.isEmpty()) 
	        {
	        	tf.setCountryIds(countriesIds);	        	
	        	List<String> countries = new ArrayList<String>();
	        	for (Integer countryId : countriesIds)
	        	{
	        		Country country = countriesMap.get(countryId);
	        		
	        		countries.add(country != null ? country.getName() : null);
	        	}
	        	
	        	tf.setCountryNames(countries);
	        }
			
	        
	        //fill cities
	        List<Integer> citiesIds = new ArrayList<Integer>();
	        
	        try 
	        {            
	            PreparedStatement select;
	            
	            String sqlString = "SELECT CITY_ID FROM TOURS_TO_CITIES WHERE TOUR_ID = ?";
	            
	            select = connToToursDb.prepareStatement(sqlString);
	                        
	            select.setInt(1, tourId);               
	            ResultSet result = select.executeQuery();

	            while (result.next()) 
	            {                                
	                citiesIds.add(result.getInt(1));
	            }            
	        } 
	        catch (Exception e) 
	        {
	            System.err.println("SQLException: " + e.getMessage());
	        }
	 
	        if (!citiesIds.isEmpty()) 
	        {
	        	tf.setCityIds(citiesIds);
	        	List<String> cities = new ArrayList<String>();
	        	for (Integer cityId : citiesIds)
	        	{
	        		City city = citiesMap.get(cityId);
	        		
	        		cities.add(city != null ? city.getName() : null);
	        	}
	        	
	        	tf.setCityNames(cities);
	        }
		}
	}
	
	private Photo findPhoto(List<Integer> countryIds, List<Integer> cityIds)	
	{			
		List<Photo> possibleResults = new ArrayList<Photo>();
		
		if (cityIds != null && !cityIds.isEmpty())
		{
			for (Integer cityId : cityIds)
			{				
				City pair = citiesMap.get(cityId);
				if (pair != null)
				{
					String countryNameEn = countriesMap.get(pair.getCountryId()).getNameEn();
					String cityNameEn = pair.getNameEn();
					
//					System.out.println(countryNameEn);
//					System.out.println(cityNameEn);
					
					if (countryNameEn != null && !countryNameEn.isEmpty() && cityNameEn != null && !cityNameEn.isEmpty())
					{
						String path = pathStart + countryNameEn + File.separator + cityNameEn;
						String imgInHtmlPath = imgInHtmlPathPathStart + countryNameEn + "/" + cityNameEn + "/";
//						System.out.println(imgInHtmlPath);
						File photoDir = new File(path);
						if (photoDir.exists())
						{
							File[] photos = photoDir.listFiles();
							if (photos != null && photos.length != 0)
							{
								for (File photo : photos)
								{				
									String name = photo.getName();
									if (name != null && !name.isEmpty())
									{
										BufferedImage bimg;
										try
										{
//											System.out.println("bimg read starts " + name);
											bimg = ImageIO.read(photo);
											
											int width          = bimg.getWidth();
											int height         = bimg.getHeight();
																					
											possibleResults.add(new Photo(imgInHtmlPath + name, height, width));
										}
										catch (IOException ignore)
										{
//											System.out.println("bimg read err " + ignore.getMessage().toString());
											continue;
										}
									}
								}
							}
						}
					}
				}				
			}
		}

		if (possibleResults.isEmpty() && countryIds != null && !countryIds.isEmpty())
		{
			for (Integer countryId : countryIds)
			{
				String countryNameEn = countriesMap.get(countryId).getNameEn();
//				System.out.println(countryNameEn);
				
				if (countryNameEn != null && !countryNameEn.isEmpty())
				{
					countryNameEn = countryNameEn.replace(" ", "_");
					String path = pathStart + countryNameEn;
					String imgInHtmlPath = imgInHtmlPathPathStart + countryNameEn + "/";
					File photoDir = new File(path);
					if (photoDir.exists())
					{
						File[] photos = photoDir.listFiles();
						if (photos != null && photos.length != 0)
						{
							for (File photo : photos)
							{			
								if (photo.isFile()) 
								{
									String name = photo.getName();
									if (name != null && !name.isEmpty())
									{
										BufferedImage bimg;
										try
										{
											bimg = ImageIO.read(photo);
											
											int width          = bimg.getWidth();
											int height         = bimg.getHeight();
																					
											possibleResults.add(new Photo(imgInHtmlPath + name, height, width));
										}
										catch (IOException ignore)
										{
											continue;
										}										
									}
								}
							}
						}
					}
				}
			}
		}
		
		//choose no photo or random from possible results
		return possibleResults.isEmpty() ? selectNoPhoto() : possibleResults.get(random.nextInt(possibleResults.size()));
	}

	private Photo selectNoPhoto()
	{
		return new Photo(imgInHtmlPathPathStart + "def.jpg", 480, 360);
	}

	public static Map<Integer, Tour> queryTours(List<Integer> countryIds, Integer priceFrom, Integer priceTo, Date dateFrom, Date dateTill)
    {                
        Map<Integer, Tour> res = new HashMap<>();
        
        try 
        {            
            PreparedStatement select;
            
            String sqlString = "SELECT * FROM TOUR INNER JOIN TOURS_TO_COUNTRIES ON TOUR.TOUR_ID=TOURS_TO_COUNTRIES.TOUR_ID ";
        
            boolean somethingIsInParams = (countryIds != null && !countryIds.isEmpty()) || (priceFrom != null && priceFrom.compareTo(0) > 0) || (priceTo != null && priceTo.compareTo(0) > 0) || dateFrom != null || dateTill != null; 
            if (somethingIsInParams)
            {
                sqlString += " WHERE ";
                
                int countryParameterFirstIndex  = -1;
                int priceFromParameterIndex     = -1;
                int priceToParameterIndex       = -1;
                int dateFromParameterIndex      = -1;
                int dateTillParameterIndex      = -1;

                int currentIndex = 1;

                if (countryIds != null && !countryIds.isEmpty())
                {                    
                    sqlString += "(";
                    countryParameterFirstIndex = currentIndex;
                    for (int i = 0; i < countryIds.size(); i++)
                    {                    
                        sqlString += " COUNTRY_ID = ? ";
                        if ((i + 1) != countryIds.size())
                        {
                            sqlString += " OR ";
                        }                    
                        currentIndex++;
                    }
                    
                    sqlString += ") ";
                }                                  

                if (priceFrom != null && priceFrom.compareTo(0) > 0)
                {
                    if (countryParameterFirstIndex != -1)
                    {
                        sqlString += " AND ";
                    }
                    
                    sqlString += " PRICE > ? ";
                    priceFromParameterIndex = currentIndex;
                    currentIndex++;
                }

                if (priceTo != null && priceTo.compareTo(0) > 0)
                {
                    if (countryParameterFirstIndex != -1 || priceFromParameterIndex != -1)
                    {
                        sqlString += " AND ";
                    }
                    
                    sqlString += " PRICE < ? ";
                    priceToParameterIndex = currentIndex;
                    currentIndex++;
                }

                if (dateFrom != null)
                {
                    if (countryParameterFirstIndex != -1 || priceFromParameterIndex != -1 || priceToParameterIndex != -1)
                    {
                        sqlString += " AND ";
                    }
                    
                    sqlString += " FLIGHT_DATE > ? ";
                    dateFromParameterIndex = currentIndex;
                    currentIndex++;
                }

                if (dateTill != null)
                {
                    if (countryParameterFirstIndex != -1 || priceFromParameterIndex != -1 || priceToParameterIndex != -1 || dateFromParameterIndex != -1)
                    {
                        sqlString += " AND ";
                    }
                    
                    sqlString += " FLIGHT_DATE < ? ";
                    dateTillParameterIndex = currentIndex;
                    currentIndex++;
                }
            
                select = connToToursDb.prepareStatement(sqlString);
                       
                if (countryParameterFirstIndex > 0)
                {                    
                    for (int i = countryParameterFirstIndex; i < countryParameterFirstIndex + countryIds.size(); i++)
                    {
                        select.setInt(i, countryIds.get(i - countryParameterFirstIndex));
                    }
                }

                if (priceFromParameterIndex > 0)
                {
                    select.setInt(priceFromParameterIndex, priceFrom);
                }

                if (priceToParameterIndex > 0)
                {
                    select.setInt(priceToParameterIndex, priceTo);
                }

                if (dateFromParameterIndex > 0)
                {
                    select.setDate(dateFromParameterIndex, dateFrom);
                }

                if (dateTillParameterIndex > 0)
                {
                    select.setDate(dateFromParameterIndex, dateTill);
                }
            }
            else
            {
                select = connToToursDb.prepareStatement(sqlString);
            }
            
            ResultSet result = select.executeQuery();

            while (result.next()) 
            { 
                Tour t = new Tour();
                t.setUrl(result.getString(1));
                t.setFeedPlan(result.getString(2));
                t.setRoomType(result.getString(3));
                t.setFlightDateDate(result.getDate(4));
                t.setPrice(result.getInt(5));
                t.setNightsCount(result.getInt(6));
                t.setDescription(result.getString(7));
                t.setHotelId(result.getInt(8));
                t.setId(result.getInt(9));
                t.setDepartCityId(result.getInt(10));                
                t.setCountryId(result.getInt(12));
                
                res.put(t.getId(), t);
            }            
        } 
        catch (Exception e) 
        {
            System.err.println("SQLException: " + e.getMessage());
        }
 
        return res;
    }

	@Override
	public List<TourFull> getTours(Integer countryId, Integer cityId) 
	{
		synchronized (lock)
		{
			if (countryId != null)
			{
				List<TourFull> countriesTours = countriesToursMap.get(countryId);
				
				if (cityId != null)
				{
					List<TourFull> res = new ArrayList<TourFull>();
					
					for (TourFull tourFull : countriesTours)
					{
						List<Integer> citiesIds = tourFull.getCityIds();
						if (citiesIds != null && citiesIds.contains(cityId))
						{
							res.add(tourFull);
						}
					}
					
					safeListSort(res);
					
					return res;
				}
				else
				{
					return countriesTours;
				}			
			}
			
			return null;
	   }
	}
	
	@Override
	public List<TourFull> getTours(int tourOpId)
	{
		synchronized (lock)
		{
			return touropsToursMap.get(tourOpId);			
	   }
	}	

	private PageInfo safeGetPageInfo(Map<Integer, PageInfo> map, int id)
	{
		PageInfo res = map.get(id);
		if (res == null)
		{
			res = new PageInfo();
			String errStr = "Banan Горящие туры - Ошибка! Проверьте превильность заполнения сведений о странице!";
			res.setTitle(errStr);
			res.setDescription(errStr);
			res.setUserTitle(errStr);
		}
		
		return res;
	}
	
	@Override
	public PageInfo getPageInfoForTourOp(int tourOpId) 
	{		
		return safeGetPageInfo(pageInfoTourOps, tourOpId);
	}
	
	@Override
	public PageInfo getPageInfoForCity(int cityId) 
	{		
		return safeGetPageInfo(pageInfoCities, cityId);
	}
	
	@Override
	public PageInfo getPageInfoForCountry(int countryId) 
	{		
		return safeGetPageInfo(pageInfoCountries, countryId);
	}

	@Override
	public List<TourFull> getRandomTours(int count)
	{
		synchronized (lock)
		{
			List<TourFull> copySea = new ArrayList<TourFull>();
			copySea.addAll(seaTours);
			
//			System.out.println("Sea tours size: " + seaTours.size());
			
			Collections.shuffle(copySea);
			
			if (count < copySea.size())
			{							
				List<TourFull> res = copySea.subList(0, count);
				safeListSort(res);
				
				return res;
			}
			else
			{
				return copySea;
			}		
		}	
	}

	@Override
	public List<TourFull> getNearestTours() 
	{		
		synchronized (lock)
		{
			return nearestTours;
		}
	}

	@Override
	public List<TourFull> getSeaTours() 
	{
		synchronized (lock)
		{
			return seaTours;
		}
	}

	@Override
	public List<TourFull> getBusTours() 
	{		
		synchronized (lock)
		{
			return busTours;	
		}	
	}

	@Override
	public String getText(Integer countryId, Integer cityId)
	{		
		return cityId != null ? citiesMap.get(cityId).getDescription() : countriesMap.get(countryId).getDescription();
	}
	
	@Override
	public String getText(int tourOpId)
	{		
		TourOp op = tourOpsMap.get(tourOpId);
		
		return op != null ? op.getDescription() : null;
	}

	@Override
	public List<CountPresentation> getTopCountriesCountPresentation()
	{		
		return topCountries;
	}

	@Override
	public List<CountPresentation> getTopTourOpsCountPresentation()
	{
		return topTourOps;
	}
	
	private String toSQLString(String s)
	{
		if (s != null)
		{
			s = s.replace('\'', '"');
			
			return "'" + s + "'"; 
		}
		
		return "NULL";
	}

	@Override
	public void saveFeedbackComment(Comment c) 
	{
		if (c != null)
		{
			Statement statement = null;
			 						
			String insertTableSQL = "INSERT INTO FEEDBACK_COMMENTS "
					+ "(COMMENT, NAME, PHONE, MAIL, CREATED) " + " VALUES "
					+ "(" + toSQLString(c.getComment()) + ", " +
					        toSQLString(c.getName()) + ", " +
					        toSQLString(c.getPhone()) + ", " +
					        toSQLString(c.getMail()) + ", " +
					        "CURRENT_TIMESTAMP" + ")";
					
			System.out.println(insertTableSQL);
			
			try 
			{
				statement = connToToursDb.createStatement();
	 	 
				statement.executeUpdate(insertTableSQL);	 
			} 
			catch (SQLException e) 
			{	 
				System.out.println(e.getMessage());
	 
			} 
			finally
			{	 
				if (statement != null) 
				{
					try
					{
						statement.close();
					} 
					catch (SQLException e) 
					{
						System.out.println(e.getMessage());
					}
				}	 
			}
		}
	}

	@Override
	public void savePartnerComment(Comment c) 
	{
		if (c != null)
		{
			Statement statement = null;
			 						
			String insertTableSQL = "INSERT INTO FEEDBACK_COMMENTS "
					+ "(COMMENT, NAME, PHONE, MAIL, CREATED) " + " VALUES "
					+ "(" + toSQLString(c.getComment()) + ", " +
					        toSQLString(c.getName()) + ", " +
					        toSQLString(c.getPhone()) + ", " +
					        toSQLString(c.getMail()) + ", " +
					        "CURRENT_TIMESTAMP" + ")";
					
			System.out.println(insertTableSQL);
			
			try 
			{
				statement = connToToursDb.createStatement();
	 	 
				statement.executeUpdate(insertTableSQL);	 
			} 
			catch (SQLException e) 
			{	 
				System.out.println(e.getMessage());
	 
			} 
			finally
			{	 
				if (statement != null) 
				{
					try
					{
						statement.close();
					} 
					catch (SQLException e) 
					{
						System.out.println(e.getMessage());
					}
				}	 
			}
		}
	}

	private List<TourFull> getFromAllTours(Predicate<TourFull> p)
	{
		List<TourFull> res = null;
		
		for(List<TourFull> list : touropsToursMap.values())
		{
			if (list != null && !list.isEmpty())
			{
				for(TourFull tourFull : list)
				{
					if (p.apply(tourFull))
					{
						if (res == null)
						{
							res = new ArrayList<TourFull>();
						}
						
						res.add(tourFull);
					}
				}
			}
		}
		
		if (res != null && !res.isEmpty())
		{
			Collections.sort(res);
		}
		
		return res != null && !res.isEmpty() ? res : Collections.EMPTY_LIST;
	}
	
	@Override
	public List<TourFull> getDblTours()
	{			
		return getFromAllTours(new Predicate<TourFull>()
		{			
			@Override
			public boolean apply(TourFull tourFull) 
			{				
				return tourFull != null && tourFull.getPersonsCount() == 2;
			}
		});		
	}

	@Override
	public List<TourFull> getFeedTours()
	{
		return getFromAllTours(new Predicate<TourFull>()
		{			
			@Override
			public boolean apply(TourFull tourFull) 
			{			
				String feed = tourFull != null ? tourFull.getFeedPlan() : null;
				//Only in Turkey and Egypt				
				return feed != null && (feed.equalsIgnoreCase("AI") || feed.equalsIgnoreCase("UAI")) && (tourFull.getCountryIds().contains(109) || tourFull.getCountryIds().contains(61));				
			}
		});		
	}

	@Override
	public List<TourFull> getContinentTours(final short continentId)
	{
		return getFromAllTours(new Predicate<TourFull>()
		{			
			@Override
			public boolean apply(TourFull tourFull) 
			{			
				List<Integer> countries = tourFull.getCountryIds();
				
				if (countries != null)
				{
					for(Integer countryId : countries)
					{
						Country c = countriesMap.get(countryId);
						Short currentContinentId = c != null ? c.getContinentId() : null;
						
						if (currentContinentId != null && currentContinentId.equals(continentId))
						{
							return true;
						}
					}
				}
				
				return false;
			}
		});	
	}

	@Override
	public List<TourFull> getToursFromCity(final int cityId)
	{
		return getFromAllTours(new Predicate<TourFull>()
				{			
					@Override
					public boolean apply(TourFull tourFull) 
					{			
						Integer depCityId = tourFull.getDepartCityId();
						return depCityId != null && depCityId.equals(cityId);						
					}
				});	
	}

	
	
}
