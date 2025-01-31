package tests.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import persistence.extendeddb.BdeApi;
import persistence.extendeddb.CombinedResult;
import persistence.extendeddb.CombinedResults;
import persistence.extendeddb.SQLConfiguration;
import persistence.extendeddb.TextualConfiguration;
import persistence.extendeddb.jdbc.SQLResults;
import persistence.extendeddb.lucene.TextualResult;
import persistence.extendeddb.lucene.TextualResults;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

public class BDeTests {
    private BdeApi api;

    // On peut récupérer ces chemins depuis le code existant
    private static Path sourcePath = Paths.get("C:\\Users\\mokht\\Desktop\\AGP\\AGP\\AGP_DB\\Description");
    private static Path indexPath = Paths.get("C:\\Users\\mokht\\Desktop\\AGP\\AGP\\AGP_DB\\Index");
	
    @Before
    public void setUp() {
        SQLConfiguration sqlConfiguration = new SQLConfiguration(
             "mysql",
             "mysql-agp-antilles.alwaysdata.net",
             "agp-antilles_database",
             "396335_wassim",
             "Pmlpmlpmlk000"
        );
        TextualConfiguration textualConfiguration = new TextualConfiguration(
             sourcePath,
             indexPath,
             "Site",
             "id"
        );
        api = new BdeApi(sqlConfiguration, textualConfiguration);
    }

@Test
public void testSimpleQuery() throws SQLException {
    SQLResults results = api.simpleQuery("SELECT id, name from Hotel;");
    assertNotNull(results);
    assertTrue(results.size() > 0);
}

@Test
public void testTextualQuery() {
    try {
        // On récupère les résultats textuels
        TextualResults results = api.textualQuery("banane");
        assertNotNull("Les résultats textuels ne doivent pas être nuls", results);

        // On peut vérifier s’il y a des résultats
        // (ici, on ne sait pas si "banane" devrait renvoyer qqch, on teste surtout que ça ne plante pas)
        if (results.size() > 0) {
            for (TextualResult res : results) {
                assertNotNull("Le contenu ne doit pas être nul", res.getContent());
            }
        }
    } catch (Exception e) {
        fail("Exception inattendue : " + e.getMessage());
    }
}

@Test
public void testMixedQuery() {
    try {
        // Requête mixte fictive : "SELECT * FROM Site WITH test"
        CombinedResults results = api.combinedQuery("SELECT id, name, type, duration, entryPrice, latitude, longitude, idIsland FROM Site WITH banane");
        assertNotNull("Les résultats mixtes ne doivent pas être nuls", results);
        if (results.size() > 0) {
            for (CombinedResult res : results) {
                assertNotNull(res);
            }
        }
    } catch (Exception e) {
        fail("Exception inattendue : " + e.getMessage());
    }
}

}