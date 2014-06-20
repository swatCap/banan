package ua.banan.data.search;

import java.util.List;

public class SearchStringContainer 
{
	private String s;
	
	private List<String> c;
	
	private String m;
	
	public SearchStringContainer() { }
	
	public String getSearchString()
	{
		return s;
	}

	public void setSearchString(String searchString)
	{
		this.s = searchString;
	}

	
	

	public List<String> getC() {
		return c;
	}

	public void setC(List<String> c) {
		this.c = c;
	}

	public String getMoneyString()
	{
		return m;
	}

	public void setMoneyString(String moneyString)
	{
		this.m = moneyString;
	}

	public String getS()
	{
		return s;
	}

	public void setS(String s)
	{
		this.s = s;
	}

	public String getM()
	{
		return m;
	}

	public void setM(String m)
	{
		this.m = m;
	}
	
	
}
