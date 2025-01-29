/**
 * 
 */
package persistence;

import java.nio.file.Path;
import java.nio.file.Paths;

import persistence.extendeddb.ExtendedDatabaseAPI;
import persistence.extendeddb.SQLConfiguration;
import persistence.extendeddb.TextualConfiguration;

/**
 *
 */
public class Database {
	private static ExtendedDatabaseAPI connection;
	
	// SQL configuration
	private static String system = "mysql";
	private static String host = "mysql-agp-antilles.alwaysdata.net";
	private static String base = "agp-antilles_database";
	private static String user = "396335";
	private static String password = "Pmlpmlpmlk000";
	
	// Textual queries configuration
	private static Path sourcePath = Paths.get("AGP_DB", "Description");
	private static Path indexPath = Paths.get("AGP_DB", "Index");
	private static String table = "Site";
	private static String joinKey = "id";
	
	
	private Database() {
		
	}
	
	public static ExtendedDatabaseAPI getConnection() {
		if (connection == null) {
			SQLConfiguration sqlConfiguration = new SQLConfiguration(
					system,
					host,
					base,
					user,
					password
			);
			
			TextualConfiguration textualConfiguration = new TextualConfiguration(
					sourcePath,
					indexPath,
					table,
					joinKey
			);
			
			connection = new ExtendedDatabaseAPI(sqlConfiguration, textualConfiguration);
		}
		
		return connection;
	}
}
