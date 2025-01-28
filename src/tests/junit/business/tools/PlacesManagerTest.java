package tests.junit.business.tools;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import business.tools.SitesManager;

public class PlacesManagerTest {

	@Test
	@DisplayName("Should not be null")
	void testGetInstance() {
		int idPlace = 0;
		assertNotNull(SitesManager.getInstance(idPlace));
	}
	
	@Test
	@DisplayName("Should not be null")
	void testGetPlacesInOrder() {
		assertNotNull(SitesManager.getPlacesInOrder());
	}

}
