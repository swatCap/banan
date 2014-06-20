package ua.banan.web;

import java.util.HashMap;
import java.util.Map;

public class Routes
{
	public static final Map<Integer, String> countriesRoutes	     = new HashMap<Integer, String>();
	public static final Map<Integer, String> citiesRoutes 		 = new HashMap<Integer, String>();
	
	public static final Map<Integer, String> tourOpsRoutes         = new HashMap<Integer, String>();
	
	static
	{
		countriesRoutes.put(109,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%A2%D1%83%D1%80%D1%86%D0%B8%D1%8E");//turkey()
		countriesRoutes.put(61,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%95%D0%B3%D0%B8%D0%BF%D0%B5%D1%82");//egypt()
		countriesRoutes.put(18,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%93%D1%80%D0%B5%D1%86%D0%B8%D1%8E");//greece()
		countriesRoutes.put(121,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%91%D0%BE%D0%BB%D0%B3%D0%B0%D1%80%D0%B8%D1%8E");//bulgaria()
		countriesRoutes.put(35,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%98%D1%81%D0%BF%D0%B0%D0%BD%D0%B8%D1%8E");//spain()
		countriesRoutes.put(202,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%A5%D0%BE%D1%80%D0%B2%D0%B0%D1%82%D0%B8%D1%8E");//croatia()
		countriesRoutes.put(9,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%A2%D0%B0%D0%B8%D0%BB%D0%B0%D0%BD%D0%B4");//thailand()
		countriesRoutes.put(108,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%A2%D1%83%D0%BD%D0%B8%D1%81");//tunisia()
		countriesRoutes.put(14,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%AD%D0%BC%D0%B8%D1%80%D0%B0%D1%82%D1%8B");//emirates()
		countriesRoutes.put(117,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%BD%D0%B0_%D0%9A%D0%B8%D0%BF%D1%80");//cyprus()
		countriesRoutes.put(13,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%98%D1%82%D0%B0%D0%BB%D0%B8%D1%8E");//italy()
		countriesRoutes.put(114,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%A7%D0%B5%D1%85%D0%B8%D1%8E");//czech()
		countriesRoutes.put(133,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%A7%D0%B5%D1%80%D0%BD%D0%BE%D0%B3%D0%BE%D1%80%D0%B8%D1%8E");//montenegro()
		countriesRoutes.put(118,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%92%D0%B5%D0%BD%D0%B3%D1%80%D0%B8%D1%8E");//hungary()
		countriesRoutes.put(127,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%90%D0%B2%D1%81%D1%82%D1%80%D0%B8%D1%8E");//austria()
		countriesRoutes.put(12,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2%D0%BE_%D0%A4%D1%80%D0%B0%D0%BD%D1%86%D0%B8%D1%8E");//france()
		countriesRoutes.put(29,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%93%D0%B5%D1%80%D0%BC%D0%B0%D0%BD%D0%B8%D1%8E");//germany()
		countriesRoutes.put(132,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%BD%D0%B0_%D0%A8%D1%80%D0%B8-%D0%9B%D0%B0%D0%BD%D0%BA%D1%83");//sriLanka()
		countriesRoutes.put(110,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%9F%D0%BE%D0%BB%D1%8C%D1%88%D1%83");//poland()
		countriesRoutes.put(34,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%98%D0%B7%D1%80%D0%B0%D0%B8%D0%BB%D1%8C");//israel()
		countriesRoutes.put(52,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%BD%D0%B0_%D0%9C%D0%B0%D0%BB%D1%8C%D0%B4%D0%B8%D0%B2%D1%8B");//maldives()
		countriesRoutes.put(33,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%93%D0%BE%D0%BB%D0%BB%D0%B0%D0%BD%D0%B4%D0%B8%D1%8E");//netherlands()
		countriesRoutes.put(191,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%98%D0%BE%D1%80%D0%B4%D0%B0%D0%BD%D0%B8%D1%8E");//jordan()
		countriesRoutes.put(11,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2%D0%BE_%D0%92%D1%8C%D0%B5%D1%82%D0%BD%D0%B0%D0%BC");//vietnam()
		countriesRoutes.put(84,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%BD%D0%B0_%D0%9C%D0%B0%D0%B2%D1%80%D0%B8%D0%BA%D0%B8%D0%B9");//mauritius()
		countriesRoutes.put(98,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%BD%D0%B0_%D0%A1%D0%B5%D0%B9%D1%88%D0%B5%D0%BB%D0%BB%D1%8B");//seychelles()
		countriesRoutes.put(129,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%A1%D0%BB%D0%BE%D0%B2%D0%B5%D0%BD%D0%B8%D1%8E");//slovenia()
		countriesRoutes.put(124,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%93%D1%80%D1%83%D0%B7%D0%B8%D1%8E");//georgia()
		countriesRoutes.put(99,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%9C%D0%B0%D1%80%D0%BE%D0%BA%D0%BA%D0%BE");//morocco()
		countriesRoutes.put(170,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%94%D0%BE%D0%BC%D0%B8%D0%BD%D0%B8%D0%BA%D0%B0%D0%BD%D1%83");//dominicanRepublic()
		countriesRoutes.put(45,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%98%D0%BD%D0%B4%D0%BE%D0%BD%D0%B5%D0%B7%D0%B8%D1%8E");//indonesia()
		countriesRoutes.put(56,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%91%D0%B5%D0%BB%D1%8C%D0%B3%D0%B8%D1%8E");//belgium()
		countriesRoutes.put(23,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%A1%D0%A8%D0%90");//usa()
		countriesRoutes.put(42,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%A1%D0%B8%D0%BD%D0%B3%D0%B0%D0%BF%D1%83%D1%80");//singapore()
		countriesRoutes.put(119,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%A1%D0%BB%D0%BE%D0%B2%D0%B0%D0%BA%D0%B8%D1%8E");//slovakia()
		countriesRoutes.put(4,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%9A%D0%B8%D1%82%D0%B0%D0%B9");//china()
		countriesRoutes.put(145,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%9B%D1%8E%D0%BA%D1%81%D0%B5%D0%BC%D0%B1%D1%83%D1%80%D0%B3");//luxembourg()
		countriesRoutes.put(140,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%AD%D1%81%D1%82%D0%BE%D0%BD%D0%B8%D1%8E");//estonia()
		countriesRoutes.put(120,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%A1%D0%B5%D1%80%D0%B1%D0%B8%D1%8E");//serbia()
		countriesRoutes.put(112,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%A3%D0%BA%D1%80%D0%B0%D0%B8%D0%BD%D1%83");//ukraine()
		countriesRoutes.put(154,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%9C%D0%BE%D0%BD%D0%B0%D0%BA%D0%BE");//monaco()
		countriesRoutes.put(20,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%A0%D0%BE%D1%81%D1%81%D0%B8%D1%8E");//russia()
		countriesRoutes.put(111,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%9B%D0%B0%D1%82%D0%B2%D0%B8%D1%8E");//latvia()
		countriesRoutes.put(59,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%A8%D0%B2%D0%B5%D0%B9%D1%86%D0%B0%D1%80%D0%B8%D1%8E");//switzerland()
		countriesRoutes.put(123,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%B2_%D0%A0%D1%83%D0%BC%D1%8B%D0%BD%D0%B8%D1%8E");//romania()
		countriesRoutes.put(226,"/%D0%93%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D0%B5_%D1%82%D1%83%D1%80%D1%8B_%D0%BD%D0%B0_%D0%9A%D1%83%D0%B1%D1%83");//cuba()
		
		tourOpsRoutes.put(1, "/tui");
		tourOpsRoutes.put(2, "/happyColumbus");
		tourOpsRoutes.put(3, "/hottours");
		tourOpsRoutes.put(4, "/tour-shturman");
		tourOpsRoutes.put(5, "/%D0%91%D0%B0%D0%BC%D0%B1%D0%B0%D1%80%D0%B1%D0%B8%D1%8F");
		tourOpsRoutes.put(6, "/touravia");
		tourOpsRoutes.put(7, "/turtess");
		tourOpsRoutes.put(8, "/%D0%9F%D0%BE%D0%B5%D1%85%D0%B0%D0%BB%D0%B8_%D1%81_%D0%BD%D0%B0%D0%BC%D0%B8");
		tourOpsRoutes.put(9, "/%D0%BE%D1%82%D0%BF%D1%83%D1%81%D0%BA");
		tourOpsRoutes.put(10, "/turne");
		tourOpsRoutes.put(11, "/%D0%A1%D0%B5%D1%82%D1%8C_%D0%B0%D0%B3%D0%B5%D0%BD%D1%81%D1%82%D0%B2_%D0%B3%D0%BE%D1%80%D1%8F%D1%89%D0%B8%D1%85_%D0%BF%D1%83%D1%82%D0%B5%D0%B2%D0%BE%D0%BA");
		tourOpsRoutes.put(12, "/tez");
		tourOpsRoutes.put(13, "/mansana");
		tourOpsRoutes.put(14, "/candytour");
		
	}
	
}
