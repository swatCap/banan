package ua.banan.data.search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DidYouMeaner
{
	public static String getDidYouMeanTip(String inputString)
    {
        try
        {
            Document doc = Jsoup.connect("https://www.google.com.ua/search?client=firefox-a&hl=ru&q=" + inputString).
                    header("URIEncoding", "UTF-8").userAgent("Mozilla").get();
            Element elementsSpell = doc.getElementById("spe");

            if (elementsSpell != null)
            {                
                Elements spellElement = elementsSpell.getElementsByTag("i");
                if (spellElement != null && !spellElement.isEmpty())
                {                    
                    String spelledWords = spellElement.get(0).html();

                    return spelledWords;                                  
                }                            
            }                                                                        
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return inputString;
    }
}
