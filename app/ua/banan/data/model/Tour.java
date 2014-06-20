package ua.banan.data.model;

import java.util.Date;

public class Tour implements Comparable<Tour>
{
	protected Integer  id;
	protected String   name;
	protected String   url;
	protected int      price;
	protected String   feedPlan;
	protected String   roomType;
	protected int      nightsCount;
	protected int      personsCount;    
	protected Date     flightDate;    
	protected String   description;
	protected int      stars;
	protected Integer  hotelId;
	protected Integer  departCityId;
	protected Integer  countryId;
    protected Integer  sourceId;
	
	public Tour() 
	{
		super();
	}

	public Tour(String name, Date date)
	{
		this.name       = name;
		this.flightDate = date;
	}
	
	

	@Override
	public String toString()
	{
		return id + " " + url;
	}

	public Integer getId() 
	{
		return id;
	}



	public void setId(Integer id) 
	{
		this.id = id;
	}



	public String getName() 
	{
		return name;
	}



	public void setName(String name) 
	{
		this.name = name;
	}



	public String getUrl() 
	{
		return url;
	}



	public void setUrl(String url) 
	{
		this.url = url;
	}



	public int getPrice() 
	{
		return price;
	}



	public void setPrice(int price)
	{
		this.price = price;
	}



	public String getFeedPlan() 
	{
		return feedPlan;
	}



	public void setFeedPlan(String feedPlan)
	{
		this.feedPlan = feedPlan;
	}	



	public String getRoomType()
	{
		return roomType;
	}



	public void setRoomType(String roomType)
	{
		this.roomType = roomType;
	}

	public int getNightsCount() 
	{
		return nightsCount;
	}



	public void setNightsCount(int nightsCount) 
	{
		this.nightsCount = nightsCount;
	}



	public int getPersonsCount() 
	{
		return personsCount;
	}



	public void setPersonsCount(int personsCount) 
	{
		this.personsCount = personsCount;
	}



	public Date getFlightDate() 
	{
		return flightDate;
	}



	public void setFlightDateDate(Date flightDate)
	{
		this.flightDate = flightDate;
	}
	

	public String getDescription() 
	{
		return description;
	}



	public void setDescription(String description) 
	{
		this.description = description;
	}



	public int getStars() 
	{
		return stars;
	}



	public void setStars(int stars) 
	{
		this.stars = stars;
	}



	public Integer getHotelId() 
	{
		return hotelId;
	}



	public void setHotelId(Integer hotelId) 
	{
		this.hotelId = hotelId;
	}

    public Integer getDepartCityId() {
        return departCityId;
    }

    public void setDepartCityId(Integer departCityId) {
        this.departCityId = departCityId;
    }

	public Integer getCountryId()
	{
		return countryId;
	}

	public void setCountryId(Integer countryId)
	{
		this.countryId = countryId;
	}

	public void setFlightDate(Date flightDate)
	{
		this.flightDate = flightDate;
	}
	
	

	
	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	@Override
	public int compareTo(Tour arg0) 
	{						
		return arg0 != null ? flightDate.compareTo(arg0.getFlightDate()) : 1;
	}
	

	
	
}