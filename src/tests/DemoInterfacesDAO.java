/**
 * 
 */
package tests;

import business.Hotel;
import business.Island;
import business.Site;
import business.tools.HotelSelector;

import java.util.List;
import persistence.HotelPersistence;
import persistence.IslandPersistence;
import persistence.SitePersistence;
import dao.HotelDAO;
/**
 *
 */
public class DemoInterfacesDAO {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		
		HotelDAO hotelDAO = new HotelPersistence();
		HotelSelector hotelselector = new HotelSelector();
		hotelselector.setHotelDAO(hotelDAO);
		// Hotels
		List<Hotel> hotels = hotelselector.getAllHotels();
		
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
		
		// sites
		List<Site> sites = SitePersistence.getSites("musée");
		
		for (Site site : sites) {
			System.out.println("========= site : "
							   + site.getName()
							   + " ========="
			);
			
			System.out.println(" [Score] "
							
			);
			
			System.out.println("[Description] "
							   + site.getDescription()
			);
		}
	}
}
