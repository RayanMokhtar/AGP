package tests.junit.extendeddb;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import persistence.extendeddb.ExtendedDatabaseAPI;
import persistence.extendeddb.MixedResult;
import persistence.extendeddb.MixedResults;
import persistence.extendeddb.SQLConfiguration;
import persistence.extendeddb.TextualConfiguration;
import persistence.extendeddb.lucene.Indexer;


public class MixedQueryTest {
	@Test
	void testMixedQuery() {
		Path sourcePath = Paths.get("C:\\Data");
		Path indexPath = Paths.get("C:\\Index");
		
		SQLConfiguration sqlConfiguration = new SQLConfiguration(
				"mysql",
				"localhost",
				"travelDB",
				"root",
				"p@ssword"
		);
		
		TextualConfiguration textualConfiguration = new TextualConfiguration(
				sourcePath,
				indexPath,
				"Place",
				"id"
		);
		
		try {
			Indexer index = new Indexer(sourcePath, indexPath);
			
			index.createIndex(true);
			index.addDocuments(sourcePath);
			index.close();
			
			ExtendedDatabaseAPI database = new ExtendedDatabaseAPI(sqlConfiguration, textualConfiguration);
			MixedResults sqlResults = database.mixedQuery("SELECT name, type FROM Place WITH musée");
			
			for(MixedResult result : sqlResults) {
				assertNotNull(result.getContent());
				assertTrue(result.getScore() >= 0 );
				assertNotNull(result.getAttribute("name"));
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
