/**
 * 
 */
package persistence;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import business.Island;
import persistence.extendeddb.ExtendedDatabaseAPI;
import persistence.extendeddb.jdbc.SQLResult;
import persistence.extendeddb.jdbc.SQLResults;

/**
 *
 */
public class IslandPersistence {
	
	private IslandPersistence() {
		
	}
	
	private static SQLResults getIslandsResults() {
		SQLResults sqlResults = null;
		ExtendedDatabaseAPI database = Database.getConnection();
		
		try {
			sqlResults = database.simpleQuery("SELECT * FROM Island");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return sqlResults;
	}
	
	private static Island getIslandObject(SQLResult tuple) {
		int id;
		String name;
		
		id = Integer.parseInt(tuple.getAttribute("id"));
		name = tuple.getAttribute("name");
		
		return new Island(id, name);
	}
	
	public static List<Island> getIslands() {
		List<Island> islands = new LinkedList<Island>();
		SQLResults tuples = getIslandsResults();
		
		for (SQLResult tuple : tuples) {
			islands.add(getIslandObject(tuple));
		}
		
		return islands;
	}
}
