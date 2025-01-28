/**
 * 
 */
package business.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import business.Place;

/**
 *
 */
public class PlacesManager {
	private static Map<Integer, Place> registry = new HashMap<Integer, Place>();
	private static List<Place> placesInOrder;
	
	private PlacesManager() {
		
	}
	
	public static Place getInstance(int idPlace) {
		if (!registry.containsKey(idPlace)) {
			Place place = new Place(idPlace);
			registry.put(idPlace, place);
			
			return place;
			
		} else {
			return registry.get(idPlace);
		}
	}
	
	public static void register(Place place) {
		registry.put(place.getId(), place);
	}
	
	public static void reset() {
		registry.clear();
	}

	public static List<Place> getPlacesInOrder() {
		return placesInOrder;
	}

	public static void setPlacesInOrder(List<Place> placesInOrder) {
		PlacesManager.placesInOrder = placesInOrder;
	}
}
