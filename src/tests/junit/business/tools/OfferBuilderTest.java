package tests.junit.business.tools;


import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import business.Offer;
import business.tools.OfferBuilder;
import business.tools.UserCriteria;

public class OfferBuilderTest {

	@Test
	@DisplayName("Shoudl not be empty")
	void testBuildOffers() {
		UserCriteria criteria = new UserCriteria();
		
		
		OfferBuilder builder = new OfferBuilder();
		List<Offer> offers = builder.generateOffers(criteria,5);
		int actualResult = offers.size();
		
		int expectedResult = 0;
		
		assertTrue(actualResult != expectedResult);
	}
}
