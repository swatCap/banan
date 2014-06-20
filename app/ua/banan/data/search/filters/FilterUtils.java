package ua.banan.data.search.filters;

public class FilterUtils
{
	public static String normalizeString(String input)
	{
		if (input != null && !input.isEmpty())
		{
			input = input.toLowerCase();
			input = input.replaceAll("\\s+", " ");
		}
		
		return input;
	}
}
