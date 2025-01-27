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
public class DemoApiBDA {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Path sourcePath = Paths.get("C:\\Users\\darkf\\Desktop\\java_workspace\\AGP\\AGP_DB\\Description");
		Path indexPath = Paths.get("C:\\Users\\darkf\\Desktop\\java_workspace\\AGP\\AGP_DB\\Index");
		
		try {
			// Create an index and add documents
			Indexer index = new Indexer(sourcePath, indexPath);
			
			index.createIndex(true);
			
			// (Optional) Write a document in sourcePath
			index.writeDocument("100", "Description\nOn multiple lines...");
			
			index.addDocuments(sourcePath);
			index.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		SQLConfiguration sqlConfiguration = new SQLConfiguration(
				"mysql",
				"mysql-agp-antilles.alwaysdata.net",
				"agp-antilles_bdd",
				"396335",
				"Pmlpmlpmlk000"
		);
		
		TextualConfiguration textualConfiguration = new TextualConfiguration(
				sourcePath,
				indexPath,
				"Place",
				"id"
		);
		
		try {
			// Establish the connection
			ExtendedDatabaseAPI database = new ExtendedDatabaseAPI(sqlConfiguration, textualConfiguration);
			
			// 1. Simple query
			// ============================
			SQLResults sqlResults = database.simpleQuery("SELECT name, type FROM Place WHERE type = 'historic'");
			
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
			
			
			// 2. Textual query
			// ============================
			TextualResults textualResults = database.textualQuery("mus�e");
			
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
			
			
			// 3. Mixed query
			// ============================
			MixedResults mixedResults = database.mixedQuery("SELECT name, type FROM Place WITH mus�e");
			
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
			
			// Display the results with a while loop
			MixedResult mixedResult;
			
			while (mixedResults.hasNext()) {
				mixedResult = mixedResults.next();
				
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
