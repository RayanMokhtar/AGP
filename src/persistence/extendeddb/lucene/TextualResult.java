/**
 * 
 */
package persistence.extendeddb.lucene;

/**
 * TextualResult class
 * 
 * Used to store a textual result.
 */
public class TextualResult {
	private int id;
	private int score;
	private String content;
	
	/**
	 * TextualResult constructor
	 * 
	 * @param id      The identifier of the document to do the correspondence
	 * 				  with a primary key in a table of the database.
	 * @param score   The relevance of the result.
	 * @param content The content of a file.
	 */
	public TextualResult(int id, int score, String content) {
		this.id = id;
		this.score = score;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public int getScore() {
		return score;
	}

	public String getContent() {
		return content;
	}
}
