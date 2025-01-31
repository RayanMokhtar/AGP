package tests.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import business.Hotel;
import business.Island;
import business.Site;
import business.tools.TypeSite;
import business.tools.UserCriteria;
import dao.HotelDAO;
import dao.IslandDAO;
import dao.SiteDAO;
import persistence.HotelPersistence;
import persistence.IslandPersistence;
import persistence.SitePersistence;
import persistence.extendeddb.BdeApi;
import persistence.extendeddb.CombinedResult;
import persistence.extendeddb.CombinedResults;
import persistence.extendeddb.SQLConfiguration;
import persistence.extendeddb.TextualConfiguration;
import persistence.extendeddb.jdbc.SQLResults;
import persistence.extendeddb.lucene.TextualResult;
import persistence.extendeddb.lucene.TextualResults;

public class SoutenanceJunit {
    private BdeApi api;

    // On peut récupérer ces chemins depuis le code existant
    private Path sourcePath = Paths.get("C:\\Users\\darkf\\Desktop\\java_workspace\\AGP\\AGP_DB\\Description");
    private Path indexPath  = Paths.get("C:\\Users\\darkf\\Desktop\\java_workspace\\AGP\\AGP_DB\\Index");

    private IslandDAO islandDAO;
    private HotelDAO hotelDAO;
    private SiteDAO siteDAO;

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
    	siteDAO = new SitePersistence();
        islandDAO = new IslandPersistence();
        hotelDAO = new HotelPersistence();
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

    // Tests IslandDAO
    @Test
    public void testIslandFindById() {
        Island island = islandDAO.findById(1);
        assertNotNull(island);
        assertEquals("Dominica", island.getName());
    }

    @Test
    public void testHotelFindByCriteria() {
    	UserCriteria criteria = new UserCriteria();
    	criteria.setHotelStars(3);
    	List<Hotel> hotels = hotelDAO.findByCriteria(criteria);
    	assertNotNull(hotels);
    	for (Hotel hotel : hotels) {
            assertNotNull(hotel);
            }
    	
    }

    @Test
    public void testSiteFindByDescription() {
        String description = "banane";
        List<Site> sites = siteDAO.findByDescription(description);
        assertNotNull(sites);
        for (Site site : sites) {
        	System.out.println(site.toString()+"\n");
            assertNotNull(site);
            assertTrue("La description doit contenir 'banane'",
                    site.getDescription().toLowerCase().contains("banane"));
            }
    }

    @Test
    public void testSiteFindByCriteria() {
        UserCriteria criteria = new UserCriteria();
        criteria.setTypesite(TypeSite.HOBBIES);
        criteria.setDescriptionSite("plage");
        List<Site> sites = siteDAO.findByCriteria(criteria);
        assertNotNull(sites);
        for (Site site : sites) {
            assertEquals(TypeSite.HOBBIES, site.getType());
        assertNotNull(site.getDescription());
        assertTrue("La description doit contenir 'plage'",
                   site.getDescription().toLowerCase().contains("plage"));
        }
        // Peut être vide si aucun site ne correspond exactement
    }
}