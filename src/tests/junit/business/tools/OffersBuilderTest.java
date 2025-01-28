package tests.junit.business.tools;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import business.Offer;
import business.tools.OffersBuilder;
import business.tools.UserPreferences;

public class OffersBuilderTest {

	@Test
	@DisplayName("Shoudl not be empty")
	void testBuildOffers() {
		UserPreferences userPreferences = new UserPreferences();
		userPreferences.setExcursionsFrequency(2);
		userPreferences.setKeywords("rhum");
		userPreferences.setMaximumPrice(500);
		userPreferences.setMaximumTransportDuration(120);
		userPreferences.setMinimumPrice(200);
		userPreferences.setNumberOfDays(5);
		userPreferences.setPlaceType("Plage des cocotiers");
		
		OffersBuilder builder = new OffersBuilder(userPreferences);
		List<Offer> offers = builder.buildOffers();
		int actualResult = offers.size();
		
		int expectedResult = 0;
		
		assertTrue(actualResult != expectedResult);
	}
}
