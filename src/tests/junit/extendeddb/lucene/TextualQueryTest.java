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


@SuppressWarnings("unused")
class TextualQueryTest {
	
	@Test
	void testTextualQuery() {
		Path sourcePath = Paths.get("C:\\Users\\darkf\\Desktop\\java_workspace\\AGP\\AGP_DB\\Description");
		Path indexPath = Paths.get("C:\\Users\\darkf\\Desktop\\java_workspace\\AGP\\AGP_DB\\Index");
		
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
		@SuppressWarnings("unused")
		int size = result.size();
		@SuppressWarnings("unused")
		boolean bool = result.hasNext();
		TextualResult name = new TextualResult(1, 3, "test");
		@SuppressWarnings("unused")
		TextualResult text = result.next();
		@SuppressWarnings("unused")
		int id = name.getId();
		@SuppressWarnings("unused")
		int score = name.getScore();
		@SuppressWarnings("unused")
		String oui = name.getContent();
		try {
			Indexer index = new Indexer(sourcePath, indexPath);
			
			index.createIndex(true);
			index.setIndexPath(sourcePath);
			index.setSourcePath(indexPath);
			index.addDocuments(sourcePath);
			index.close();
			
			ExtendedDatabaseAPI database = new ExtendedDatabaseAPI(sqlConfiguration, textualConfiguration);
			
			TextualResults textualResults = database.textualQuery("mus�e");
			
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
