package ua.banan.data.model;

import ua.banan.data.model.interfaces.Named;

public class Country implements Named
{	
	private Integer id;
	private String  name;
	private String  nameEn;
    private String  description;
    private Short   continentId;
          
	public Country() 
	{
		super();
	}

	public Country(Integer id, String name, String description)
	{
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Country(Integer id, String name)
	{
		super();
		this.id = id;
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

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public Short getContinentId() {
		return continentId;
	}

	public void setContinentId(Short continentId) {
		this.continentId = continentId;
	}
		
	
}
