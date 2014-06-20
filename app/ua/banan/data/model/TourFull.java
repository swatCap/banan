package ua.banan.data.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import ua.banan.web.Routes;

public class TourFull extends Tour
{
	public static final String DATE_FORMAT = "dd.MM";
	
	
	public static final Double MIN_HEIGHT_IN_PERCENTS = 0.30;
	
	public static final int ADV_BOTTOM_MARGIN_IN_PIXELS     = 50;
	public static final int ADV_TOP_MARGIN_IN_PIXELS     	= 20;
	public static final int ADV_DIVS_STEP_IN_PIXELS         = 15;	
	
	
	public static final String ADV_DIV_CLASS_1 		= "1adv";
	public static final String ADV_DIV_CLASS_2 		= "2adv";
	public static final String ADV_DIV_CLASS_3 		= "3adv";
	public static final String ADV_DIV_CLASS_4 		= "4adv";
	public static final String ADV_DIV_CLASS_5 		= "5adv";
	
	
	private List<String> countryNames;
	private List<String> cityNames;
    
	private List<Integer> countryIds;
	private List<Integer> cityIds;
	
	private String hotelName;
    
	private Photo photo;       
    	
	private int daysBeforeLeave;
	
	private String sourceName = "сайт";
    
    public TourFull()
    {
    }
    
    public TourFull(Tour t)
    {
    	setId(t.getId());
    	setName(t.getName());
    	setUrl(t.getUrl());
    	setPrice(t.getPrice());
    	setFeedPlan(t.getFeedPlan());
    	setRoomType(t.getRoomType());
    	setNightsCount(t.getNightsCount());
    	setPersonsCount(t.getPersonsCount());
    	setFlightDate(t.getFlightDate());
    	setDescription(t.getDescription());
    	setStars(t.getStars());
    	setHotelId(t.getHotelId());
    }

    public List<String> getCountryNames()
    {
        return countryNames;
    }

    public void setCountryNames(List<String> countryNames)
    {
        this.countryNames = countryNames;
    }

    public List<String> getCityNames()
    {
        return cityNames;
    }

    public void setCityNames(List<String> cityNames)
    {
        this.cityNames = cityNames;
    }

	public void setPhoto(Photo photo)
	{
		this.photo = photo;
	}	

	public Photo getPhoto()
	{
		return photo;
	}

	public List<Integer> getCountryIds()
	{
		return countryIds;
	}

	public void setCountryIds(List<Integer> countryIds)
	{
		this.countryIds = countryIds;
	}

	public List<Integer> getCityIds()
	{
		return cityIds;
	}

	public void setCityIds(List<Integer> cityIds)
	{
		this.cityIds = cityIds;
	}
	
	public String getHotelName()
	{
		return hotelName;
	}

	public void setHotelName(String hotelName)
	{
		this.hotelName = hotelName;
	}	

	public String getPeriodString()
	{
		Calendar c = Calendar.getInstance();
		c.setTime(getFlightDate());
		c.add(Calendar.DAY_OF_YEAR, getNightsCount());
					
		return "с " + new SimpleDateFormat(DATE_FORMAT).format(getFlightDate()) + (getNightsCount() != 0 ? (" по " + new SimpleDateFormat(DATE_FORMAT).format(c.getTime())) : "");
	}

	
	public String getCountryInfoString()
	{
		String res = "";
		if (cityNames != null && !cityNames.isEmpty())
		{
			if (cityNames.size() == 1 && countryNames.size() == 1)
			{
				return countryNames.get(0) + "\n" + cityNames.get(0);
			}
			else if (cityNames.size() > 1)
			{
				for(String cityName : cityNames)
				{
					res += cityName + " ";
				}
			}
			else
			{
				for(String countryName : countryNames)
				{				
					res += countryName + " ";
				}
			}
		}
		else
		{
			for(String countryName : countryNames)
			{				
				res += countryName + " ";
			}
		}
		
		return res;
	}
	
	public String getCountriesString()
	{
//		<a href='/' rel='nofollow'><font color="black">СТРАНА </font></a>
		String res = "";
		
		int resLengthWithoutTags = 0;
		
		if (countryNames != null && !countryNames.isEmpty())
		{
			for(String countryName : countryNames)
			{				
				if (resLengthWithoutTags + countryName.length() > 39)
				{
					res = res.substring(0, res.length() - 1) + "...";
					break;
				}
				else
				{
					Integer countryId = countryIds.get(countryNames.indexOf(countryName));
					
					if (countryId.equals(0)) //bus tour
					{
						res += "<a href='/%D0%B0%D0%B2%D1%82%D0%BE%D0%B1%D1%83%D1%81%D0%BD%D1%8B%D0%B5_%D1%82%D1%83%D1%80%D1%8B' rel='nofollow'>" + countryName + " </a> ";
					}
					else
					{
						String ref = Routes.countriesRoutes.get(countryId);
						if (ref != null)
						{
							res += "<a href='" + ref + "' rel='nofollow'>" + countryName + " </a> ";
						}
						else
						{
							res += countryName + " ";
						}											
					}
					
					resLengthWithoutTags += countryName.length() + 1;
				}
			}			
		}
		
		return res;
	}

	public int getDaysBeforeLeave() {
		return daysBeforeLeave;
	}

	public void setDaysBeforeLeave(int daysBeforeLeave) {
		this.daysBeforeLeave = daysBeforeLeave;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public int getHotelFontSizeInPx() 
	{
		if (hotelName != null && !hotelName.equalsIgnoreCase("NONE"))
		{
			if (hotelName.length() <= 17)
			{
				return 18;
			}
			else if (hotelName.length() <= 20)
			{
				return 17;
			}
			else if (hotelName.length() <= 24)
			{
				return 14;
			}
			else if (hotelName.length() <= 28)
			{
				return 13;
			}
			else 
			{
				return 10;
			}
		}
		
		return 0;
	}
	
	public boolean isManyCountries()
	{
		return countryNames != null && countryNames.size() > 1;				
	}
	
	
	
}
