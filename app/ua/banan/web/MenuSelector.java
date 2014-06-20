package ua.banan.web;

public class MenuSelector
{
	public static final int NONE = 0;
	public static final int HOT_TOURS = 1;
	public static final int BUS_TOURS = 2;
	public static final int SEA_TOURS = 3;
	public static final int PARTNERS = 4;
	public static final int FEEDBACK = 5;
	public static final int ABOUT = 6;
	
	private int type;

	public MenuSelector(int type) {
		super();
		this.type = type;
	}
	
	public boolean isHot()
	{
		return type == HOT_TOURS;
	}
	
	public boolean isBus()
	{
		return type == BUS_TOURS;
	}
	
	public boolean isSea()
	{
		return type == SEA_TOURS;
	}
	
	public boolean isPartners()
	{
		return type == PARTNERS;
	}
	
	public boolean isFeedback()
	{
		return type == FEEDBACK;
	}
	
	public boolean isAbout()
	{
		return type == ABOUT;
	}
	
	public boolean isNone()
	{
		return type == NONE;
	}
}
