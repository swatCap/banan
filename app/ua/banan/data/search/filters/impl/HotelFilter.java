package ua.banan.data.search.filters.impl;

import java.util.List;

import ua.banan.data.search.filters.TourFilter;
import ua.banan.data.search.filters.FilterValue;

public class HotelFilter extends TourFilter<String>
{

	public HotelFilter() 
	{		
		super(HotelFilter.class.getName());
	}

	@Override
	public List<FilterValue<String>> getValues(String inputString) 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
