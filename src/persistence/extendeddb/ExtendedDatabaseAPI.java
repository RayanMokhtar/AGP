/**
 * 
 */
package persistence.extendeddb;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.lucene.queryparser.classic.ParseException;

import persistence.extendeddb.jdbc.SQLResult;
import persistence.extendeddb.jdbc.SQLResults;
import persistence.extendeddb.jdbc.SQLSearcher;
import persistence.extendeddb.lucene.Searcher;
import persistence.extendeddb.lucene.TextualResult;
import persistence.extendeddb.lucene.TextualResults;

/**
 * ExtendedDatabaseAPI class
 * 
 * This API allows you to query the database through simple
 * or compound queries.
 */
public class ExtendedDatabaseAPI {
	private SQLConfiguration sqlConfiguration;
	private TextualConfiguration textualConfiguration;
	private SQLSearcher databaseConnection;
	
	/**
	 * ExtendedDatabaseAPI constructor
	 * 
	 * @param sqlConfiguration     The database configuration.
	 * @param textualConfiguration The Lucene configuration.
	 */
	public ExtendedDatabaseAPI(SQLConfiguration sqlConfiguration,
							   TextualConfiguration textualConfiguration) {
		
		this.sqlConfiguration = sqlConfiguration;
		this.textualConfiguration = textualConfiguration;
	}
	
	/**
	 * getDatabaseConnection
	 * 
	 * Initiates a connection to the database (Singleton).
	 * 
	 * @return SQLSearcher
	 */
	private SQLSearcher getDatabaseConnection() {
		if (databaseConnection == null) {
			databaseConnection = new SQLSearcher(
					sqlConfiguration.getUrl(),
					sqlConfiguration.getUser(),
					sqlConfiguration.getPassword()
			);
		}
		
		return databaseConnection;
	}
	
	/**
	 * simpleQuery
	 * 
	 * Method used to execute simple SQL queries on the database.
	 * 
	 * @param query An SQL query.
	 * @throws SQLException
	 * @return SQLResults
	 */
	public SQLResults simpleQuery(String query) throws SQLException {
		return getDatabaseConnection().search(query);
	}
	
	/**
	 * textualQuery
	 * 
	 * Method used to execute textual queries on the database.
	 * 
	 * @param query A textual query.
	 * @throws IOException, ParseException
	 * @return TextualResults
	 */
	public TextualResults textualQuery(String query)
			throws IOException, ParseException {
		
		Searcher searcher = new Searcher(textualConfiguration.getIndexPath());
		return searcher.search(query);
	}
	
	/**
	 * mixedQuery
	 * 
	 * Method used to execute mixed queries on the database.
	 * This method can also execute simple SQL queries,
	 * without the "with" clause.
	 * 
	 * @param mixedQuery A mixed query.
	 * @throws SQLException, IOException, ParseException
	 * @return MixedResults
	 */
	public MixedResults mixedQuery(String mixedQuery)
			throws SQLException, IOException, ParseException {
		
		String sqlQuery;
		String[] partsQuery;
		
		boolean hasTextualQuery;
		boolean hasTableForJoin;
		
		SQLResults sqlResults;
		TextualResults textualResults;
		MixedResults mixedResults;
		
		int tupleId;
		int documentId;
		String idAttribute;
		
		// Splitting the query into two parts
		// (SQL query and textual query)
		partsQuery = mixedQuery.split("(?i: WITH )");
		sqlQuery = partsQuery[0];
		
		hasTextualQuery = partsQuery.length == 2;
		hasTableForJoin = sqlQuery.matches(
				"(?i:.*FROM.* "
				+ textualConfiguration.getTable()
				+ ".*)"
		);
		
		mixedResults = new MixedResults();
		
		if (hasTextualQuery && hasTableForJoin) {
			// Execution of the simple SQL query
			// We add the primary key of the table for the join below
			sqlQuery = sqlQuery.substring(0, 7)
					   + textualConfiguration.getTable() + "."
					   + textualConfiguration.getJoinKey() + ", "
					   + sqlQuery.substring(7);
			
			sqlResults = simpleQuery(sqlQuery);
			
			// Execution of the textual query
			textualResults = textualQuery(partsQuery[1]);
			
			// Join
			for (TextualResult textualResult : textualResults) {
				documentId = textualResult.getId();
				
				for (SQLResult tuple : sqlResults) {
					idAttribute = tuple.getAttribute(textualConfiguration.getJoinKey());
					tupleId = Integer.parseInt(idAttribute);
					
					if (documentId == tupleId) {
						mixedResults.addTuple(new MixedResult(tuple, textualResult));
						break;
					}
				}
			}
		
		// The "with" clause has not been specified
		} else if (!hasTextualQuery) {
			sqlResults = simpleQuery(sqlQuery);
			
			// Converting the SQLResult into a MixedResult
			for (SQLResult tuple : sqlResults) {
				mixedResults.addTuple(new MixedResult(tuple, null));
			}
		}
		
		return mixedResults;
	}
}
