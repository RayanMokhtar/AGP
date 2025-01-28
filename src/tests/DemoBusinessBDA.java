/**
 * 
 */
package tests;

import java.util.List;

import business.Hotel;
import business.Island;
import business.Site;
import persistence.HotelPersistence;
import persistence.IslandPersistence;
import persistence.PlacePersistence;

/**
 *
 */
public class DemoBusinessBDA {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Hotels
		List<Hotel> hotels = HotelPersistence.getHotels();
		
		for (Hotel hotel : hotels) {
			System.out.println("========= Hotel : "
							   + hotel.getName()
							   + " ========="
			);
			
			System.out.println("[id] "
							   + hotel.getId()
							   + " [Price] "
							   + hotel.getPricePerDay()
			);
		}
		
		// Islands
		List<Island> islands = IslandPersistence.getIslands();
		
		for (Island island : islands) {
			System.out.println("========= Island : "
							   + island.getName()
							   + " ========="
			);
			
			System.out.println("[id] " + island.getId());
		}
		
		// Places
		List<Site> places = PlacePersistence.getPlaces("National");
		
		for (Site place : places) {
			System.out.println("========= Place : "
							   + place.getName()
							   + " ========="
			);
			
			System.out.println(" [Score] "
							   + place.getScore()
			);
			
			System.out.println("[Description] "
							   + place.getDescription()
			);
		}
	}
}
