package persistence.extendeddb;

import persistence.extendeddb.jdbc.SQLResult;
import persistence.extendeddb.lucene.TextualResult;

/**
 * MixedResult class
 * 
 * Used to store a mixed result combining an SQLResult and an optional TextualResult.
 */

// fusionner les deux typologies de résultats , ligne de base de données , et le résulat textuel . 
public class MixedResult {

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

    public int getScore() {
        return (textualResult != null) ? textualResult.getScore() : 0;
    }

    public String getContent() {
        return (textualResult != null) ? textualResult.getContent() : null;
    }
}


