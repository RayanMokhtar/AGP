package tests.junit.business.tools;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import business.Site;
import business.tools.HotelSelector;

public class HotelSelectorTest {

	@Test
	@DisplayName("Should not be null")
	void testGetInstance() {
		int idHotel = 0;
		assertNotNull(HotelSelector.getInstance(idHotel));
	}
	
	@Test
	@DisplayName("Should not be null")
	void testGetNearestHotelFrom() {
		Site place = new Site(1);
		assertNotNull(HotelSelector.getNearestHotelFrom(place));
	}
	
	@Test
	@DisplayName("Should not be null")
	void testGetHotelWithBestScore() {	assertNotNull(HotelSelector.getHotelWithBestScore());
	}
}
