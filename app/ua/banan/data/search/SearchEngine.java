package ua.banan.data.search;

import java.util.List;

import ua.banan.data.model.Comment;
import ua.banan.data.model.TourFull;
import ua.banan.data.model.presentation.CountPresentation;
import ua.banan.web.PageInfo;

public interface SearchEngine 
{		
	public List<TourFull> getTours(List<Integer> countries, Integer moneyEnd);
	public List<TourFull> getTours(Integer countryId, Integer cityId);	
	public List<TourFull> getTours(int tourOpId);
			
	public List<TourFull> getRandomTours(int count);	
	
	public List<TourFull> getNearestTours();	
	public List<TourFull> getSeaTours();	
	public List<TourFull> getBusTours();
		
	public List<CountPresentation> getTopCountriesCountPresentation();
	public List<CountPresentation> getTopTourOpsCountPresentation();
		
	public String getText(Integer countryId, Integer cityId);	
	public String getText(int tourOpId);	
	
	public PageInfo getPageInfoForTourOp(int tourOpId);
	public PageInfo getPageInfoForCity(int cityId);	
	public PageInfo getPageInfoForCountry(int countryId);	
	
	public void saveFeedbackComment(Comment c);
	public void savePartnerComment(Comment c);
	
	public List<TourFull> getDblTours();
	public List<TourFull> getFeedTours();
	
	public List<TourFull> getContinentTours(short continentId);
	public List<TourFull> getToursFromCity(int cityId);
}
