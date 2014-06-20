package ua.banan.data.model;

public class Photo
{
	public static final String NO_PHOTO_URL  = "NONE";//TODO	
	
	private String url;
	private int height;
	private int width;
	
	
	public Photo(String url, int height, int width)
	{
		super();
		this.url = url;
		this.height = height;
		this.width = width;
	}


	public String getUrl()
	{
		return url;
	}


	public void setUrl(String url)
	{
		this.url = url;
	}


	public int getHeight()
	{
		return height;
	}


	public void setHeight(int height)
	{
		this.height = height;
	}


	public int getWidth()
	{
		return width;
	}


	public void setWidth(int width)
	{
		this.width = width;
	}
	
//	public int formatFirstClassImgHeight()
//	{
//		double ratio = ((double) height) / width;
//		
//		return (int) (ratio * CLASS_ADV_WIDTH_FIRST);
//	}
//	
//	public int formatSecondClassImgHeight()
//	{
//		double ratio = ((double) height) / width;
//		
//		return (int) ratio * CLASS_ADV_WIDTH_SECOND;
//	}
//	
//	public int formatThirdClassImgHeight()
//	{
//		double ratio = ((double) height) / width;
//		
//		return (int) ratio * CLASS_ADV_WIDTH_THIRD;
//	}
//	
//	public int formatFourthClassImgHeight()
//	{
//		double ratio = ((double) height) / width;
//		
//		return (int) ratio * CLASS_ADV_WIDTH_FOURTH;
//	}
	
	public int minus(int a, int b)
	{
		return a - b;
	}
	
	public String addPx(int integer)
	{
		return integer + "px";
	}
	
	
//	public int formatClassImgHeight(int advClass)
//	{
//		switch(advClass)
//		{
//			case 0:{
//				return formatFirstClassImgHeight();
//			}
//			case 1:{
//				return formatSecondClassImgHeight();
//			}
//			case 2:{
//				return formatThirdClassImgHeight();
//			}
//			case 3:{
//				return formatFourthClassImgHeight();
//			}
//		}
//		
//		return 0;
//	}
	
	
//	public String formatClassImgHeightStr(int advClass)
//	{
//		return addPx(formatClassImgHeight(advClass));
//	}
//	
	
}
