package ua.banan.data.search.filters;

import java.util.ArrayList;
import java.util.List;

import ua.banan.data.model.Pair;

public class TermFilter 
{
    /*
        List<ID, List<term>>
    */
    private List<Pair<Integer, List<String>>> termsWithIdsList;

    public TermFilter() {}            

    public TermFilter(List<Pair<Integer, List<String>>> termsWithIdsList) 
    {
        this.termsWithIdsList = termsWithIdsList;
    }

    public List<Pair<Integer, List<String>>> getTermsWithIdsList() 
    {
        return termsWithIdsList;
    }
    public void setTerms(List<Pair<Integer, List<String>>> termsWithIdsList) 
    {
        this.termsWithIdsList = termsWithIdsList;
    }

    
       
    public List<Integer> filter(String inputString)
    {
        if (inputString != null && !inputString.isEmpty() && termsWithIdsList != null)
        {            
            List<Integer> res = new ArrayList<>();
            
            for (Pair<Integer, List<String>> tokenPair : termsWithIdsList)
            {
                List<String> terms = tokenPair != null ? tokenPair.getSecond() : null;
                if (terms != null)
                {
                    for (String term : terms)
                    {                        
                        if (inputString.contains(term))
                        {
                            res.add(tokenPair.getFirst());
                            break;
                        }
                    }
                }                
            }
            
            return !res.isEmpty() ? res : null;
        }
        
        return null;
    }
}
