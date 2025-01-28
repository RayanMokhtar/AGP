/**
 * 
 */
package business.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import business.Site;

/**
 *
 */
public class PlacesManager {
	private static Map<Integer, Site> registry = new HashMap<Integer, Site>();
	private static List<Site> placesInOrder;
	
	private PlacesManager() {
		
	}
	
	public static Site getInstance(int idPlace) {
		if (!registry.containsKey(idPlace)) {
			Site place = new Site(idPlace);
			registry.put(idPlace, place);
			
			return place;
			
		} else {
			return registry.get(idPlace);
		}
	}
	
	public static void register(Site place) {
		registry.put(place.getId(), place);
	}
	
	public static void reset() {
		registry.clear();
	}

	public static List<Site> getPlacesInOrder() {
		return placesInOrder;
	}

	public static void setPlacesInOrder(List<Site> placesInOrder) {
		PlacesManager.placesInOrder = placesInOrder;
	}
}
