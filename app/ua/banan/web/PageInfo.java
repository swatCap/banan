package ua.banan.web;

public class PageInfo 
{
	private String userTitle;
	private String title;
	private String description;
	private String keywords;
	private boolean noResultsFound = false; // DISPLAY 'WE FOUND NO RESULTS BY YOUR REQUEST PLEASE SEE RECOMMENDED BELOW'
	
	public PageInfo() {}

	public String getUserTitle() {
		return userTitle;
	}

	public void setUserTitle(String userTitle) {
		this.userTitle = userTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public boolean isNoResultsFound() {
		return noResultsFound;
	}

	public void setNoResultsFound(boolean noResultsFound) {
		this.noResultsFound = noResultsFound;
	}
	
	
}
