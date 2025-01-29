/**
 * 
 */
package persistence;

import java.util.LinkedList;
import java.util.List;

import business.Coordinates;
import business.Island;
import business.Site;
import business.tools.TypeSite;
import persistence.extendeddb.ExtendedDatabaseAPI;
import persistence.extendeddb.MixedResult;
import persistence.extendeddb.MixedResults;

/**
 *
 */
public class SitePersistence {
	
	private SitePersistence() {
		
	}
	
	private static MixedResults getSitesResults(String whereClause, String withClause) {
		MixedResults mixedResults = null;
		ExtendedDatabaseAPI database = Database.getConnection();
		
		String query = "SELECT id, name, type, duration, entryPrice,"
					   + "latitude, longitude, idIsland FROM Site";
		
		if (!whereClause.isEmpty()) {
			query += " WHERE " + whereClause;
		}
		
		if (!withClause.isEmpty()) {
			query += " WITH " + withClause;
		}
		
		System.out.println(query);
		
		try {
			mixedResults = database.mixedQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mixedResults;
	}
	
	private static Site getSiteObject(MixedResult tuple) {
	    try {
	        // Récupération des attributs de base
	        int id = Integer.parseInt(tuple.getAttribute("id"));
	        TypeSite type = TypeSite.fromString(tuple.getAttribute("type"));
	        String name = tuple.getAttribute("name");
	        int duration = (int)Double.parseDouble(tuple.getAttribute("duration"));
	        double entryPrice = Double.parseDouble(tuple.getAttribute("entryPrice"));
	        double latitude = Double.parseDouble(tuple.getAttribute("latitude"));
	        double longitude = Double.parseDouble(tuple.getAttribute("longitude"));
	        int idIsland = Integer.parseInt(tuple.getAttribute("idIsland"));
	        
	        // Création des objets complexes
	        Coordinates coordinates = new Coordinates(latitude, longitude);
	        Island island = IslandPersistence.getIslandById(idIsland);
	        
	        // Récupération de la description via ExtendedDatabaseAPI
	        ExtendedDatabaseAPI api = Database.getConnection();
	        String description = api.getDescriptionById(id);
	        
	        return new Site(id, name, entryPrice, duration, coordinates, island, type, description);
	    } catch (Exception e) {
	        System.err.println("Erreur lors de la création du Site: " + e.getMessage());
	        return null;
	    }
	}
	
	public static List<Site> getSites(String keywords) {
		List<Site> Sites = new LinkedList<Site>();
		MixedResults tuples = getSitesResults("", keywords);
		
		for (MixedResult tuple : tuples) {
			Sites.add(getSiteObject(tuple));
		}
		
		return Sites;
	}
	
	public static List<Site> getActivitySites(String keywords) {
		List<Site> sites = new LinkedList<Site>();
		MixedResults tuples = getSitesResults("type = 'activity'", keywords);
		
		for (MixedResult tuple : tuples) {
			sites.add(getSiteObject(tuple));
		}
		
		return sites;
	}
	
	public static List<Site> getHistoricSites(String keywords) {
		List<Site> Sites = new LinkedList<Site>();
		MixedResults tuples = getSitesResults("type = 'historic'", keywords);
		
		for (MixedResult tuple : tuples) {
			Sites.add(getSiteObject(tuple));
		}
		
		return Sites;
	}
}