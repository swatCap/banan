package ua.banan.data.search.filters.impl;

import java.util.Date;
import java.util.List;

import ua.banan.data.model.Pair;
import ua.banan.data.search.filters.DateParser;
import ua.banan.data.search.filters.FilterEngine;
import ua.banan.data.search.filters.MoneyParser;
import ua.banan.data.search.filters.MoneyRange;

public class FilterEngineImpl implements FilterEngine
{
	private CountryFilter countryFilter = new CountryFilter();
	
	@Override
	public Pair<Date, Date> getPeriod(String token)
	{
		return DateParser.findDatePeriod(token);
	}

	@Override
	public List<Integer> getCountriesIds(String token)
	{		
		return countryFilter.getValues(token);
	}

	@Override
	public Pair<Integer, Integer> getMoneyRangeUSD(String token)
	{
		MoneyRange moneyRange = MoneyParser.findMoneyRange(token);
		
		return moneyRange != null ? moneyRange.convertToUsd() : null;
	}
	
}
