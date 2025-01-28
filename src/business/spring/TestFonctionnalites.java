package business.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import business.tools.HotelSelector;
import business.tools.SiteSelector;

public class TestFonctionnalites {

	 public static void main(String[] args) {

	        // 1) Charger le contexte Spring (applicationContext.xml)
	        ApplicationContext context =
	            new ClassPathXmlApplicationContext("business/spring/spring.xml");

	        // 2) Récupérer le bean SiteSelector
	        HotelSelector hotelSelector = context.getBean("hotelSelector", HotelSelector.class);
	        SiteSelector siteSelector = context.getBean("siteSelector", SiteSelector.class);

	        
	 }
	 
}