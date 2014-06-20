package ua.banan.data.model;

import ua.banan.data.model.interfaces.Named;

public class City implements Named
{
	private Integer		id;
	private String 		name;
	private String 		nameEn;
	private String 		description;
	private Integer		countryId;
            
	public City() 
	{
		super();
	}

	public City(Integer id, String name, String description, Integer countryId) 
	{
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.countryId = countryId;
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

	public String getDescription() 
	{
		return description;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}
	
	public Integer getCountryId() 
	{
		return countryId;
	}
	public void setCountryId(Integer countryId) 
	{
		this.countryId = countryId;
	}

	public String getNameEn() 
	{
		return nameEn;
	}
	public void setNameEn(String nameEn) 
	{
		this.nameEn = nameEn;
	}
    
}
