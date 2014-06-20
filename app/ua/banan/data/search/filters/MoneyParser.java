package ua.banan.data.search.filters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ua.banan.data.model.Pair;

public class MoneyParser
{
	private static final String regexFromToFull         = "(от|від)\\s?[0-9]{3,}\\s?(euro|евро|евр|eur|€|євро|долл|долларов|$|usd|у\\.е\\.|уе|грн|гривен|грв|гривень|uah)\\s.*до\\s?[0-9]{3,}\\s?(euro|евро|евр|eur|€|євро|долл|долларов|$|usd|у\\.е\\.|уе|грн|гривен|грв|гривень|uah)";
	private static final String regexFromTo_NoFromMoney = "(от|від)\\s?[0-9]{3,}.*до\\s?[0-9]{3,}\\s?(euro|евро|евр|eur|€|євро|долл|долларов|$|usd|у\\.е\\.|уе|грн|гривен|грв|гривень|uah)";
        private static final String regexFromTo_NoToMoney   = "(от|від)\\s?[0-9]{3,}\\s?(euro|евро|евр|eur|€|євро|долл|долларов|$|usd|у\\.е\\.|уе|грн|гривен|грв|гривень|uah).*до\\s?[0-9]{3,}";
	private static final String regexFrom               = "(от|від)\\s?[0-9]{3,}\\s?(euro|евро|евр|eur|€|євро|долл|долларов|$|usd|у\\.е\\.|уе|грн|гривен|грв|гривень|uah).*";
	private static final String regexTo                 = "(до)?\\s?[0-9]{3,}\\s?(euro|евро|евр|eur|€|євро|долл|долларов|$|usd|у\\.е\\.|уе|грн|гривен|грв|гривень|uah)";
	private static final String regexRange              = "[0-9]{3,}(-|\\s)[0-9]{3,}\\s?(euro|евро|евр|eur|€|євро|долл|долларов|$|usd|у\\.е\\.|уе|грн|гривен|грв|гривень|uah)";        
	
	private static final String regexNumber             = "[0-9]+";        
        
        
        private static final Pattern patternFromToFull          = Pattern.compile(regexFromToFull);
        private static final Pattern patternFromTo_NoFromMoney  = Pattern.compile(regexFromTo_NoFromMoney);
        private static final Pattern patternFromTo_NoToMoney    = Pattern.compile(regexFromTo_NoToMoney);
        private static final Pattern patternFrom                = Pattern.compile(regexFrom);
        private static final Pattern patternTo                  = Pattern.compile(regexTo);
        private static final Pattern patternRange               = Pattern.compile(regexRange);
        
        private static final Pattern patternNumber              = Pattern.compile(regexNumber);
        
        
	public static MoneyRange findMoneyRange(String term)
	{
		term = FilterUtils.normalizeString(term);
		Pair<Integer, Integer> range = new Pair<Integer, Integer>();
                
		if (term != null && !term.isEmpty())
		{                    
                    Matcher matcher = patternFromToFull.matcher(term);
                    if (matcher.find()) 
                    {
                        String s = matcher.group();
                        List<Integer> numbers = extractNumbersSort(s);
                        range.setFirst(numbers.get(0));
                        range.setSecond(numbers.get(1));
                        
                        return new MoneyRange(range, findMoneyCurrency(s));
                    }           
                    
                    matcher = patternFromTo_NoFromMoney.matcher(term);
                    if (matcher.find()) 
                    {
                        String s = matcher.group();
                        List<Integer> numbers = extractNumbersSort(s);
                        range.setFirst(numbers.get(0));
                        range.setSecond(numbers.get(1));
                        
                        return new MoneyRange(range, findMoneyCurrency(s));
                    }  
                    
                    matcher = patternFromTo_NoToMoney.matcher(term);
                    if (matcher.find()) 
                    {
                        String s = matcher.group();
                        List<Integer> numbers = extractNumbersSort(s);
                        range.setFirst(numbers.get(0));
                        range.setSecond(numbers.get(1));
                        
                        return new MoneyRange(range, findMoneyCurrency(s));
                    }
                    
                    matcher = patternFrom.matcher(term);
                    if (matcher.find()) 
                    {
                        String s = matcher.group();
                        List<Integer> numbers = extractNumbersSort(s);
                        range.setFirst(numbers.get(0));
                        
                        return new MoneyRange(range, findMoneyCurrency(s));
                    }
                    
                    matcher = patternTo.matcher(term);
                    if (matcher.find()) 
                    {
                        String s = matcher.group();
                        List<Integer> numbers = extractNumbersSort(s);
                        range.setSecond(numbers.get(0));
                        
                        return new MoneyRange(range, findMoneyCurrency(s));
                    }
                    
                    matcher = patternRange.matcher(term);
                    if (matcher.find()) 
                    {
                        String s = matcher.group();
                        List<Integer> numbers = extractNumbersSort(s);
                        range.setFirst(numbers.get(0));
                        range.setSecond(numbers.get(1));
                        
                        return new MoneyRange(range, findMoneyCurrency(s));
                    }
		}
                
		return new MoneyRange(range, findMoneyCurrency(term));
	}	
	
	private static int findMoneyCurrency(String term)
	{
		if (term != null && !term.isEmpty())
		{
			term = term.toLowerCase();
			
			if (term.contains("грн") || term.contains("гривен") || term.contains("грв") || term.contains("гривень") || term.contains("uah"))
			{
				return MoneyRange.CUURENCY_UAH;
			}
			else if (term.contains("долл") || term.contains("долларов") || term.contains("$") || term.contains("usd") || term.contains(" у.е") || term.contains(" уе"))
			{				
				return MoneyRange.CUURENCY_USD;
			}
			else if (term.contains("eur") || term.contains(" евр") || term.contains("€") || term.contains(" євро"))
			{				
				return MoneyRange.CUURENCY_EUR;
			}			
		}
		
		return MoneyRange.CUURENCY_USD;
	}      

    private static List<Integer> extractNumbersSort(String term) 
    {
        if (term != null && !term.isEmpty())
        {        
            List<Integer> numbers = new ArrayList<>();
            Matcher matcher = patternNumber.matcher(term);
            while (matcher.find()) 
            {
                numbers.add(Integer.parseInt(matcher.group()));
            }           
            
            Collections.sort(numbers);

            return numbers;
        }
        
        return Collections.EMPTY_LIST;
    }
}

