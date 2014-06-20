package ua.banan.data.search.filters;

import java.util.List;

public interface FilterValue<T extends Object> 
{
	public T getValue();
	public void setValue(T value);
	
	public List<T> getSynonyms();
	public void setSynonyms(List<T> synonyms);
}
