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
<<<<<<< HEAD
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
=======
    private SQLResult sqlResult;
    private TextualResult textualResult;
    
    public MixedResult(SQLResult sqlResult, TextualResult textualResult) {
        this.sqlResult = sqlResult;
        this.textualResult = textualResult;
    }
    
    public String getAttribute(String attribute) {
        return sqlResult.getAttribute(attribute);
    }
    
    public int getNumberAttributes() {
        return sqlResult.getNumberAttributes();
    }
    
    public float getScore() {
        return textualResult != null ? textualResult.getScore() : 0.0f;
    }
    
    public String getContent() {
        return textualResult != null ? textualResult.getContent() : null;
    }
}
>>>>>>> Test
