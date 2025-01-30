package tests;
import business.Coordinates;
import business.TransportUtils;
import org.springframework.context.ApplicationContext;
import business.tools.SiteSelector;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestFonctionnalites {

	 public static void main(String[] args) {

	        // 1) Charger le contexte Spring (applicationContext.xml)
	        ApplicationContext context =
	            new ClassPathXmlApplicationContext("Buspring.xml");

	        // 2) Récupérer le bean SiteSelector
	        SiteSelector siteSelector = context.getBean("siteSelector", SiteSelector.class);
	        
}
	 
}