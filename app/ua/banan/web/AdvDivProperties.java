package ua.banan.web;

public class AdvDivProperties
{
	public static final String ADV_DIV_CLASS_COUNTRY 			= "country";
	public static final String ADV_DIV_CLASS_CITY 				= "city";
	public static final String ADV_DIV_CLASS_DIVIDER 			= "divider";
	public static final String ADV_DIV_CLASS_HOTEL 				= "hotel";
	public static final String ADV_DIV_CLASS_HOTEL_DETAIL 		= "hotel-detail";
	public static final String ADV_DIV_CLASS_PRICE 				= "price";
	public static final String ADV_DIV_CLASS_DATE 				= "date";	
	
	
	private String  clz;
	private int     fontSize;
	private int 	fontWeight;
	private int 	topPixels;
	private String  font;
	private String  content;	
	private String  backgroundUrl;
	
	public AdvDivProperties()
	{
		super();
	}	
	
	
	public String getClz()
	{
		return clz;
	}
	public void setClz(String clz)
	{
		this.clz = clz;
	}
	public int getFontSize()
	{
		return fontSize;
	}
	public void setFontSize(int fontSize)
	{
		this.fontSize = fontSize;
	}
	public int getFontWeight()
	{
		return fontWeight;
	}
	public void setFontWeight(int fontWeight)
	{
		this.fontWeight = fontWeight;
	}
	public int getTopPixels()
	{
		return topPixels;
	}
	public void setTopPixels(int topPixels)
	{
		this.topPixels = topPixels;
	}
	public String getFont()
	{
		return font;
	}
	public void setFont(String font)
	{
		this.font = font;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}

	public boolean isDivider()
	{
		return backgroundUrl != null;
	}


	public String getBackgroundUrl()
	{
		return backgroundUrl;
	}



	public void setBackgroundUrl(String backgroundUrl)
	{
		this.backgroundUrl = backgroundUrl;
	}

	public boolean isHotelDetail()
	{
		return clz != null && clz.equals(ADV_DIV_CLASS_HOTEL_DETAIL);
	}
	
	public boolean isPrice()
	{
		return clz != null && clz.equals(ADV_DIV_CLASS_PRICE);
	}
	
	public static String getRightWordForm(Integer num, WordCase cases)
	{
		num = Math.abs(num);
		
		String word = "";
	    
	    if (num.toString().indexOf('.') > -1) {
	        word = cases.gen;
	    } else { 
	        word = (
	            num % 10 == 1 && num % 100 != 11 
	                ? cases.nom
	                : num % 10 >= 2 && num % 10 <= 4 && (num % 100 < 10 || num % 100 >= 20) 
	                    ? cases.gen
	                    : cases.plu
	        );
	    }
	    
	    return word;
	}
}
