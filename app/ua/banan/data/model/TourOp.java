package ua.banan.data.model;

import ua.banan.data.model.interfaces.Named;

public class TourOp implements Named
{
	private Integer id;
	private String  name;
    private String  description;
    private String  photoUrl;
    private String  url;
    
          
	public TourOp() 
	{
		super();
	}

	public TourOp(Integer id, String name, String description)
	{
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public TourOp(Integer id, String name)
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

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
