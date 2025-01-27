package tests.junit.extendeddb.lucene;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import persistence.extendeddb.lucene.Indexer;


class IndexerTest {
	
	@Test
	void testIndex() {
		Path sourcePath = Paths.get("C:\\Data");
		Path indexPath = Paths.get("C:\\Index");
		
		try {
			Indexer index = new Indexer(sourcePath, indexPath);
			Indexer index2 = new Indexer(sourcePath, indexPath);
			
			index.createIndex(true);
			index.addDocuments(sourcePath);
			index.close();
			
			index2.createIndex(false);
			index2.addDocuments(sourcePath);
			index2.close();
			
		} catch (IOException e) {
			fail();
		}
		
		try (Stream<Path> filePathStream=Files.walk(indexPath)) {
			assertTrue(filePathStream.count() > 5);
		} catch (IOException e) {
			fail();
		}
	}
}
