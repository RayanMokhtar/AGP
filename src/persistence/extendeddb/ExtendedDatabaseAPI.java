/**
 * 
 */
package persistence.extendeddb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;

import persistence.extendeddb.jdbc.SQLResult;
import persistence.extendeddb.jdbc.SQLResults;
import persistence.extendeddb.jdbc.SQLSearcher;
import persistence.extendeddb.lucene.Searcher;
import persistence.extendeddb.lucene.TextualResult;
import persistence.extendeddb.lucene.TextualResults;
import java.nio.file.Path;

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
	public MixedResults mixedQuery(String mixedQuery) throws SQLException, IOException, ParseException {
	    String sqlQuery;
	    String[] partsQuery;
	    boolean hasTextualQuery;
	    boolean hasTableForJoin;
	    SQLResults sqlResults;
	    TextualResults textualResults;
	    MixedResults mixedResults;
	    List<Integer> scores = new ArrayList<>();
	    
	    // Splitting the query into two parts
	    partsQuery = mixedQuery.split("(?i: WITH )");
	    sqlQuery = partsQuery[0];
	    
	    hasTextualQuery = partsQuery.length == 2;
	    hasTableForJoin = sqlQuery.matches("(?i:.*FROM.* " + textualConfiguration.getTable() + ".*)");
	    
	    mixedResults = new MixedResults();
	    
	    if (hasTextualQuery && hasTableForJoin) {
	        // Ajout de la clé primaire pour la jointure
	        sqlQuery = sqlQuery.substring(0, 7)
	               + textualConfiguration.getTable() + "."
	               + textualConfiguration.getJoinKey() + ", "
	               + sqlQuery.substring(7);
	        
	        sqlResults = simpleQuery(sqlQuery);
	        textualResults = textualQuery(partsQuery[1]);
	        
	        // Join avec scores
	        for (TextualResult textualResult : textualResults) {
	            int documentId = textualResult.getId();
	            
	            for (SQLResult tuple : sqlResults) {
	                String idAttribute = tuple.getAttribute(textualConfiguration.getJoinKey());
	                int tupleId = Integer.parseInt(idAttribute);
	                
	                if (documentId == tupleId) {
	                    mixedResults.addTuple(new MixedResult(tuple, textualResult));
	                    scores.add((int)textualResult.getScore());
	                    break;
	                }
	            }
	        }
	    } else {
	        // Sans recherche textuelle
	        sqlResults = simpleQuery(sqlQuery);
	        
	        for (SQLResult tuple : sqlResults) {
	            String idAttribute = tuple.getAttribute(textualConfiguration.getJoinKey());
	            int tupleId = Integer.parseInt(idAttribute);
	            
	            // Récupérer la description même sans recherche textuelle
	            String description = getDescriptionById(tupleId);
	            TextualResult textualResult = description != null ? 
	                new TextualResult(tupleId, 0, description) : null;
	                
	            mixedResults.addTuple(new MixedResult(tuple, textualResult));
	            scores.add(0); // Score 0 pour les résultats sans recherche textuelle
	        }
	    }
	    
	    // Définir la liste des scores
	    mixedResults.setScoreList(scores);
	    
	    return mixedResults;
	}
	// get the description in the Index repertory of the site by its id
	public String getDescriptionById(int id) {
    try {
        // Construire le chemin du fichier description
        Path descPath = textualConfiguration.getSourcePath().resolve(id + ".txt");
        
        // Vérifier si le fichier existe
        if (!Files.exists(descPath)) {
            System.err.println("Description file not found for id: " + id);
            return null;
        }

        // Lire le contenu du fichier
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(descPath.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        }
        
        return contentBuilder.toString();
        
    } catch (IOException e) {
        System.err.println("Error reading description file for id " + id + ": " + e.getMessage());
        return null;
    }
}
}