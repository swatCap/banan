package ua.banan.data.search.filters;

import java.util.Calendar;
import java.util.Date;
import ua.banan.data.model.Pair;

public class DateParser 
{    
	private static Calendar calendar = Calendar.getInstance();

	
    public static Pair<Date, Date> findDatePeriod(String text) 
    {       
    	Date current = new Date();
    	
    	calendar.setTime(current);
    	calendar.roll(Calendar.MONTH, false);
    	
    	return new Pair<>(calendar.getTime(), current);
    }
    
}
