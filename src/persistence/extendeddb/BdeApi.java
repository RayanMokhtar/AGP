package persistence.extendeddb;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import org.apache.lucene.queryparser.classic.ParseException;
import org.w3c.dom.Text;

import persistence.extendeddb.jdbc.SQLResult;
import persistence.extendeddb.jdbc.SQLResults;
import persistence.extendeddb.jdbc.SQLAccess;
import persistence.extendeddb.lucene.TextAccess;
import persistence.extendeddb.lucene.TextualResult;
import persistence.extendeddb.lucene.TextualResults;

/**
 * ExtendedDatabaseAPI class
 * 
 * Script de test des méthodes disponible dans test.DemoBDe.java
 * 
 */
@SuppressWarnings("unused")
public class BdeApi {
    private TextualConfiguration textualConfiguration;
    private SQLConfiguration sqlConfiguration;

    public BdeApi(SQLConfiguration sqlConfiguration, TextualConfiguration textualConfiguration) {
        this.sqlConfiguration = sqlConfiguration;
        this.textualConfiguration = textualConfiguration;
    }
    
    
    public String getSiteDescription(int siteId) {
        String description = "N/A";
        try {
        	Path filePath = Paths.get("C:\\Users\\darkf\\Desktop\\java_workspace\\AGPTEST\\AGP_DB\\Description", siteId + ".txt");
            description = new String(Files.readAllBytes(filePath));
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture de la description pour le site ID " + siteId + ": " + e.getMessage());
        }
        return description;
    }

    public void createDescriptionFile(int siteId, String content) throws IOException {
    Path filePath = Paths.get("C:\\Users\\darkf\\Desktop\\java_workspace\\AGP\\AGP_DB\\Description", siteId + ".txt");
    Files.write(filePath, content.getBytes(StandardCharsets.UTF_8));
}

  
    public TextualResults textualQuery(String query) throws IOException, ParseException {
        if (query == null || query.isEmpty()) {
            System.err.println("La requête textuelle ne peut pas être vide");
            return new TextualResults();
        }

        try {
            System.out.println("Exécution de la requête textuelle: " + query);
            TextAccess searcher = new TextAccess(textualConfiguration.getIndexPath());
            TextualResults results = searcher.search(query);
            
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
     * @param combinedQuery A mixed query.
     * @throws SQLException, IOException, ParseException
     * @return MixedResults
     */
    public CombinedResults combinedQuery(String combinedQuery) throws SQLException, IOException, ParseException {
        String sqlQuery;
        String[] partsQuery;
        boolean hasTextualQuery;
        boolean hasTableForJoin;
        SQLResults sqlResults;
        TextualResults textualResults;
        CombinedResults mixedResults = new CombinedResults();
    
        // Découpage de la requête
        partsQuery = combinedQuery.split("(?i: WITH )");
        sqlQuery = partsQuery[0];
        
        hasTextualQuery = partsQuery.length == 2;
        hasTableForJoin = sqlQuery.matches("(?i:.*FROM.* " + textualConfiguration.getTable() + ".*)");
    
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
                            mixedResults.addTuple(new CombinedResult(tuple, textualResult));
                            break;
                        }
                    }
                }
            } else {
                // Requête SQL simple sans recherche textuelle
                sqlResults = simpleQuery(sqlQuery);
                for (SQLResult tuple : sqlResults) {
                    TextualResult textualResult = new TextualResult(
                        Integer.parseInt(tuple.getAttribute(textualConfiguration.getJoinKey())),
                        0,
                        getSiteDescription(Integer.parseInt(tuple.getAttribute(textualConfiguration.getJoinKey())))
                    );
                    mixedResults.addTuple(new CombinedResult(tuple, textualResult));
                }
            }
    
            // Debug des résultats
            System.out.println("Nombre de résultats: " + mixedResults.size());
            for (CombinedResult result : mixedResults) {
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
        SQLAccess searcher = new SQLAccess(sqlConfiguration.getUrl(), sqlConfiguration.getUser(), sqlConfiguration.getPassword());
        try {
            return searcher.search(query);
        } finally {
            searcher.close(); // Assurez-vous que la connexion est fermée après utilisation
        }
    }
}