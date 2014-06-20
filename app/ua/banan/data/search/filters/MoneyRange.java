package ua.banan.data.search.filters;

import ua.banan.data.model.Pair;

public class MoneyRange
{
	private static final float UAH_USD_EXCHANGE_RATE = 9;
	private static final float EUR_USD_EXCHANGE_RATE = 1.5F;
                        
	public static final int CUURENCY_UAH = 0;
	public static final int CUURENCY_USD = 1;
	public static final int CUURENCY_EUR = 2;
	public static final int CUURENCY_RUR = 3;

        private Pair<Integer, Integer> range;
	private int currency = 1;
	
	public MoneyRange()
	{
		super();		
	}
	
	public MoneyRange(Pair<Integer, Integer> range, int currency)
	{
		super();
		this.range = range;
		this.currency = currency;
	}

	public Pair<Integer, Integer> getRange()
	{
		return range;
	}

	public void setRange(Pair<Integer, Integer> range)
	{
		this.range = range;
	}

	public int getCurrency()
	{
		return currency;
	}

	public void setCurrency(int currency)
	{
		this.currency = currency;
	}
	
        
        public Pair<Integer, Integer> convertToUsd()
        {         
            Integer from = range != null ? range.getFirst() : null;
            Integer to   = range != null ? range.getSecond() : null;
            
            switch (currency)
            {
                case CUURENCY_EUR :
                {                    
                    from = from != null ? ((int)(from * EUR_USD_EXCHANGE_RATE)) : null;
                    to   = to   != null ? ((int)(to * EUR_USD_EXCHANGE_RATE))   : null;                    
                }  
                break;
                
                case CUURENCY_UAH :
                {                    
                    from = from != null ? ((int)(from * UAH_USD_EXCHANGE_RATE)) : null;
                    to   = to   != null ? ((int)(to * UAH_USD_EXCHANGE_RATE))   : null;                    
                }  
                break;
            }
            
            return new Pair<>(from, to);
        }
	
	
}
