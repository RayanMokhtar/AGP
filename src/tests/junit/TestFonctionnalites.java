package tests.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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

public class TestFonctionnalites {

    private IslandDAO islandDAO;
    private HotelDAO hotelDAO;
    private SiteDAO siteDAO;

    @Before
    public void setUp() {
        islandDAO = new IslandPersistence();
        hotelDAO = new HotelPersistence();
        siteDAO = new SitePersistence();
    }

    // Tests IslandDAO
    @Test
    public void testIslandFindById() {
        Island island = islandDAO.findById(1);
        assertNotNull(island);
        assertEquals("Dominica", island.getName());
    }

    @Test
    public void testIslandFindByName() {
        Island island = islandDAO.findByName("Dominica");
        assertNotNull(island);
    }


    // Tests HotelDAO
    @Test
    public void testHotelFindById() {
        Hotel hotel = hotelDAO.findById(1);
        assertNotNull(hotel);
        assertEquals(1, hotel.getId());
    }

    @Test
    public void testHotelFindByName() {
        String hotelName = "MonHotel";
        List<Hotel> hotels = hotelDAO.findByName(hotelName);
        assertNotNull(hotels);
        for (Hotel hotel : hotels) {
            assertNotNull(hotelName, hotel.getName());
        }
    }

    @Test
    public void testHotelFindByIsland() {
        Island island = islandDAO.findById(1);
        List<Hotel> hotels = hotelDAO.findByIsland(island);
        assertNotNull(hotels);
        assertFalse(hotels.isEmpty());
    }

    @Test
    public void testHotelFindAll() {
        List<Hotel> hotels = hotelDAO.findAll();
        assertNotNull(hotels);
    }

    // Tests SiteDAO
    @Test
    public void testSiteFindById() {
        Site site = siteDAO.findById(1);
        assertNotNull(site);
        assertEquals(1, site.getId());
    }

    @Test
    public void testSiteFindByName() {
        String siteName = "FortDelgres";
        List<Site> sites = siteDAO.findByName(siteName);
        assertNotNull(sites);
    }

    @Test
    public void testSiteFindByType() {
        String type = "historic";
        List<Site> sites = siteDAO.findByType(type);
        assertNotNull(sites);
        assertFalse(sites.isEmpty());
    }

    @Test
    public void testSiteFindByDescription() {
        String description = "colonial";
        List<Site> sites = siteDAO.findByDescription(description);
        assertNotNull(sites);
        assertFalse(sites.isEmpty());
    }

    @Test
    public void testSiteFindByIsland() {
        Island island = islandDAO.findById(1);
        List<Site> sites = siteDAO.findByIsland(island);
        assertNotNull(sites);
        assertFalse(sites.isEmpty());
    }

    @Test
    public void testSiteFindAll() {
        List<Site> sites = siteDAO.findAll();
        assertNotNull(sites);
    }

    @Test
    public void testSiteFindByCriteria() {
        UserCriteria criteria = new UserCriteria();
        criteria.setTypesite(TypeSite.HOBBIES);
        criteria.setDescriptionSite("plage");
        List<Site> sites = siteDAO.findByCriteria(criteria);
        assertNotNull(sites);
        // Peut être vide si aucun site ne correspond exactement
    }
}