import java.sql.Connection;

import play.Application;
import play.GlobalSettings;
import play.mvc.*;
import play.mvc.Http.RequestHeader;
import play.db.*;

import ua.banan.data.model.Pair;
import ua.banan.data.search.impl.SearchEngineImpl;
import ua.banan.web.ColorImgGenerator;
import ua.banan.web.MenuSelector;
import ua.banan.web.PageInfo;
import views.html.*;

import main.parser.com.Parsers;


public class Global extends GlobalSettings
{
    
	private static final ColorImgGenerator colorImgGenerator = new ColorImgGenerator();    
	
	@Override
	public void onStart(Application arg0)
	{		
		Connection conn = DB.getConnection();
		System.out.println("Starting...");

		Parsers.startParsing(conn);
				
//		try 
//		{
//			Thread.sleep(5 * 1000);
//		} 
//		catch (InterruptedException ignore) { }			
		
		SearchEngineImpl.setConnToToursDb(conn);
		super.onStart(arg0);		
		System.out.println("OK!");
	}

	@Override
	public void onStop(Application arg0)
	{		
		System.out.println("Stopping...");
		super.onStop(arg0);

		SearchEngineImpl.setConnToToursDb(null);
		System.out.println("OK!");
	}	

//	public static void closeConnection()
//    {
//        try
//        {
//            connToToursDb.close();
//        }
//        catch (SQLException e)
//        {
//            System.err.println("Caught IOException: " + e.getMessage());
//        }
//        
//        cpToToursDb.dispose();
//    }
    
//    public static void createConnection()
//    {
//        cpToToursDb = JdbcConnectionPool.create(
////            "jdbc:h2:tcp://localhost/~/bananH2Db", "testH2", "secret");
//        "jdbc:h2:tcp://localhost/~/bananH2Db", "webServer", "secret");
//        
////        cpToCountriesDb = JdbcConnectionPool.create(
////                "jdbc:h2:tcp://localhost/~/countryCityH2db", "GUEST", "secret");
//        try
//        {
//            connToToursDb 	  = cpToToursDb.getConnection();
//        }
//        catch (SQLException e)
//        {
//            System.err.println("Caught IOException: " + e.getMessage());
//        }    
//    }
    
    @Override
    public Result onHandlerNotFound(RequestHeader request) 
    {
    	Pair<String, String> colorImg = colorImgGenerator.getColorAndImg();
    	    	
    	PageInfo pi = new PageInfo();
    	pi.setUserTitle("Ой! Такой страницы нет!");
    	pi.setTitle("Banan Страница не найдена");
    	pi.setDescription("Описание - Banan Горящие туры Страница не найдена");
    	pi.setNoResultsFound(false);       
    	
    	String text = "No such page text \n No such page text \n No such page text \n No such page text \n No such page text \n No such page text \n No such page text \n No such page text \n No such page text \n No such page text \n No such page text \n No such page text \n No such page text \n No such page text \n ";
    	
        return Results.ok(main.render(pi, colorImg.getFirst(), new MenuSelector(MenuSelector.NONE), colorImg.getSecond(), null, null, null, null, text, null, controllers.Application.PAGE_TYPE_SEARCH));
    }

    @Override
    public Result onError(RequestHeader request, Throwable t) 
    {
    	Pair<String, String> colorImg = colorImgGenerator.getColorAndImg();
    	
    	PageInfo pi = new PageInfo();
    	pi.setUserTitle("Ой! Произошла ошибка! Попробуйте обновить страницу!");
    	pi.setTitle("Banan Произошла ошибка");
    	pi.setDescription("Описание - Banan Горящие туры Произошла ошибка");
    	pi.setNoResultsFound(false);   
    	
    	String text = "No such page text \n No such page text \n No such page text \n No such page text \n No such page text \n No such page text \n No such page text \n No such page text \n No such page text \n No such page text \n No such page text \n No such page text \n No such page text \n No such page text \n ";    	
    	
        return Results.ok(main.render(pi, colorImg.getFirst(), new MenuSelector(MenuSelector.NONE), colorImg.getSecond(), null, null, null, null, text, null, controllers.Application.PAGE_TYPE_SEARCH));
    }
    
}
