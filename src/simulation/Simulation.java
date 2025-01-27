/**
 * 
 */
package simulation;

import java.util.List;

import business.Offer;
import business.tools.OffersBuilder;
import business.tools.UserPreferences;

/**
 *
 */
public class Simulation {
	
	public static void main(String[] args) {
		// Settings
		final String PLACE_TYPE = "activity";
		final String KEYWORDS = "";
		final int MINIMUM_PRICE = 200;
		final int MAXIMUM_PRICE = 1000;
		final int NUMBER_OF_DAYS = 4;
		final int EXCURSIONS_FREQUENCY = 1;
		final double MAXIMUM_TRANSPORT_DURATION = 0.25;
		
		
		// Creation of offers based on client criteria
		UserPreferences preferences = new UserPreferences();
		preferences.setPlaceType(PLACE_TYPE);
		preferences.setKeywords(KEYWORDS);
		preferences.setMinimumPrice(MINIMUM_PRICE);
		preferences.setMaximumPrice(MAXIMUM_PRICE);
		preferences.setNumberOfDays(NUMBER_OF_DAYS);
		preferences.setExcursionsFrequency(EXCURSIONS_FREQUENCY);
		preferences.setMaximumTransportDuration(MAXIMUM_TRANSPORT_DURATION);
		
		
		OffersBuilder offersBuilder = new OffersBuilder(preferences);
		List<Offer> offers = offersBuilder.buildOffers();
		
		Tools.displayOffers(offers);
	}
}
