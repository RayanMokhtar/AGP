package tests.junit.business.tools;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import business.tools.IslandsManager;

public class IslandsManagerTest {
	@Test
	@DisplayName("Should not be null")
	void testGetInstance() {
		int idIsland = 0;
		assertNotNull(IslandsManager.getInstance(idIsland));
	}
	
	@Test
	@DisplayName("Should not be null")
	void testGetIslandWithBestScore() {
		assertNotNull(IslandsManager.getIslandWithBestScore());
	}
	
	@Test
	@DisplayName("Should not be null")
	void testGetHotelWithBestScore() {
		assertNotNull(IslandsManager.getIslandWithMostPlaces());
	}
}
