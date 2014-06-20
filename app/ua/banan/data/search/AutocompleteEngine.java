package ua.banan.data.search;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AutocompleteEngine
{
	private static final String GOOGLE_AUTOCOMPLETE_API_URL = "http://suggestqueries.google.com/complete/search?client=chrome&language=ru&q=";
	
	
	private static final String GOOGLE_AUTOCOMPLETE_TERM_1 = "тур ";

	private static final int GOOGLE_GOOGLE_RESPONSE_JSON_OFFSET  = 5;
	
	
	private static final List<String> GOOGLE_AUTOCOMPLETE_TERMS_LIST = new ArrayList<String>();

	static
	{
		GOOGLE_AUTOCOMPLETE_TERMS_LIST.add(GOOGLE_AUTOCOMPLETE_TERM_1);
	}
	
	public static String getSuggestionsJSONArray(String term)
	{		
		String res = "[";
		
		for (int i = 0; i < GOOGLE_AUTOCOMPLETE_TERMS_LIST.size(); i++)
		{
			String googleAutocompleteTerm = GOOGLE_AUTOCOMPLETE_TERMS_LIST.get(i);
			String currRes = getSuggestionsByTerm(googleAutocompleteTerm, term);
			if (currRes != null && !currRes.isEmpty())
			{
				res += currRes + ",";
				if (i != (GOOGLE_AUTOCOMPLETE_TERMS_LIST.size() - 1))
				{
					res += ",";
				}
			}
		}
		
		res += "]";
		
		return res;
	}
	
	private static String getSuggestionsByTerm(String googleAutocompleteTerm, String userTerm)
	{
		if (userTerm != null)
		{
			userTerm = userTerm.replace(" ", "+");
			String googleQuery = GOOGLE_AUTOCOMPLETE_API_URL + 
					             (googleAutocompleteTerm != null ? (googleAutocompleteTerm + userTerm) : userTerm);
			
			URL obj;
			try {
				obj = new URL(googleQuery);
			
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();	 
				con.setRequestMethod("GET");	 
				//add request header
				con.setRequestProperty("User-Agent", "Mozilla/5.0");
		 		 
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				
				String inputLine;
				StringBuffer response = new StringBuffer();
		 
				while ((inputLine = in.readLine()) != null)
				{
					response.append(inputLine);
				}
				in.close();
		 
				String responseString = response.toString();
				System.out.println(responseString);
				if (responseString != null)
				{
					responseString = responseString.substring(GOOGLE_GOOGLE_RESPONSE_JSON_OFFSET);
					String suggestionsArrayString = responseString.substring(0, responseString.indexOf(']'));
					suggestionsArrayString.replace(googleAutocompleteTerm, "");
					
					return suggestionsArrayString;
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
