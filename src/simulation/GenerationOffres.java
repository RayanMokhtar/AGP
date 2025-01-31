package simulation;

import java.util.List;

import business.Offer;
import business.exceptions.InsufficientBudgetException;
import business.tools.HotelSelector;
import business.tools.Intensity;
import dao.HotelDAO;
import persistence.HotelPersistence;
import persistence.SitePersistence;
import business.tools.OfferBuilder;
import business.tools.SiteSelector;
import business.tools.TypeSite;
import business.tools.UserCriteria;
import dao.SiteDAO;

public class GenerationOffres {
	
/**
 * @param args
 * @throws InsufficientBudgetException
 */
public static void main(String[] args) throws InsufficientBudgetException {
		
		
		SiteDAO siteDAO = new SitePersistence();
		HotelDAO hotelDAO = new HotelPersistence();
		
		HotelSelector hotelselector = new HotelSelector();
		SiteSelector siteselector = new SiteSelector();
		
		hotelselector.setHotelDAO(hotelDAO);
		siteselector.setSiteDAO(siteDAO);
		
	
		OfferBuilder offerbuilder = new OfferBuilder();
		
		offerbuilder.setHotelSelector(hotelselector);
		offerbuilder.setSiteSelector(siteselector);
        
		UserCriteria criteria = new UserCriteria();
        criteria.setNbDays(5);
        criteria.setMinPrice(20);     // budget min
        criteria.setMaxPrice(2000);    // budget max global
        criteria.setComfort(8);        // heures de transport tolérées/jour
        criteria.setVisitedPlacesPerDay(2);
        criteria.setIntensity(Intensity.MODERE); // ex : MODERE / INTENSE / RELAX
        criteria.setHotelStars(3);     
        criteria.setDescriptionSite("");
        criteria.setTypesite(TypeSite.HISTORIC);
        
	
		
		List<Offer> offers = offerbuilder.generateOffers(criteria,5);
		System.out.println("Offers : \n\n");
		SimulationUtils.displayOffers(offers);
	}
}
