package ua.banan.data.model;

public class Hotel 
{
	private Long id;
	private String name;
	private String description;
	private int stars;
	private Long cityId;
    
    
    
	public Hotel() 
	{
		super();
	}



	public Hotel(Long id, String name, String description, int stars, Long cityId) 
	{
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.stars = stars;
		this.cityId = cityId;
	}



	public Long getId()
	{
		return id;
	}



	public void setId(Long id)
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



	public Long getCityId() 
	{
		return cityId;
	}



	public void setCityId(Long cityId)
	{
		this.cityId = cityId;
	}



	
}
