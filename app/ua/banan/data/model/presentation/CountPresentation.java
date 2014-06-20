package ua.banan.data.model.presentation;

public class CountPresentation implements Comparable<CountPresentation>
{
	private String name;
	
	private int count;
	private String photoImgUrl;
	private String url;
	
	public CountPresentation() { }
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getCount()
	{
		return count;
	}
	public void setCount(int count)
	{
		this.count = count;
	}	
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getPhotoImgUrl()
	{
		return photoImgUrl;
	}

	public void setPhotoImgUrl(String photoImgUrl)
	{
		this.photoImgUrl = photoImgUrl;
	}

	@Override
	public int compareTo(CountPresentation o)
	{
		return Integer.compare(count, o != null ? o.getCount() : 0);
	}
	
	
}
