package simulation;

import java.util.List;

import business.Offer;
import business.tools.Intensity;
import business.tools.TypeSite;
import business.tools.UserCriteria;
import business.tools.OfferBuilder;
import dao.HotelDAO;
import dao.SiteDAO;
import business.tools.HotelSelector;
import persistence.HotelPersistence;
import persistence.SitePersistence;
import business.tools.SiteSelector;

public class Simulation {

	public static void main(String[] args) {
		// Création d'une entrée de simulation avec les critères de l'utilisateur
        SimulationEntry entry = new SimulationEntry(
            5, // nbDays
            20, // minPrice
            20000, // maxPrice
            Intensity.RELAX, // intensity
            4, // confort
            "Banane", // descriptionSite
            2, // visitedPlacesPerDay
            TypeSite.HOBBIES, // typeSite
            2 // hotelStars
        );

        // Conversion des données de SimulationEntry en UserCriteria
        UserCriteria criterias = new UserCriteria();
        criterias.setNbDays(entry.getNbDays());
        criterias.setMinPrice(entry.getMinPrice());
        criterias.setMaxPrice(entry.getMaxPrice());
        criterias.setIntensity(entry.getIntensity());
        criterias.setComfort(entry.getConfort());
        criterias.setDescriptionSite(entry.getDescriptionSite());
        criterias.setVisitedPlacesPerDay(entry.getVisitedPlacesPerDay());
        criterias.setTypesite(entry.getTypeSite());
        criterias.setHotelStars(entry.getHotelStars());

        // Construction des offres
        
        HotelDAO h = new HotelPersistence();
        SiteDAO s = new SitePersistence();
        HotelSelector hotelselector = new HotelSelector();
        hotelselector.setHotelDAO(h);
        
        SiteSelector siteselector = new SiteSelector();
        siteselector.setSiteDAO(s);
        OfferBuilder offerBuilder = new OfferBuilder();
        offerBuilder.setHotelSelector(hotelselector);
        offerBuilder.setSiteSelector(siteselector);
        List<Offer> offers = offerBuilder.generateOffers(criterias, 5);

        SimulationUtils.displayOffers(offers);
    

	}

}
