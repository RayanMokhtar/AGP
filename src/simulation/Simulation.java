package simulation;

import java.util.List;

import business.Offer;
import business.tools.Intensity;
import business.tools.TypeSite;
import business.tools.UserCriteria;
import business.tools.OfferBuilder;
import business.tools.TestOfferBuilder;

public class Simulation {

	public static void main(String[] args) {
		// Création d'une entrée de simulation avec les critères de l'utilisateur
        SimulationEntry entry = new SimulationEntry(
            7, // nbDays
            200, // minPrice
            1000, // maxPrice
            Intensity.RELAX, // intensity
            4, // confort
            "Beautiful beach destination", // descriptionSite
            2, // visitedPlacesPerDay
            TypeSite.HOBBIES, // typeSite
            4 // hotelStars
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
        TestOfferBuilder offerBuilder = new TestOfferBuilder();
        List<Offer> offers = offerBuilder.generateOffer();

        // Affichage des offres
        SimulationUtils.displayOffers(offers);
    

	}

}
