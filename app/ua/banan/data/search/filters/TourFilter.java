package ua.banan.data.search.filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TourFilter <T extends Object>
{
	private String 				  name;	
	
	private List<FilterValue<T>> filterValues;	
		
	
	public TourFilter(String name) 
	{
		this.name = name;
	}
	
	
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public List<FilterValue<T>> getFilterValues()
	{
		return filterValues;
	}
	public void setFilterValues(List<FilterValue<T>> filterValues) 
	{
		this.filterValues = filterValues;
	}


	public List<FilterValue<T>> getValues(String inputString)
	{
		if (inputString != null)
		{
			List<FilterValue<T>> res = new ArrayList<FilterValue<T>>();
			
			List<String> terms = Arrays.asList(inputString.split("\\s+"));
			if (terms != null && !terms.isEmpty())
			{
				for (String term : terms)
				{
					FilterValue<T> filterValue = getFilterValue(term);
					if (filterValue != null)
					{
						res.add(filterValue);
					}
				}
			}
			
			return res;			
		}
		
		return null;
	}
	
	private FilterValue<T> getFilterValue(String term)
	{
		if (term != null && !term.isEmpty() && filterValues != null)
		{
			for (FilterValue<T> filterValue : filterValues)
			{
				String stringFilterValue = filterValue.getValue().toString();
				if (stringFilterValue.equalsIgnoreCase(term))
				{
					return filterValue;
				}
				
				List<T> synonymsList = filterValue.getSynonyms();
				if (synonymsList != null && !synonymsList.isEmpty())
				{
					for (T synonym : synonymsList)
					{
						String stringFilterValueSynonym = synonym.toString();
						if (stringFilterValueSynonym.equalsIgnoreCase(term))
						{
							return filterValue;
						}
					}
				}
			}
		}
		
		return null;
	}
	
}
