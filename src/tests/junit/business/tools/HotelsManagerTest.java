package tests.junit.business.tools;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import business.Site;
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
		Site place = new Site(1);
		assertNotNull(HotelsManager.getNearestHotelFrom(place));
	}
	
	@Test
	@DisplayName("Should not be null")
	void testGetHotelWithBestScore() {
		assertNotNull(HotelsManager.getHotelWithBestScore());
	}
}
