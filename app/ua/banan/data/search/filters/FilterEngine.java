package ua.banan.data.search.filters;

import java.util.Date;
import java.util.List;

import ua.banan.data.model.Pair;

public interface FilterEngine
{
	public Pair<Date, Date> getPeriod(String token);
	
	public List<Integer> getCountriesIds(String token);
	
	public Pair<Integer, Integer> getMoneyRangeUSD(String token);
}
