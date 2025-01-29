/**
 * 
 */
package persistence.extendeddb;

import java.nio.file.Path;

/**
 * TextualConfiguration class
 * 
 * Used to define the configuration of the ExtendedDatabaseAPI.
 */
public class TextualConfiguration {
	private Path sourcePath;
	private Path indexPath;
	private String table;
	private String joinKey;
	
	/**
	 * TextualConfiguration constructor
	 */
	public TextualConfiguration() {

	}
	
	/**
	 * TextualConfiguration constructor
	 * 
	 * @param sourcePath The directory that contains the files to index.
	 * @param indexPath  The directory that contains the index.
	 * @param table      The table with a Lucene index.
	 * @param joinKey    The name of the attribute used for the join.
	 */
	public TextualConfiguration(Path sourcePath, Path indexPath, String table, String joinKey) {
		this.sourcePath = sourcePath;
		this.indexPath = indexPath;
		this.table = table;
		this.joinKey = joinKey;
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

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getJoinKey() {
		return joinKey;
	}

	public void setJoinKey(String joinKey) {
		this.joinKey = joinKey;
	}
}