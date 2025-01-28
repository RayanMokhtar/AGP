package tests.junit.business.tools;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import business.tools.PlacesManager;

public class PlacesManagerTest {

	@Test
	@DisplayName("Should not be null")
	void testGetInstance() {
		int idPlace = 0;
		assertNotNull(PlacesManager.getInstance(idPlace));
	}
	
	@Test
	@DisplayName("Should not be null")
	void testGetPlacesInOrder() {
		assertNotNull(PlacesManager.getPlacesInOrder());
	}

}
