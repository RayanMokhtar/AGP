/**
 * 
 */
package business.tools;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import business.Island;

/**
 *
 */
public class IslandsManager {
	private static Map<Integer, Island> registry = new HashMap<Integer, Island>();
	
	private IslandsManager() {
		
	}
	
	public static Island getInstance(int idIsland) {
		if (!registry.containsKey(idIsland)) {
			Island island = new Island(idIsland);
			registry.put(idIsland, island);
			
			return island;
			
		} else {
			return registry.get(idIsland);
		}
	}
	
	public static void reset() {
		registry.clear();
	}
	
	public static Island getIslandWithBestScore() {
		int maximum = 0;
		int score = 0;
		Island bestIsland = null;
		Collection<Island> islands = registry.values();
		
		for (Island island : islands) {
			score = island.getScore();
			
			if (score > maximum) {
				bestIsland = island;
				maximum = score;
			}
		}
		
		return bestIsland;
	}
	
	public static Island getIslandWithMostPlaces() {
		int maximum = 0;
		int score = 0;
		Island bestIsland = null;
		Collection<Island> islands = registry.values();
		
		for (Island island : islands) {
			score = island.getScore();
			
			if (score > maximum) {
				bestIsland = island;
				maximum = score;
			}
		}
		
		return bestIsland;
	}
}
