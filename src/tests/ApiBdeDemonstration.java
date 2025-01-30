/**
 * 
 */
package tests;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import persistence.extendeddb.ExtendedDatabaseAPI;
import persistence.extendeddb.MixedResult;
import persistence.extendeddb.MixedResults;
import persistence.extendeddb.SQLConfiguration;
import persistence.extendeddb.TextualConfiguration;
import persistence.extendeddb.jdbc.SQLResult;
import persistence.extendeddb.jdbc.SQLResults;
import persistence.extendeddb.lucene.Indexer;
import persistence.extendeddb.lucene.TextualResult;
import persistence.extendeddb.lucene.TextualResults;

/**
 *
 */
	public class ApiBdeDemonstration {
	
		/**
	 * @param args
	 */
	public static void main(String[] args) {
		Path sourcePath = Paths.get("AGP_DB", "Description");
		Path indexPath = Paths.get("AGP_DB", "Index");
		
		try {
	
			Indexer index = new Indexer(sourcePath, indexPath);
			
			index.createIndex(true);
			
			index.writeDocument("18", "il s'agit de COO projet AGP pour démo Lucene Supposons que c'est un test"); // pour ajouter des documents //
			
			index.addDocuments(sourcePath);
			index.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		SQLConfiguration sqlConfiguration = new SQLConfiguration(  // conf SQL pour connexion a la base de données
				"mysql",
				"mysql-agp-antilles.alwaysdata.net",
				"agp-antilles_database",
				"396335",
				"Pmlpmlpmlk000"
		);
		
		TextualConfiguration textualConfiguration = new TextualConfiguration(
				sourcePath,
				indexPath,
				"Site",
				"id"
		);
		
		try {
			// connexion à la base de données 
			ExtendedDatabaseAPI database = new ExtendedDatabaseAPI(sqlConfiguration, textualConfiguration);
						
			// 1. Simple query
			// ============================
			System.out.println("simple query : \n");
			SQLResults sqlResults = database.simpleQuery("SELECT name, type FROM Site WHERE type = 'historic'");
			
			// Display the results with a for loop
			System.out.println("Test de requete simple :\n");
			for (SQLResult sqlResult : sqlResults) {
				System.out.println(sqlResult.getAttribute("name"));
			}
			
			// Display the results with a while loop
			SQLResult sqlResult;
			
			// sqlResults.init();
			
			while (sqlResults.hasNext()) {
				sqlResult = sqlResults.next();
				
				System.out.println(sqlResult.getAttribute("name"));
			}
			
			
			
			//  requete textuelle
			System.out.println("requete textuelle: \n");
			TextualResults textualResults = database.textualQuery("musée");
			
			// Display the results with a for loop
			for (TextualResult textualResult : textualResults) {
				System.out.println("[id] " + textualResult.getId()
								   + " [score] " + textualResult.getScore()
								   + " [content] " + textualResult.getContent()
				);
			}
			
			// Display the results with a while loop
			TextualResult textualResult;
			
			while (textualResults.hasNext()) {
				textualResult = textualResults.next();
				
				System.out.println("[id] " + textualResult.getId()
								   + " [score] " + textualResult.getScore()
								   + " [content] " + textualResult.getContent()
				);
			}
			
			
			
			System.out.println("Mixed results :\n");
			// 3. Mixed query
			// ============================
			MixedResults mixedResults = database.mixedQuery("SELECT id, name, type, duration, entryPrice, latitude, longitude, idIsland FROM Site WITH musée");
			
			// Display the results with a for loop
			for (MixedResult mixedResult : mixedResults) {
				System.out.println("========= "
								   + mixedResult.getAttribute("name")
								   + " ========="
				);
				
				System.out.println("[Type] "
								   + mixedResult.getAttribute("type")
								   + " [Score] "
								   + mixedResult.getScore()
				);
				
				System.out.println("[Description] "
									   + mixedResult.getContent()
					);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
