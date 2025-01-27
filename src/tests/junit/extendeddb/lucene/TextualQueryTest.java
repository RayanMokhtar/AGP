package tests.junit.extendeddb.lucene;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import persistence.extendeddb.ExtendedDatabaseAPI;
import persistence.extendeddb.SQLConfiguration;
import persistence.extendeddb.TextualConfiguration;
import persistence.extendeddb.lucene.Indexer;
import persistence.extendeddb.lucene.Searcher;
import persistence.extendeddb.lucene.TextualResult;
import persistence.extendeddb.lucene.TextualResults;


class TextualQueryTest {
	
	@Test
	void testTextualQuery() {
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
		TextualResults result = new TextualResults();
		int size = result.size();
		boolean bool = result.hasNext();
		TextualResult name = new TextualResult(1, 3, "test");
		TextualResult text = result.next();
		int id = name.getId();
		int score = name.getScore();
		String oui = name.getContent();
		try {
			Indexer index = new Indexer(sourcePath, indexPath);
			
			index.createIndex(true);
			index.setIndexPath(sourcePath);
			index.setSourcePath(indexPath);
			index.addDocuments(sourcePath);
			index.close();
			
			ExtendedDatabaseAPI database = new ExtendedDatabaseAPI(sqlConfiguration, textualConfiguration);
			
			TextualResults textualResults = database.textualQuery("musée");
			
			for (TextualResult textualResult : textualResults) {
				assertTrue(textualResult.getId() >= 0);
				assertTrue(textualResult.getScore() > 0);
				assertNotNull(textualResult.getContent());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
