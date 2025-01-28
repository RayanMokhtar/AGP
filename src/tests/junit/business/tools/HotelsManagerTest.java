package tests.junit.business.tools;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import business.Place;
import business.tools.HotelsManager;

public class HotelsManagerTest {

	@Test
	@DisplayName("Should not be null")
	void testGetInstance() {
		int idHotel = 0;
		assertNotNull(HotelsManager.getInstance(idHotel));
	}
	
	@Test
	@DisplayName("Should not be null")
	void testGetNearestHotelFrom() {
		Place place = new Place(1);
		assertNotNull(HotelsManager.getNearestHotelFrom(place));
	}
	
	@Test
	@DisplayName("Should not be null")
	void testGetHotelWithBestScore() {
		assertNotNull(HotelsManager.getHotelWithBestScore());
	}
}
