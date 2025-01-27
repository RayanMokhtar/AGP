/**
 * 
 */
package business.tools;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import business.Coordinates;
import business.Hotel;
import business.Island;
import business.Place;

/**
 *
 */
public class HotelsManager {
	private static Map<Integer, Hotel> registry = new HashMap<Integer, Hotel>();
	private static List<Hotel> nearestHotelsHistory = new LinkedList<Hotel>();
	
	private HotelsManager() {
		
	}
	
	public static Hotel getInstance(int idHotel) {
		if (!registry.containsKey(idHotel)) {
			Hotel hotel = new Hotel(idHotel);
			registry.put(idHotel, hotel);
			
			return hotel;
			
		} else {
			return registry.get(idHotel);
		}
	}
	
	public static void register(Hotel hotel) {
		registry.put(hotel.getId(), hotel);
	}
	
	public static void reset() {
		registry.clear();
		nearestHotelsHistory.clear();
	}
	
	public static void journalizeNearestHotel(Hotel hotel) {
		nearestHotelsHistory.add(hotel);
	}
	
	public static void resetHotelsScore() {
		Collection<Hotel> hotels = registry.values();
		
		for (Hotel hotel : hotels) {
			hotel.setScore(0);
		}
	}
	
	public static Hotel getNearestHotelFrom(Place place) {
		double minimalDistance = Double.MAX_VALUE;
		double distance;
		boolean isOnTheSameIsland;
		Hotel nearestHotel = null;
		
		Island hotelIsland;
		Coordinates hotelCoordinates;
		
		Island placeIsland = place.getIsland();
		Coordinates placeCoordinates = place.getCoordinates();
		
		Collection<Hotel> hotels = registry.values();
		
		
		for (Hotel hotel : hotels) {
			hotelIsland = hotel.getIsland();
			hotelCoordinates = hotel.getCoordinates();
			
			isOnTheSameIsland = hotelIsland.equals(placeIsland);
			distance = hotelCoordinates.getDistanceFrom(placeCoordinates);
			
			if (distance < minimalDistance && !nearestHotelsHistory.contains(hotel)) {
				nearestHotel = hotel;
				minimalDistance = distance;
			}
		}
		
		return nearestHotel;
	}
	
	public static Hotel getHotelWithBestScore() {
		int maximum = 0;
		int score = 0;
		Hotel bestHotel = null;
		Collection<Hotel> hotels = registry.values();
		
		for (Hotel hotel : hotels) {
			score = hotel.getScore();
			
			if (score > maximum) {
				bestHotel = hotel;
				maximum = score;
			}
		}
		
		return bestHotel;
	}
}
