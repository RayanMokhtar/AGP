/**
 * 
 */
package persistence.extendeddb.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * Indexer class
 * 
 * Creates an index and allows you to add documents.
 */
public class Indexer {
	private Path sourcePath;
	private Path indexPath;
	private IndexWriter writer;
	
	/**
	 * Index constructor
	 * 
	 * @param sourcePath The directory that contains the files to index.
	 * @param indexPath  The directory that will contain the future index.
	 */
	public Indexer(Path sourcePath, Path indexPath) {
		this.sourcePath = sourcePath;
		this.indexPath = indexPath;
	}
	
	/**
	 * createIndex
	 * 
	 * Method used to create the index.
	 * 
	 * @param recreateIndex If the option is true, the existing index will
	 *                      be overwritten.
	 * @throws IOException
	 */
	public void createIndex(boolean recreateIndex) throws IOException {
		Directory directory;
		Analyzer analyzer;
		IndexWriterConfig indexConfiguration;
		
		if (!Files.isReadable(sourcePath)) {
			System.err.println("Error : sourcePath "
							   + sourcePath.toString()
							   + " does not exist or is not readable.");
		}
		
		if (!Files.isReadable(indexPath)) {
			System.err.println("Error : indexPath "
							   + indexPath.toString()
							   + " does not exist or is not readable.");
		}
		
		directory = FSDirectory.open(indexPath);
		analyzer = new StandardAnalyzer();
		indexConfiguration = new IndexWriterConfig(analyzer);
		
		indexConfiguration.setOpenMode(
				(recreateIndex) ? OpenMode.CREATE : OpenMode.CREATE_OR_APPEND
		);
		
		writer = new IndexWriter(directory, indexConfiguration);
	}
	
	/**
	 * addDocument
	 * 
	 * Method used to index a document.
	 * 
	 * @param documentPath The path to the document to index.
	 * @throws IOException
	 */
	public void addDocument(Path documentPath) throws IOException {
		String absolutePathStr;
		File documentFile;
		FileReader fileReader;
		BufferedReader bufferedReader;
		Document document;
		
		
		if (writer == null) {
			System.err.println("Please initialize the writer before.");
		}
		
		if (!Files.isReadable(documentPath)) {
			System.err.println("Error : documentPath "
					   + documentPath.toString()
					   + " does not exist or is not readable.");
		}
		
		
		documentFile = documentPath.toFile();
		absolutePathStr = documentFile.getAbsolutePath();
		
		// Opening the document to read its content
		fileReader = new FileReader(documentFile);
		bufferedReader = new BufferedReader(fileReader);
		
		document = new Document();
		
		// Indexing of two fields (absolute path and content of the file)
		document.add(new StringField("path", absolutePathStr, Field.Store.YES));
		
		// The content is not fully indexed
		// (only used to search the document by keywords)
		document.add(new TextField("content", bufferedReader));
		
		// Adding the document to the index
		writer.addDocument(document);
		
		bufferedReader.close();
		fileReader.close();
	}
	
	/**
	 * addDocuments
	 * 
	 * Method used to index multiple documents.
	 * 
	 * @param documentsPath The path to the directory with the documents to index.
	 * @throws IOException
	 */
	public void addDocuments(Path documentsPath) throws IOException {
		if (Files.isDirectory(documentsPath)) {
			Files.walkFileTree(documentsPath, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
						throws IOException {
					
					addDocument(file);
					return FileVisitResult.CONTINUE;
				}
			});
		} else {
			addDocument(documentsPath);
		}
	}
	
	/**
	 * writeDocument
	 * 
	 * Method used to write a document to the sourcePath directory.
	 * Useful if the files to index are not created.
	 * 
	 * @param key     The primary key of the line of your database to
	 * 				  which the content will be attached.
	 * @param content The content of the file.
	 * @throws IOException 
	 */
	public void writeDocument(String key, String content)
			throws IOException {
		
		File documentFile = new File(sourcePath + "/" + key + ".txt");
		FileWriter fileWriter = new FileWriter(documentFile);
		fileWriter.write(content);
		fileWriter.close();
	}
	
	/**
	 * close
	 * 
	 * Method used to close the connection to the indexer.
	 * Call it when you have finished adding documents.
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		writer.close();
	}

	public Path getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(Path sourcePath) {
		this.sourcePath = sourcePath;
	}

	public Path getIndexPath() {
		return indexPath;
	}

	public void setIndexPath(Path indexPath) {
		this.indexPath = indexPath;
	}
}
