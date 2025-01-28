/**
 * 
 */
package persistence;

import java.util.LinkedList;
import java.util.List;

import business.Coordinates;
import business.Island;
import business.Site;
import persistence.extendeddb.ExtendedDatabaseAPI;
import persistence.extendeddb.MixedResult;
import persistence.extendeddb.MixedResults;
import dao.SiteDAO;
/**
 *
 */
public class SitePersistence implements SiteDAO{
	
	private SitePersistence() {
		
	}
	
	private static MixedResults getPlacesResults(String whereClause, String withClause) {
		MixedResults mixedResults = null;
		ExtendedDatabaseAPI database = Database.getConnection();
		
		String query = "SELECT id, name, type, visitDuration, entrancePrice,"
					   + "latitude, longitude, idIsland FROM Place";
		
		if (!whereClause.isEmpty()) {
			query += " WHERE " + whereClause;
		}
		
		if (!withClause.isEmpty()) {
			query += " WITH " + withClause;
		}
		
		try {
			mixedResults = database.mixedQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mixedResults;
	}
	
	private static Site getPlaceObject(MixedResult tuple) {
		int id;
		boolean isHistoric;
		String name;
		int visitDuration;
		double entrancePrice;
		double latitude;
		double longitude;
		int idIsland;
		Coordinates coordinates;
		Island island;
		
		int score;
		String description;
		
		id = Integer.parseInt(tuple.getAttribute("id"));
		isHistoric = tuple.getAttribute("type") == "historic";
		name = tuple.getAttribute("name");
		visitDuration = Integer.parseInt(tuple.getAttribute("visitDuration"));
		entrancePrice = Double.parseDouble(tuple.getAttribute("entrancePrice"));
		latitude = Double.parseDouble(tuple.getAttribute("latitude"));
		longitude = Double.parseDouble(tuple.getAttribute("longitude"));
		idIsland = Integer.parseInt(tuple.getAttribute("idIsland"));
		
		score = tuple.getScore();
		description = tuple.getContent();
		
		coordinates = new Coordinates(latitude, longitude);
		
		island = IslandsManager.getInstance(idIsland);
		island.incrementScore();
		
		return new Site(id, name, isHistoric, visitDuration, entrancePrice,
						 description, coordinates, island, score);
	}
	
	public static List<Site> getPlaces(String keywords) {
		List<Site> places = new LinkedList<Site>();
		MixedResults tuples = getPlacesResults("", keywords);
		
		for (MixedResult tuple : tuples) {
			places.add(getPlaceObject(tuple));
		}
		
		return places;
	}
	
	public static List<Site> getActivityPlaces(String keywords) {
		List<Site> places = new LinkedList<Site>();
		MixedResults tuples = getPlacesResults("type = 'activity'", keywords);
		
		for (MixedResult tuple : tuples) {
			places.add(getPlaceObject(tuple));
		}
		
		return places;
	}
	
	public static List<Site> getHistoricPlaces(String keywords) {
		List<Site> places = new LinkedList<Site>();
		MixedResults tuples = getPlacesResults("type = 'historic'", keywords);
		
		for (MixedResult tuple : tuples) {
			places.add(getPlaceObject(tuple));
		}
		
		return places;
	}
}
