/**
 * 
 */
package tests;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import persistence.extendeddb.BdeApi;
import persistence.extendeddb.CombinedResult;
import persistence.extendeddb.CombinedResults;
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
public class DemoBDe {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Path sourcePath = Paths.get("AGP_DB", "Description");
		Path indexPath = Paths.get("AGP_DB", "Index");
		
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
				"",
				"",
				"",
				""
		);
		
		TextualConfiguration textualConfiguration = new TextualConfiguration(
				sourcePath,
				indexPath,
				"Site",
				"id"
		);
		
		try {
			// établir la connexion à l'API
			BdeApi database = new BdeApi(sqlConfiguration, textualConfiguration);
			// créer une descrition pour une clée donnée
			database.createDescriptionFile(30, "Anse Lazio, située sur l'île de Praslin aux Seychelles, est souvent considérée comme l'une des plus belles plages du monde. Avec son sable fin, ses eaux cristallines et ses rochers granitiques, c'est un lieu de rêve pour les voyageurs.");
			
			// 1. Simple query
			System.out.println("simple query : \n");
			SQLResults sqlResults = database.simpleQuery("SELECT name, type FROM Site WHERE type = 'historic'");
			
			System.out.println("Test de requete simple :\n");			
			// Display the results with a while loop
			SQLResult sqlResult;
			
			
			while (sqlResults.hasNext()) {
				sqlResult = sqlResults.next();
				
				System.out.println(sqlResult.getAttribute("name"));
			}

			
			
			// 2. Textual query
			// ============================
			System.out.println("textual query : \n");
			TextualResults textualResults = database.textualQuery("musée");
			
			/*// Display the results with a for loop
			for (TextualResult textualResult : textualResults) {
				System.out.println("textual query : \n");
				System.out.println("text result est : "+textualResult);

			}*/
			
			// Display the results with a while loop
			TextualResult textualResult;
			
			while (textualResults.hasNext()) {
				textualResult = textualResults.next();
				
				System.out.println("[id] " + textualResult.getId()
								   + " [score] " + textualResult.getScore()
								   + " [content] " + textualResult.getContent()
				);
			}
			
			
			
			System.out.println("résultat des deux typologies de requetes :\n\n\n\n");
			// 3. Mixed query
			// ============================
			CombinedResults combinedResults = database.combinedQuery("SELECT id, name, type, duration, entryPrice, latitude, longitude, idIsland FROM Site WITH musée");
			CombinedResult combinedResult;
			// Display the results with while loop
			while (combinedResults.hasNext()) {
				combinedResult = combinedResults.next();
				System.out.println("========= "
								   + combinedResult.getAttribute("name")
								   + " ========="
				);
				
				System.out.println("[Type] "
								   + combinedResult.getAttribute("type")
								   + " [Score] "
								   + combinedResult.getScore()
				);
				
				System.out.println("[Description] "
								   + combinedResult.getContent()
				);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
