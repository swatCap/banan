package controllers;

import java.util.List;
import java.util.Map;

import play.api.mvc.AnyContent;
import play.mvc.*;

import ua.banan.data.model.Comment;
import ua.banan.data.model.Pair;
import ua.banan.data.model.TourFull;
import ua.banan.data.search.SearchEngine;
import ua.banan.data.search.impl.SearchEngineImpl;
import ua.banan.web.ColorImgGenerator;
import ua.banan.web.MenuSelector;
import ua.banan.web.PageInfo;

import views.html.*;

public class Application extends Controller 
{  				
	public static final int PAGE_TYPE_SEARCH 				= 0;
	public static final int PAGE_TYPE_FEEDBACK 			= 1;	
	public static final int PAGE_TYPE_PARTNERS 			= 2;
	public static final int PAGE_TYPE_ABOUT				= 3;
		
	private static final int RECOMMENDED_TOURS_COUNT 						 = 16;
	private static final int MINIMAL_FOUND_TOURS_SIZE_TO_SHOW_RECOMMENDED = 10;
	
	private static final int DAYS_BEFORE_LEAVE 							 = 2;
	
			
	private static final SearchEngine searchEngine = new SearchEngineImpl(DAYS_BEFORE_LEAVE);	
	
	private static final ColorImgGenerator colorImgGenerator = new ColorImgGenerator();
	
	public static play.api.mvc.Action<AnyContent> favicon = Assets.at("/public/images", "favicon.ico");

    
    public static Result main() 
    {    	
//    	String userAgent = request().getHeader("User-Agent");
//    	System.out.println("userAgent: " + userAgent);
    	
    	Pair<String, String> colorImg = colorImgGenerator.getColorAndImg();    	    
    	    
    	PageInfo pi = new PageInfo();
    	pi.setUserTitle("<h1>Ближайшие туры</h1>");
    	pi.setTitle("Banan - Все горящие туры Украины! | Ближайшие туры ");
    	pi.setDescription("На Банане представлен огромный выбор горящих туров со всех интернет-ресурсов Украины 2014 года." + 
    	" Все самые выгодные предложения на одном сайте - вот так просто. Отдыхай на Банане!");
    	
    	List<TourFull> foundTours = searchEngine.getNearestTours();
    	List<TourFull> recommended = null;
    	    	
    	if (foundTours == null || foundTours.isEmpty())
    	{
    		pi.setNoResultsFound(true); 
    		foundTours = searchEngine.getRandomTours(RECOMMENDED_TOURS_COUNT);
    	}
    	
    	if (foundTours != null && foundTours.size() < MINIMAL_FOUND_TOURS_SIZE_TO_SHOW_RECOMMENDED)
    	{
    		recommended = searchEngine.getRandomTours(RECOMMENDED_TOURS_COUNT);
    	}
    	
//    	
    	String text = "Здесь у нас представлены ближайшие по дате горящие путевки." + 
    	" Не секрет, что горящий тур - это вид туристических путевок, которые по ряду причин продаются дешевле." + 
		" Будь то близость рейса, отбытия поезда либо устаревающая бронь в отеле - " + 
    	"данный тур не был продан в фиксированые сроки и с каждым днем он все больше теряет в цене. <p>" +
		"Туристические компании, туристические операторы либо агентства изо всех сил пытаются продать" +
    	" Вам такие путевки по двум простым причинам: они просто теряю деньги за конкретный тур и продажа " + 
		"таких путевок создает для туристической компании своеобразную рекламную акцию. <p>" +
    	"Очень часто цена путевки падает за свою себестоимость, но продажа ее не останавливается" + 
		" из-за привлечения потенциальных клиентов. Часто можно найти прекрасное предложение: " + 
    	"<a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B4%D0%BB%D1%8F_%D0%B4%D0%B2%D0%BE%D0%B8%D1%85'>тур на двоих</a>, " + 
		"питание - <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D1%81_%D0%BF%D0%B8%D1%82%D0%B0%D0%BD%D0%B8%D0%B5%D0%BC'>«Все включено»</a>, " + 
    	"7 или 10 ночей и все это по более чем доступной цене! <p>" +
    	"Банан собирает для Вас такие лакомы предложения и просто показывает Вам самы ближайшие и выгодные - вот так просто! " + 
    	"Мы верим, что каждый человек, вырвавшийся на такой выгодный и приятный для себя отдых, отлично проведет время и еще долго будет вспоминать те счастливые дни.";
    	
    	    	
        return ok(main.render(
				        		pi,
				        		colorImg.getFirst(),
				        		new MenuSelector(MenuSelector.HOT_TOURS),
				        		colorImg.getSecond(), 
				        		searchEngine.getTopCountriesCountPresentation(),
				        		searchEngine.getTopTourOpsCountPresentation(),
				        		foundTours,
				        		recommended,
				        		text, 
				        		//"44186513",
				        		"71319358",
				        		PAGE_TYPE_SEARCH));
    }         

    public static Result about() 
    {    	    	
    	Pair<String, String> colorImg = colorImgGenerator.getColorAndImg();
    	    
    	PageInfo pi = new PageInfo();
    	pi.setUserTitle("<h1>О проекте Banan</h1>");
    	pi.setTitle("Banan - Все горящие туры Украины! | О нас - чем занимается Banan?");
    	pi.setDescription("Banan - это автоматический агрегатор горящих туров и путевок представленных на всех украинских интернет-ресурсах 2014 года. Отдыхай на Банане!");
    	pi.setNoResultsFound(false);   
    	    	    	
        return ok(main.render(
				        		pi,
				        		colorImg.getFirst(),
				        		new MenuSelector(MenuSelector.ABOUT),
				        		colorImg.getSecond(),
				        		searchEngine.getTopCountriesCountPresentation(), 
				        		searchEngine.getTopTourOpsCountPresentation(), 
				        		null,
				        		searchEngine.getRandomTours(RECOMMENDED_TOURS_COUNT),
				        		null, 
				        		null,
				        		PAGE_TYPE_ABOUT));
    }     
    
    public static Result sea() 
    {    	    	
    	Pair<String, String> colorImg = colorImgGenerator.getColorAndImg();    	    	  
    	    
    	PageInfo pi = new PageInfo();
    	pi.setUserTitle("<h1>Пляжные туры</h1>");
    	pi.setTitle("Banan | Пляжные туры | Все горящие пляжные туры Украины!");
    	pi.setDescription("У нас на Банане собраны все горящие пляжные туры со всех интернет-ресурсов Украины 2014 года." + 
    					" Вот так просто, огромный выбор на одном сайте. Отдыхай на Банане!");
    	
    	List<TourFull> foundTours = searchEngine.getSeaTours();
    	List<TourFull> recommended = null;
    	
    	if (foundTours == null || foundTours.isEmpty())
    	{
    		pi.setNoResultsFound(true); 
    		foundTours = searchEngine.getRandomTours(RECOMMENDED_TOURS_COUNT);
    	}
    	
    	if (foundTours != null && foundTours.size() < MINIMAL_FOUND_TOURS_SIZE_TO_SHOW_RECOMMENDED)
    	{
    		recommended = searchEngine.getRandomTours(RECOMMENDED_TOURS_COUNT);
    	}
    	
    	String text = "Мало что сравнимо с отдыхом на берегу моря после череды рабочих городских будней." + 
    	" Большинство семей сформировало традицию, раз за год отправляются на отдых в отель на побережье." + 
    	" Кого-то привлекает возможность посетить экзотические страны, кто-то любит купаться в теплой воде," + 
    	" кому-то необходим морской воздух, а кто-то просто не представляет своей жизни без моря. <p>" +
    	"Каждый день на пляжном курорте тянется очень долго и лениво, тяжело себе отказать в этом прекрасном состоянии. " +
    	"А яркие и незабываемые ночи часто перерастают в легендарные истории, " + 
    	"которые вы еще не один год будете вспоминать за рабочим местом, или дома в кругу друзей. <p>" +
    	"Если вы себя относите к тем людям для которых «отдых = море», то мы с радостью поможем вам приблизить свою мечту." + 
    	" <a href='/'>Горящие туры</a> - это идеальная возможность сохранить некоторе количество денег и потратить их уже находясь на курорте. " + 
    	"Никогда не лишайте себя этой радости. Пускай другие думают о вашем отдыхе!";
    	    	    	
        return ok(main.render(
				        		pi, 
				        		colorImg.getFirst(), 
				        		new MenuSelector(MenuSelector.SEA_TOURS),
				        		colorImg.getSecond(),
				        		searchEngine.getTopCountriesCountPresentation(),
				        		searchEngine.getTopTourOpsCountPresentation(), 
				        		foundTours, 
				        		recommended,
				        		text, "71319358",
				        		PAGE_TYPE_SEARCH));
    }     
    
    
    public static Result bus() 
    {    	    	
    	Pair<String, String> colorImg = colorImgGenerator.getColorAndImg();
    	
    	PageInfo pi = new PageInfo();
    	pi.setUserTitle("<h1>Автобусные туры</h1>");
    	pi.setTitle("Banan Автобусные туры");
    	pi.setDescription("Описание - Banan Горящие туры Автобусные");
    	
    	List<TourFull> foundTours = searchEngine.getBusTours();
    	List<TourFull> recommended = null;
    	
    	if (foundTours == null || foundTours.isEmpty())
    	{
    		pi.setNoResultsFound(true); 
    		foundTours = searchEngine.getRandomTours(RECOMMENDED_TOURS_COUNT);
    	}
    	
    	if (foundTours != null && foundTours.size() < MINIMAL_FOUND_TOURS_SIZE_TO_SHOW_RECOMMENDED)
    	{
    		recommended = searchEngine.getRandomTours(RECOMMENDED_TOURS_COUNT);
    	}

    	String text = "Пожалуй, ни один вид отдыха никогда не заменит автобусных туров. В них кроется какой-то особый шарм." + 
    	" Такой вид отдыха нельзя назвать активным, но и для любителей проводить отпуск не вставая с дивана он не подойдет. <p> " + 
		" Каждая такая поездка дарит кучу новых знакомых, с которыми вы проживаете эту прекрасную маленькую сказку. " +     	
		" Столько общения не бывает при перелетах и пассивном отдыхе в отеле. <p> Отдельно стоит выделить категорию людей, " + 
		"которая предпочитает автобусный туризм. Это как заядлые любители автобусных туров, так и молодые студенты, жаждущие путешествий." + 
		" Все это создает неповторимость впечатлений, которые осядут в вашей голове после поездки. <p>" + 
		" Так же стоит отметить, что горящие автобусные туры часто бывают очень доступными, потому отлично выручают людей, " + 
		"для которых путешествия, туризм, общение и познание новых культур является ключевым стремлением в жизни, однако финансы" + 
		" не позволяют ездить так часто, как бы им хотелось.";
    	    	    	
        return ok(main.render(
				        		pi,
				        		colorImg.getFirst(),
				        		new MenuSelector(MenuSelector.BUS_TOURS),
				        		colorImg.getSecond(),
				        		searchEngine.getTopCountriesCountPresentation(),
				        		searchEngine.getTopTourOpsCountPresentation(), 
				        		foundTours, 
				        		recommended,
				        		text, "71319358", 
				        		PAGE_TYPE_SEARCH));
    }
    
    public static Result partner() 
    {    	    	
    	Pair<String, String> colorImg = colorImgGenerator.getColorAndImg();
    	
    	PageInfo pi = new PageInfo();
    	pi.setUserTitle("<h1>Сотрудничество</h1>");
    	pi.setTitle("Banan Сотрудничество");
    	pi.setDescription("Banan - Горящие туры Сотрудничество");    	    
    	
    	String text = null;
    	    	    	
        return ok(main.render(
				        		pi,
				        		colorImg.getFirst(),
				        		new MenuSelector(MenuSelector.PARTNERS),
				        		colorImg.getSecond(),
				        		searchEngine.getTopCountriesCountPresentation(),
				        		searchEngine.getTopTourOpsCountPresentation(), 
				        		null, 
				        		searchEngine.getRandomTours(RECOMMENDED_TOURS_COUNT),
				        		text, null, 
				        		PAGE_TYPE_PARTNERS));
    }
    
    public static Result feedback() 
    {    	    	
    	Pair<String, String> colorImg = colorImgGenerator.getColorAndImg();
    	
    	PageInfo pi = new PageInfo();
    	pi.setUserTitle("<h1>Оставить отзыв</h1>");
    	pi.setTitle("Banan Оставить отзыв");
    	pi.setDescription("Banan - Горящие туры Оставить отзыв");    	    
    	    	    	    	
        return ok(main.render(
				        		pi,
				        		colorImg.getFirst(),
				        		new MenuSelector(MenuSelector.FEEDBACK),
				        		colorImg.getSecond(),
				        		searchEngine.getTopCountriesCountPresentation(),
				        		searchEngine.getTopTourOpsCountPresentation(), 
				        		null, 
				        		searchEngine.getRandomTours(RECOMMENDED_TOURS_COUNT),
				        		null, 
				        		null,
				        		PAGE_TYPE_FEEDBACK));
    }
    
    
    public static Result commentSubmit()
    {
    	Map<String, String[]> map = request().body().asFormUrlEncoded();
    	
    	String name = getFromUrlEncoded("nm", map);
    	String mail = getFromUrlEncoded("em", map);
    	String comment = getFromUrlEncoded("cm", map);
    	
//    	System.out.println(name);
//    	System.out.println(mail);
//    	System.out.println(comment);    	
    	
    	Comment c = new Comment();
    	c.setComment(comment);
    	c.setMail(mail);
    	c.setName(name);
    	
    	searchEngine.saveFeedbackComment(c);
        	   
    	return ok("Got");
    }
    
    public static Result partnerSubmit()
    {
    	Map<String, String[]> map = request().body().asFormUrlEncoded();
    	
    	String name = getFromUrlEncoded("nm", map);
    	String mail = getFromUrlEncoded("em", map);
    	String phone = getFromUrlEncoded("ph", map);
    	String comment = getFromUrlEncoded("cm", map);    	
    	
//    	System.out.println(name);
//    	System.out.println(mail);
//    	System.out.println(phone);
//    	System.out.println(comment);
    	
    	Comment c = new Comment();
    	c.setComment(comment);
    	c.setMail(mail);
    	c.setName(name);
    	c.setPhone(phone);
    	
    	searchEngine.savePartnerComment(c);
    	        	   
    	return ok("Got");
    }
    
    
    private static String getFromUrlEncoded(String key, Map<String, String[]> map)
    {
    	if (map != null && !map.isEmpty())
    	{
    		String[] arr = map.get(key);
    		
    		return arr != null && arr.length > 0 ? arr[0] : null;
    	}
    	
    	return null;
    }
    
    public static Result searchPage(PageInfo pi, String text, List<TourFull> foundTours) 
    {    	    	
    	Pair<String, String> colorImg = colorImgGenerator.getColorAndImg();    	    	      	    
    	
    	List<TourFull> recommended = null;
    	
    	if (foundTours == null || foundTours.isEmpty())
    	{
    		pi.setNoResultsFound(true); 
    		foundTours = searchEngine.getRandomTours(RECOMMENDED_TOURS_COUNT);
    	}
    	
    	if (foundTours != null && foundTours.size() < MINIMAL_FOUND_TOURS_SIZE_TO_SHOW_RECOMMENDED)
    	{
    		recommended = searchEngine.getRandomTours(RECOMMENDED_TOURS_COUNT);
    	}
    	    	    	    	    	    	    	  
        return ok(main.render(	pi, 
				        		colorImg.getFirst(), 
				        		new MenuSelector(MenuSelector.NONE),
				        		colorImg.getSecond(),
				        		searchEngine.getTopCountriesCountPresentation(),
				        		searchEngine.getTopTourOpsCountPresentation(), 
				        		foundTours, 
				        		recommended,
				        		text, "71319358", 
				        		PAGE_TYPE_SEARCH));
    }     
    
    
    //////////////////////////////////////////////////////TOUR OPS/////////////////////////////////////////////////////////////////////////////////////////
    
    public static Result tourOperatorPage(int tourOpId) 
    {    	    	
    	PageInfo pi = searchEngine.getPageInfoForTourOp(tourOpId);    	
    	
    	return searchPage(pi, searchEngine.getText(tourOpId), searchEngine.getTours(tourOpId));    	
    }     
   
       
    public static Result poehalisnami()				 {return tourOperatorPage(8);}
    public static Result smgp()				 {return tourOperatorPage(11);}     
    public static Result bambarbia()				 {return tourOperatorPage(5);}
    public static Result tui()				 {return tourOperatorPage(1);}
    public static Result happyColumbus()   {return tourOperatorPage(2);}
    public static Result tourOpHottours()  {return tourOperatorPage(3);}
    public static Result turtess()  		 {return tourOperatorPage(7);}
    public static Result otpusk()   		 {return tourOperatorPage(9);}
    public static Result turne()    		 {return tourOperatorPage(10);}
    public static Result tez()    		 	 {return tourOperatorPage(12);}
    
    //////////////////////////////////////////////////////TOUR OPS END/////////////////////////////////////////////////////////////////////////////////////////
    
                  
    ///////////////////////////////////////////////////////COUNTRIES///////////////////////////////////////////////////////////////////////////////

    public static Result countryPage(int countryId) 
    {    	   
    	PageInfo pi = searchEngine.getPageInfoForCountry(countryId);   	
    	
    	return searchPage(pi, searchEngine.getText(countryId, null), searchEngine.getTours(countryId, null));    	
    }    
        
    public static Result turkey()     				{return countryPage(109);}
    public static Result egypt()     				{return countryPage(61);}
    public static Result greece()     				{return countryPage(18);}
    public static Result bulgaria()     			{return countryPage(121);}
    public static Result spain()     				{return countryPage(35);}
    public static Result croatia()     			{return countryPage(202);}
    public static Result thailand()     			{return countryPage(9);}
    public static Result tunisia()     			{return countryPage(108);}
    public static Result emirates()     			{return countryPage(14);}
    public static Result cyprus()     				{return countryPage(117);}
    public static Result italy()     				{return countryPage(13);}
    public static Result czech()     				{return countryPage(114);}
    public static Result montenegro()     			{return countryPage(133);}
    public static Result hungary()     			{return countryPage(118);}
    public static Result austria()     			{return countryPage(127);}
    public static Result france()     				{return countryPage(12);}
    public static Result germany()     			{return countryPage(29);}
    public static Result sriLanka()     			{return countryPage(132);}
    public static Result poland()    			    {return countryPage(110);}
    public static Result israel()     				{return countryPage(34);}
    public static Result maldives()    			{return countryPage(52);}
    public static Result netherlands()     		{return countryPage(33);}
    public static Result jordan()     				{return countryPage(191);}
    public static Result vietnam()     			{return countryPage(11);}
    public static Result mauritius()     			{return countryPage(84);}
    public static Result seychelles()     			{return countryPage(98);}
    public static Result slovenia()     			{return countryPage(129);}
    public static Result georgia()     			{return countryPage(124);}
    public static Result morocco()     			{return countryPage(99);}
    public static Result dominicanRepublic()      {return countryPage(170);}
    public static Result indonesia()     			{return countryPage(45);}
    public static Result belgium()     			{return countryPage(56);}
    public static Result usa()     				{return countryPage(23);}
    public static Result singapore()     			{return countryPage(42);}
    public static Result slovakia()     			{return countryPage(119);}
    public static Result china()     				{return countryPage(4);}
    public static Result luxembourg()     			{return countryPage(145);}
    public static Result estonia()    			    {return countryPage(140);}
    public static Result serbia()     				{return countryPage(120);}
    public static Result ukraine()     			{return countryPage(112);}
    public static Result monaco()     				{return countryPage(154);}
    public static Result russia()    			    {return countryPage(20);}
    public static Result latvia()     				{return countryPage(111);}
    public static Result switzerland()     		{return countryPage(59);}
    public static Result romania()     			{return countryPage(123);}
    public static Result cuba()     				{return countryPage(226);}
    ///////////////////////////////////////////////////////COUNTRIES END///////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////INTERESTING PAGES START ////////////////////////////////////////////////////////
    
    public static Result dblTours()
    {
    	PageInfo pi = new PageInfo();
    	
    	pi.setUserTitle("<h1>Туры на двоих</h1>");
    	pi.setTitle("Banan | Туры на двоих | Горящие туры на двоих!");
    	pi.setDescription("На Банане представлен огромный выбор горящих туров на двоих со всех интернет-ресурсов Украины 2014 года. " + 
    	"Все самые выгодные предложения на одном сайте - вот так просто. Отдыхай на Банане!");
    	
    	String text = "<p>В данном разделе мы представляем Вам туры на двоих по самым выгодным ценам. Отправится вдвоем в одну из самых популярных и экзотических стран мира по экстремально низким ценам горящего предложения — что может быть лучше?<p>Мы собрали для Вас все такие горящие путевки на одно странице. Банан делает поиск вариантов отдохнуть дешевле максимально простым и не долгосрочным.<p>Горящие путевки на двоих — это самый популярный рынок туристических путевок, поэтому ситуация здесь меняется каждый час. Банан круглосуточно следит за такими турами и предоставляет Вам самую актуальную информацию. Мы не продаем путевки, а только лишь собираем их в одном удобном для Вас месте, что бы вы могли выбрать максимально выгодный для себя отдых.<p>Ведь это очень важно, находясь в райском уголке нашей планеты, видеть рядом с собой самого близкого человека и делить с ним прекрасный не дорогостоящий отдых по горящему предложению. Вот почему Банан собирает для Вас все такие путевки со всех туристических интернет-ресурсов Украины — вот так просто. Отдыхай на Банане!</p>";
    	
    	return searchPage(pi, text, searchEngine.getDblTours()); 
    }
    
    public static Result feedTours()
    {
    	PageInfo pi = new PageInfo();
    	
    	pi.setUserTitle("<h1>Туры с питанием</h1>");
    	pi.setTitle("Banan | Все включено | Все горящие туры с питанием!");
    	pi.setDescription("На Банане представлен огромный выбор горящих туров с питанием типа «Все включено» со всех интернет-ресурсов Украины 2014 года." + 
    	" Все самые выгодные предложения на одном сайте - вот так просто. Отдыхай на Банане!");
    	
    	String text = "<p>Здесь Банан собрал для Вас все выгодные горящие предложения с питанием. Любите отдыхать по системе «Все включено»? Вам сюда.<p>На период сладостного отдыха можно не вспоминать о том, что мы все бережем и следим за своей фигурой. Дать душе волю, ведь это всего на недельку :-). А по возвращению домой мы все наверстаем на работах и в спортивных залах.<p>Огромный выбор самой разнообразной еды: экзотической, калорийной, полезной, красивой и именно в тех объемах, которых требует наша славянская душа :-). Также не забываем про огромный ассортимент алкогольных и безалкогольных напитков. Что еще нужно чтобы весело провести неделю (или дольше) долгожданного отдыха в одном из самых прекрасных уголков планеты. <p>Банан не продает туры, он собирает для вас именно такие предложения по самым выгодным ценам со всех туристических интернет-ресурсов Украины. Каждый день самые интересные предложения с любыми вариантами питания на одном сайте — вот так просто. Отдыхай на Банане!</p>";
    	
    	return searchPage(pi, text, searchEngine.getFeedTours()); 
    }
    
    public static Result fromKiev()
    {
    	PageInfo pi = new PageInfo();
    	
    	pi.setUserTitle("<h1>Туры из Киева</h1>");
    	pi.setTitle("Banan | Украина | Киев | Горящие туры и путевки из Киева 2014");
    	pi.setDescription("На Банане собраны все горящие путевки и туры из Киева. Огромный выбор горящих предложений со всех интернет-ресурсов Украиным 2014. Отдыхай на Банане!");
    	    	
    	String text = "<h1>Тур из Киева</h1><p>Из стольного града <strong>Киева</strong> очень просто отправиться в путешествие по <a href='/'>горящему туру</a>. Заходим на <strong>gdebanan.com</strong> и выбираем самый подходящий нам тур и отправляемся в путешествие мечты. Именно так, <strong>Банан</strong> собирает все самые выгодные горящие туры из <strong>Киева</strong> и размещает их на одном украинском ресурсе.</p><p>Устали от активного, киевляне поймут, ритма жизни, постоянное движение толпы людей, стоящего в городе-герое смога — вам срочно нужно отправляться в путешествие по горящему туру. Турция с ее великолепными отелями, Египет с уникальным климатом, Тунис с его прекрасной природой, экзотический Таиланд, далекая Шри-Ланка, великая Греция либо инновационные Эмираты — все эти великолепные курорты в одном шаге от вас. <strong>Банан</strong> поможет ;-).</p><p>В <strong>Киеве</strong> работает чудовищное количество турагентств и туроператоров. И каждое из них может поделиться с вами сравнительно небольшим количеством горящих предложений, а как известно горящий тур — это тур который волей случая остался не проданным и он совсем не обязательно устроит ваше требование. Именно по этой причине <strong>Банан</strong> собирает туры с выездом из <strong>Киева</strong> со все украинских туристических интернет-ресурсов и освобождает вас от потребности перебирать сотни интернет-страниц. Вот так просто. Отдыхай на <strong>Банане</strong>!</p>";
    	
    	return searchPage(pi, text, searchEngine.getToursFromCity(49713)); 
    }
    
    //////////////////////////////////////////////////////////INTERESTING PAGES END ////////////////////////////////////////////////////////
    
    
    
    //////////////////////////////////////////////////////////CONTINENTS PAGES START ////////////////////////////////////////////////////////

    public static Result australiaAndOceania() 
    {    	   
    	PageInfo pi = new PageInfo();

    	pi.setUserTitle("<h1>Туры в Австралию и Океанию</h1>");
    	pi.setTitle("Banan | Австраля и Океания | Горящие туры в Австралию и Океанию 2014");
    	pi.setDescription("На Банане собраны все горящие путевки и туры в Австралию и Океанию. Огромный выбор горящих предложений со всех интернет-ресурсов Украиным 2014. Отдыхай на Банане!");
    	
    	String text = "<h1>Туры в Австралию и Океанию</h1><p>До чего же часто бывает невыносимым скучное сидение в офисе, на работе рутинные будни, проведенные без отдыха в повседневной суете. Возникает желание просто так все взять, бросить, и уехать подальше, далеко-далеко, на другой край света! Ну а что может сравниться с таким прекрасным “концом света”, как <strong>Австралией</strong> и ближайшими архипелагами островов!? Здесь есть все, чего только можно пожелать человеку, желающему отдохнуть от скуки – ажурные, как вышивка, островки <strong>Большого Барьерного Рифа</strong>, суриковые красноты <strong>Улуру</strong>, океанские волны, как с картинки, коалы, которые мило протягиваю свои мягкие лапы... </p><p>А <strong>Сидней</strong> и <strong>Брисбен</strong>! Да здесь просто нельзя не побывать! Но это только лишь <strong>Австралия</strong>. Рядом же расположены острова, которые манят неизведанными туристическими искушениями. Это и <strong>Новая Зеландия</strong>, и <strong>Фиджи</strong>, и <strong>Папуа-Новая Гвинея</strong>. Также Вас заворожат красоты <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%98%D0%BD%D0%B4%D0%BE%D0%BD%D0%B5%D0%B7%D0%B8%D1%8E'>Индонезии</a>, которые непременно перекликнутся с достопримечательностями <strong>Малайзии</strong>. Сегодня купить горящий тур на другой конец мира – реальнее, чем когда бы то ни было. <a href='/'>Банан</a> это доказывает каждый день, предоставляя доступ к замечательным горящим путевкам, от  <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B4%D0%BB%D1%8F_%D0%B4%D0%B2%D0%BE%D0%B8%D1%85'>семейных</a> до <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D1%81_%D0%BF%D0%B8%D1%82%D0%B0%D0%BD%D0%B8%D0%B5%D0%BC'>лакшери</a>, в страны этого превосходного континента Земли – <strong>Австралии и Океании</strong>! Наслаждайтесь!</p>";
    	
    	return searchPage(pi, text, searchEngine.getContinentTours((short)5));    	
    }
    
    public static Result europe() 
    {    	   
    	PageInfo pi = new PageInfo();

    	pi.setUserTitle("<h1>Туры в Европу</h1>");
    	pi.setTitle("Banan | Европа | Горящие туры в Европу 2014");
    	pi.setDescription("На Банане собраны все горящие путевки и туры в Европу. Огромный выбор горящих предложений со всех интернет-ресурсов Украиным 2014. Отдыхай на Банане!");
    	
    	String text = "<h1>Тур в Европу</h1><p>Величественная <strong>Европа</strong>. Собрание самых передовых государств мира — это они многие столетия дарили нашему миру науку, культуру, изобразительное искусство, музыку и многое другое. Так сказать колыбель всего общепризнанного и рационального.</p> <p>Как должно быть приятно поехать по <a href='/'>горящий путевке</a> в любое государство сего собрания. Только подумайте, как это прекрасно, отправится в <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%98%D1%81%D0%BF%D0%B0%D0%BD%D0%B8%D1%8E'>Испанию</a> и прогуляться по великолепным улочкам Барселоны, либо посетить <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2%D0%BE_%D0%A4%D1%80%D0%B0%D0%BD%D1%86%D0%B8%D1%8E'>Францию</a> и скушать теплый круасан с чашечкой кофе неподалеку от Монмартра, или же отправится в <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%93%D0%B5%D1%80%D0%BC%D0%B0%D0%BD%D0%B8%D1%8E'>Германию</a> и посетить великолепно чистые и ухоженные города этого процветающего государства. Не стоит забывать об уникальной архитектуре <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%98%D1%82%D0%B0%D0%BB%D0%B8%D1%8E'>итальянского</a> Рима, который несомненно пленит вас своим великолепием и глубиной исторических построек, кажется все исторические личности той эпохи совсем рядом и вот-вот возьмут вас за руку и отведут на экскурсию по своим увлекательным приключениям. <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%93%D1%80%D0%B5%D1%86%D0%B8%D1%8E'>Греция</a> — это просто учебник истории наяву, только этот урок вы запомните на всю жизнь и будете вспоминать с огромной теплотой. А как же старая добрая Англия, или как ее сейчас  велено называть — Великобритания! Это государство в свое имело господствующее положение во всем мире, без сомнения там есть на что посмотреть.</p><p>А как насчет посещения, скажем, сразу нескольких европейских государств за один совершенно не длительный отпуск, повидать достопримечательности сразу нескольких европейских городов? На такой случай на <strong>Банане</strong> представлен широкий выбор <a href='/%D0%B0%D0%B2%D1%82%D0%BE%D0%B1%D1%83%D1%81%D0%BD%D1%8B%D0%B5_%D1%82%D1%83%D1%80%D1%8B'>Автобусных туров</a> по <strong>Европе</strong>. Любой маршрут, любые города и страны в какой угодно последовательности — все это можно найти на <strong>Банане</strong>!</p> <p>Мы не занимаемся продажей горящих туров, мы всего лишь хотим сделать процедуру поиска горящих предложений простой и приятной. Мы собираем их все тут, на одном украинском ресурсе, вот так просто. Отдыхай на <strong>Банане</strong>!</p>";    	
    	
    	return searchPage(pi, text, searchEngine.getContinentTours((short)3));    	
    }
    
    public static Result asia() 
    {    	   
    	PageInfo pi = new PageInfo();

    	pi.setUserTitle("<h1>Туры в Азию</h1>");
    	pi.setTitle("Banan | Азия | Горящие туры в Азию 2014");
    	pi.setDescription("На Банане собраны все горящие путевки и туры в Азию. Огромный выбор горящих предложений со всех интернет-ресурсов Украиным 2014. Отдыхай на Банане!");
    	
    	String text = "<h1>Тур в Азию</h1><p><strong>Азия</strong> — колыбель многих великих цивилизаций, континент на котором живет самое большое количество людей на земле. <strong>Азия</strong> и азиаты завораживают богатствам своей культуры и самобытной жизни. Азиатские обычаи красивы, одеяния пестры, а культура полна уникальных произведений и выдающихся деятелей этой самой культуры. Азиатский мир становится все богаче и богаче с каждым годом, старушка <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%95%D0%B2%D1%80%D0%BE%D0%BF%D1%83'>Европа</a>, кажется, изживает себя и на смену ей приходит <strong>Азия</strong> с ее сильными и жаждущими развития народами.</p><p>Безусловно что в <strong>Азии</strong> найдется огромное количество мест, которые необходимо посетить. А <a href='/'>горящее предложение</a> на <strong>Банане</strong> будут как нельзя к стати. Кто откажется от выгодного путешествия в инновационные <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%AD%D0%BC%D0%B8%D1%80%D0%B0%D1%82%D1%8B'>Арабские Эмираты</a>? Это государство совершило немыслимый промышленный и социальный скачок, <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%AD%D0%BC%D0%B8%D1%80%D0%B0%D1%82%D1%8B'>ОАЭ</a>, бывшее когда-то пустынным побережьем, теперь является одним из самых передовых и смотрящих в будущее государств, а несколько рекордов Гиннеса довершаю огромный интерес к этому государству на берегу персидского залива.</p> <p>Горящий тур в <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%A2%D1%83%D1%80%D1%86%D0%B8%D1%8E'>Турцию</a> — это ваш пропуск в райский уголок на период вашего отпуска. <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%A2%D1%83%D1%80%D1%86%D0%B8%D1%8E'>Турция</a> является лидером в своем деле, а именно в деле предоставления услуг высшего качества для тех, кто мечтает отдохнуть на высшем уровне. Гостеприимная турецкая земля порадует вас привычным климатом и огромным количеством респектабельных гостиниц и отелей.</p> <p><a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%BD%D0%B0_%D0%A8%D1%80%D0%B8-%D0%9B%D0%B0%D0%BD%D0%BA%D1%83'>Шри-Ланка</a> раскроет перед вами все свои прелести и окунет в мир удивительных красок и впечатлений на все жизнь. Удивительный мир, после жаркого и тяжелого города, поспособствует восстановлению сил и возврату хорошего настроения.</p><p>А здесь еще и <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%A2%D0%B0%D0%B8%D0%BB%D0%B0%D0%BD%D0%B4'>Таиланд</a>...ну что здесь говорить, <strong>Азия</strong> всегда ждет гостей, а <strong>Банан</strong> поможет с поиском горящих туров туда ;-). Вот так просто.</p>"; 
    	
    	return searchPage(pi, text, searchEngine.getContinentTours((short)2));    	
    }
    
    public static Result africa() 
    {    	   
    	PageInfo pi = new PageInfo();

    	pi.setUserTitle("<h1>Туры в Африку</h1>");
    	pi.setTitle("Banan | Африка | Горящие туры в Африку 2014");
    	pi.setDescription("На Банане собраны все горящие путевки и туры в Африку. Огромный выбор горящих предложений со всех интернет-ресурсов Украиным 2014. Отдыхай на Банане!");
    	
    	String text = "<h1>Тур в Африку</h1><p><strong>Африка</strong>..ну что можно сказать об это континенте, колыбели человечества. В <strong>Африке</strong> можно встретить огромное количество культур и обычаев, не только африканских, но и <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%95%D0%B2%D1%80%D0%BE%D0%BF%D1%83'>европейских</a>, ведь именно <strong>Африка</strong> была полностью колонизирована европейскими государствами, которые несли туда культуру образование и науку. Но не стоит также забывать о великолепной африканской самобытности и уникальным на весь мир обычаям даже самого малого и незначительного племени.</p><p>Чем же вас может порадовать <a href='/'>горящее предложение</a> поехать в Африку? Ну в первую очередь - это солнечный <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%95%D0%B3%D0%B8%D0%BF%D0%B5%D1%82'>Египет</a>! Кажется уже каждый ребенок знает, чем в наши дни славится этот великолепный курорт. В первую очередь — это один из самых респектабельных курортов современности, не зря такое огромное количество туристов каждый год посещают это место и летом и зимой. В <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%95%D0%B3%D0%B8%D0%BF%D0%B5%D1%82'>Египте</a> также вас ожидает огромное количество самых интересных мест с мире: величественные пирамиды Гизы, прекрасный город Луксор, Национальный исторический музей Каира, дворец царицы Хатшепсут и многие другие.</p> <p>А как же красавец <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%A2%D1%83%D0%BD%D0%B8%D1%81'>Тунис</a>, также является одной из прекрасных туристических жемчужин <strong>Африки</strong>. <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%A2%D1%83%D0%BD%D0%B8%D1%81'>Тунис</a> всегда встретит вас прекрасной природой, обилием жаркого солнца и душевной гостеприимностью. Здесь вас ожидает огромное обилие комфортабельных отелей и обширная коллекция воспоминаний на всю жизнь. Также можно поехать в Марокко или в ЮАР...ну что тут сказать — <strong>Африка</strong> всегда рада гостям и память о путешествию сюда будет согревать вас холодными зимними ночами дома ;-). А <strong>Банан</strong> всегда поможет вам с поиском горящих туров в <strong>Африку</strong>!</p>"; 
    	
    	return searchPage(pi, text, searchEngine.getContinentTours((short)1));    	
    }
    
    public static Result northAmerica() 
    {    	   
    	PageInfo pi = new PageInfo();

    	pi.setUserTitle("<h1>Туры в Северную Америку</h1>");
    	pi.setTitle("Banan | Северная Америка | Горящие туры в Северную Америку 2014");
    	pi.setDescription("На Банане собраны все горящие путевки и туры в Северную Америку. Огромный выбор горящих предложений со всех интернет-ресурсов Украиным 2014. Отдыхай на Банане!");
    	
    	String text = "<h1>Туры в Северную Америку</h1><p>Если Вы хотите поехать не в очень экзотический, но интересный тур (обязательно горящий, возможно, <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B4%D0%BB%D1%8F_%D0%B4%D0%B2%D0%BE%D0%B8%D1%85'>на двоих</a> :) ) – Вам, вероятно, следует обратить внимание на путешествия в страны <strong>Северной Америки</strong> – <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%A1%D0%A8%D0%90'>США</a>, <strong>Канаду</strong> или <strong>Мексику</strong>.</p><p><strong>Мексика</strong>, конечно, выбивается из этого ряда, по вполне понятными причинам, но, тем не менее, поехать туда тоже интересно и познавательно, хотя бы из-за знаменитой текилы и гитарной игры, известной по всему миру.</p><p>Что же касается <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%A1%D0%A8%D0%90'>США</a>, то здесь Вы найдете свои неповторимые крсоты и особенности, воспетые в творчестве как писателей, так и широко показанные в современных фильмах. Страна большая, 'полакомитесь' и горами, и равнинами, и океаном. Почувствуете палящий горящий зной песков пустынь и шум двух океанов. Шумную романтику городских улиц <strong>Нью-Йорка</strong> и незамысловатые вечера на едине с девственной природой неебольших курортных окрестностей <strong>Майами</strong>. Мм, так и хочется  побыстрее поехать туда по <a href='/'>горящей путевке</a>!</p><p>О <strong>Канаде</strong> много рассказывать не стоит – лучше поехать и посмотреть на замечательную страну озер и гостеприимных людей.</p><p>Но что точно нужно сделать – это помечтать о поездке в эти замечательные страны, а потом зайти на Банан и выбрать наилучший горящий тур. И не один. Может, Вам понравится внезапно путешествовать :-)</p>"; 
    	
    	return searchPage(pi, text, searchEngine.getContinentTours((short)4));    	
    }
    
    public static Result southAmerica() 
    {    	   
    	PageInfo pi = new PageInfo();

    	pi.setUserTitle("<h1>Туры в Южную Америку</h1>");
    	pi.setTitle("Banan | Южная Америка | Горящие туры в Южную Америку 2014");
    	pi.setDescription("На Банане собраны все горящие путевки и туры в Южную Америку. Огромный выбор горящих предложений со всех интернет-ресурсов Украиным 2014. Отдыхай на Банане!");
    	
    	String text = "<h1>Туры в Латинскую Америку</h1><p>Континент удивительных цивилизаций ацтеков, майя и инков, край обворожительных красоток и изысканных кабальеро, самый большой кофейный и табачный край Земли, и, кроме этого, регион, где найдешь массу причудливых культур и традиций, <strong>Латинская Америка</strong> лежит на нижнем крае североамериканского континента, состоя из <strong>Южной Америки</strong> и целой плеяды островов, расположившихся рядом с их узким перешейком.</p><p>С туристической точки зрения, <strong>Латинская Америка</strong> — яркая шкатулка направлений. Почему только здесь не оказываются люди — и потому, чтобы собственноручно дотронуться к знаменитым памяткам стоительства, и потому, чтобы прокатиться на мощных внедорожниках в национальных парках и, безусловно, потому, чтобы 'вкусно' отдохнуть в отелях на берегах океанов. Люди, посещающие латиноамериканские страны уже достаточно путешествовали по миру, много раз посещали страны Юго-Восточной <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%90%D0%B7%D0%B8%D1%8E'>Азии</a> и требовательны к условиям проживания. <a href=''>Банан</a> предлагает окунуться в этот мир экзотики за цены на порядок ниже, чем в регулярных турах. Вы сможете предпочесть деешвый познавательный отдых по <a href=''>горящим турам</a>, для которого в <strong>Латинской Америке</strong> есть всё необходимое.</p><p><strong>Бразилия, Чили, Аргентина, Венесуэла, Перу </strong>– самые популярные страны для отдыха в <strong>Латинской Америке</strong>.</p><p>Люди едут в <strong>Бразилию</strong>, потому что хотят за раз увидеть и монолитные мегаполисы, и изучить девственные джунгли, где рокотом встречают исполинские водопады.</p><p>В <strong>Мексике</strong> едут на экскурсии к дивным храмам ацтеков и майя и, кроме этого, предпочитают <a href='/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D1%81_%D0%BF%D0%B8%D1%82%D0%B0%D0%BD%D0%B8%D0%B5%D0%BC'>лакшери-отдых</a> на наипрестижнейших пляжах мира и глубоководный дайвинг в открытом океане.</p><p><strong>Аргентина</strong> предложит побывать во многих национальных парках и покататься с гор, покрытых ледниками. Также, для изысканных любителей, можно приехать в самый южный город Земли и сразу же отправиться в гости к Антарктике.</p><p>В <strong>Латинской Америке</strong> есть менее раскрученные, но, не менее захватывающие туристические страны, как: <strong>Гондурас, Уругвай, Панама, Белиз, Парагвай, Гватемала, Сальвадор, Никарагуа, Французская Гвиана</strong>.</p><p>В этих многих увлекательных странах Вы можете побывать, лишь заходя на Банан в поисках заветно тура, что принесет множество незабываемых часов для Вашей жизни.</p>";
    	
    	return searchPage(pi, text, searchEngine.getContinentTours((short)6));    	
    }
    
    //////////////////////////////////////////////////////////CONTINENTS PAGES END ////////////////////////////////////////////////////////

    
}
