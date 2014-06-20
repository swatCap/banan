package ua.banan.data.search.filters.impl;

import java.sql.Connection;
import java.util.List;

import org.h2.jdbcx.JdbcConnectionPool;

import ua.banan.data.search.filters.TourFilter;
import ua.banan.data.search.filters.FilterValue;

public class CountryFilter
{
	private static Connection conn;
    private static JdbcConnectionPool cp;
	
	
	public CountryFilter() 
	{		
		
	}

	public List<Integer> getValues(String inputString) 
	{


		return null;
	}

}
