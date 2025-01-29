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
    private TextualConfiguration textualConfiguration;
    private SQLConfiguration sqlConfiguration;

    public ExtendedDatabaseAPI(SQLConfiguration sqlConfiguration, TextualConfiguration textualConfiguration) {
        this.sqlConfiguration = sqlConfiguration;
        this.textualConfiguration = textualConfiguration;
    }

    /**
     * Method used to execute textual queries on the database.
     * 
     * @param query A textual query.
     * @throws IOException, ParseException
     * @return TextualResults
     */
    public TextualResults textualQuery(String query) throws IOException, ParseException {
        if (query == null || query.isEmpty()) {
            System.err.println("La requête textuelle ne peut pas être vide");
            return new TextualResults();
        }

        try {
            System.out.println("Exécution de la requête textuelle: " + query);
            Searcher searcher = new Searcher(textualConfiguration.getIndexPath());
            TextualResults results = searcher.search(query);
            
            // Debug des résultats
            if (results != null) {
                System.out.println("Nombre de résultats textuels trouvés: " + results.size());
                for (TextualResult result : results) {
                    System.out.println("ID: " + result.getId() 
                        + " Score: " + result.getScore()
                        + " Content: " + (result.getContent() != null ? 
                            result.getContent().substring(0, Math.min(50, result.getContent().length())) + "..." 
                            : "null"));
                }
            }

            return results;
        } catch (Exception e) {
            System.err.println("Erreur lors de la recherche textuelle: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Method used to execute mixed queries on the database.
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
        MixedResults mixedResults = new MixedResults();
    
        // Découpage de la requête
        partsQuery = mixedQuery.split("(?i: WITH )");
        sqlQuery = partsQuery[0];
        
        hasTextualQuery = partsQuery.length == 2;
        hasTableForJoin = sqlQuery.matches("(?i:.*FROM.* " + textualConfiguration.getTable() + ".*)");
    
        // Debug
        System.out.println("SQL Query: " + sqlQuery);
        if (hasTextualQuery) {
            System.out.println("Text Query: " + partsQuery[1]);
        }
    
        try {
            if (hasTextualQuery && hasTableForJoin) {
                // Ajout de la clé primaire pour la jointure
                String joinKey = textualConfiguration.getTable() + "." + textualConfiguration.getJoinKey();
                if (!sqlQuery.contains(joinKey)) {
                    sqlQuery = sqlQuery.substring(0, 7) + joinKey + ", " + sqlQuery.substring(7);
                }
                
                // Exécution des requêtes
                sqlResults = simpleQuery(sqlQuery);
                textualResults = textualQuery(partsQuery[1]);
    
                // Jointure entre résultats SQL et textuels
                for (SQLResult tuple : sqlResults) {
                    String idAttribute = tuple.getAttribute(textualConfiguration.getJoinKey());
                    int tupleId = Integer.parseInt(idAttribute);
                    
                    for (TextualResult textualResult : textualResults) {
                        if (textualResult.getId() == tupleId) {
                            mixedResults.addTuple(new MixedResult(tuple, textualResult));
                            break;
                        }
                    }
                }
            } else {
                // Requête SQL simple sans recherche textuelle
                sqlResults = simpleQuery(sqlQuery);
                for (SQLResult tuple : sqlResults) {
                    mixedResults.addTuple(new MixedResult(tuple, null));
                }
            }
    
            // Debug des résultats
            System.out.println("Nombre de résultats: " + mixedResults.size());
            for (MixedResult result : mixedResults) {
                System.out.println("ID: " + result.getAttribute("id") 
                    + ", Name: " + result.getAttribute("name")
                    + ", Score: " + (result.getScore() > 0 ? result.getScore() : "N/A")
                    + ", Description: " + (result.getContent() != null ? result.getContent().substring(0, Math.min(50, result.getContent().length())) + "..." : "N/A"));
            }
    
        } catch (Exception e) {
            System.err.println("Erreur lors de l'exécution de la requête mixte: " + e.getMessage());
            e.printStackTrace();
        }
    
        return mixedResults;
    }

    // Méthode simpleQuery à ajouter ici
    public SQLResults simpleQuery(String query) throws SQLException {
        // Utilisation du constructeur avec paramètres
        SQLSearcher searcher = new SQLSearcher(sqlConfiguration.getUrl(), sqlConfiguration.getUser(), sqlConfiguration.getPassword());
        return searcher.search(query);
    }
}