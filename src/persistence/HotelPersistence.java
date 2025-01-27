/**
 * 
 */
package persistence;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import business.Coordinates;
import business.Hotel;
import business.Island;
import business.tools.IslandsManager;
import persistence.extendeddb.ExtendedDatabaseAPI;
import persistence.extendeddb.jdbc.SQLResult;
import persistence.extendeddb.jdbc.SQLResults;

/**
 *
 */
public class HotelPersistence {
	
	private HotelPersistence() {
		
	}
	
	private static SQLResults getHotelsResults(String whereClause) {
		SQLResults sqlResults = null;
		ExtendedDatabaseAPI database = Database.getConnection();
		
		String query = "SELECT * FROM Hotel";
		
		if (!whereClause.isEmpty()) {
			query += " WHERE " + whereClause;
		}
		
		try {
			sqlResults = database.simpleQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return sqlResults;
	}
	
	private static Hotel getHotelObject(SQLResult tuple) {
		int id;
		String name;
		double pricePerDay;
		double latitude;
		double longitude;
		int idIsland;
		Coordinates coordinates;
		Island island;
		
		id = Integer.parseInt(tuple.getAttribute("id"));
		name = tuple.getAttribute("name");
		pricePerDay = Double.parseDouble(tuple.getAttribute("pricePerDay"));
		latitude = Double.parseDouble(tuple.getAttribute("latitude"));
		longitude = Double.parseDouble(tuple.getAttribute("longitude"));
		idIsland = Integer.parseInt(tuple.getAttribute("idIsland"));
		
		coordinates = new Coordinates(latitude, longitude);
		island = IslandsManager.getInstance(idIsland);
		
		return new Hotel(id, name, pricePerDay, coordinates, island);
	}
	
	public static List<Hotel> getHotels() {
		List<Hotel> hotels = new LinkedList<Hotel>();
		SQLResults tuples = getHotelsResults("");
		
		for (SQLResult tuple : tuples) {
			hotels.add(getHotelObject(tuple));
		}
		
		return hotels;
	}
	
	public static List<Hotel> getHotelsFromIsland(int idIsland) {
		List<Hotel> hotels = new LinkedList<Hotel>();
		SQLResults tuples = getHotelsResults("idIsland = " + idIsland);
		
		for (SQLResult tuple : tuples) {
			hotels.add(getHotelObject(tuple));
		}
		
		return hotels;
	}
}
