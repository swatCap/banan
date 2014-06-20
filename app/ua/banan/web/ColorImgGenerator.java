package ua.banan.web;

import java.util.Date;
import java.util.Random;

import ua.banan.data.model.Pair;

public class ColorImgGenerator 
{
	public static final String[] searchColors = {"#c98e54", "#2ba1f3", "#527480",
													 "#4c4ea1", "#388bad", "#9d2663", 
													 "#878787", "#975ba6", "#d98a35", 
													 "#bf8324", "#387794", "#e6986e",
													 "#67b3d7", "#71a397", "#9f4a86",
													 "#7f93e8", "#6a9ea3", "#d97f0a",
													 "#558893", "#d95f3e", "#96853f",
													 "#01c1f0", "#376979", "#5f7e76",
													 "#b0bbc4", "#d59e58", "#01447a"};
	
	private static final Random rnd = new Random(new Date().getTime());
	
	public Pair<String, String> getColorAndImg()
	{
		int random = rnd.nextInt(searchColors.length);
		
		return new Pair<String, String>(searchColors[random], "/assets/images/main_bg/" + random + ".jpg");		    
	}
}
