package business.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import business.Hotel;
import business.Offer;
import business.tools.HotelSelector;
import business.tools.OfferBuilder;
import business.tools.SiteSelector;
import business.tools.UserCriteria;

public class TestFonctionnalites {

	 public static void main(String[] args) {

	        // 1) Charger le contexte Spring (applicationContext.xml)
	        ApplicationContext context =
	            new ClassPathXmlApplicationContext("business/spring/spring.xml");

	        OfferBuilder offerBuilder = context.getBean("offerBuilder", OfferBuilder.class);
	        
	        // 3) Exemple d'utilisation
	        UserCriteria criteria = new UserCriteria();
	        // On configure quelques champs (ex: nbDays, maxPrice, etc.)
	        criteria.setNbDays(5);
	        criteria.setMinPrice(1000);

	        Hotel startHotel = new Hotel(10);
	        startHotel.setName("Hotel Demo");
	        startHotel.setPricePerDay(100);

	        try {
	            Offer offer = offerBuilder.buildOffer(criteria, startHotel);
	            System.out.println("Offre créée : " + offer);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	 
