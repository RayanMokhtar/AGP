/**
 * 
 */
package persistence.extendeddb;

import persistence.extendeddb.jdbc.SQLResult;
import persistence.extendeddb.lucene.TextualResult;

/**
 * TextualResult class
 * 
 * Used to store a mixed result.
 */
public class MixedResult {
	private SQLResult sqlResult;
	private TextualResult textualResult;
	
	/**
	 * MixedResult constructor
	 * 
	 * @param sqlResult     An SQL result.
	 * @param textualResult A textual result.
	 */
	public MixedResult(SQLResult sqlResult, TextualResult textualResult) {
		this.sqlResult = sqlResult;
		this.textualResult = textualResult;
	}
	
	/**
	 * getAttribute
	 * 
	 * Retrieves an attribute from this tuple.
	 * 
	 * @param attribute An attribute.
	 * @return String
	 */
	public String getAttribute(String attribute) {
		return sqlResult.getAttribute(attribute);
	}
	
	/**
	 * getNumberAttributes
	 * 
	 * Returns the number of pairs attributes / values stored.
	 * 
	 * @return Integer
	 */
	public int getNumberAttributes() {
		return sqlResult.getNumberAttributes();
	}
	
	/**
	 * getContent
	 * 
	 * Returns the file content linked to the tuple.
	 * 
	 * @return String
	 */
	public String getContent() {
		if (textualResult != null) {
			return textualResult.getContent();
		} else {
			return null;
		}
	}
	
	/**
	 * getScore
	 * 
	 * Returns the relevance of the result.
	 * 
	 * @return Integer
	 */
	public int getScore() {
		if (textualResult != null) {
			return textualResult.getScore();
		} else {
			return 0;
		}
	}
}
