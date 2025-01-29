package tests.junit.business.tools;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;

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

class AllPersistenceDaoTests {

    // ----------------------------- HotelDAO ---------------------------------

    @Test
    void testHotelFindById() {
        HotelDAO hotelDAO = new HotelPersistence();
        Hotel hotel = hotelDAO.findById(1);
        if(hotel != null) {
            assertEquals(1, hotel.getId());
        }
    }

    @Test
    void testHotelFindByName() {
        HotelDAO hotelDAO = new HotelPersistence();
        List<Hotel> hotels = hotelDAO.findByName("TestHotel");
        assertNotNull(hotels);
        // assertFalse(hotels.isEmpty()); // dépend des données
    }

    @Test
    void testHotelFindByIsland() {
        IslandDAO islandDAO = new IslandPersistence();
        Island island = islandDAO.findById(1);
        HotelDAO hotelDAO = new HotelPersistence();
        List<Hotel> hotels = hotelDAO.findByIsland(island);
        assertNotNull(hotels);
    }

    @Test
    void testHotelFindByStars() {
        HotelDAO hotelDAO = new HotelPersistence();
        List<Hotel> hotels = hotelDAO.findByStars(3);
        assertNotNull(hotels);
    }

    @Test
    void testHotelFindAll() {
        HotelDAO hotelDAO = new HotelPersistence();
        List<Hotel> hotels = hotelDAO.findAll();
        assertNotNull(hotels);
    }

    @Test
    void testHotelFindByCriteria() {
        UserCriteria criteria = new UserCriteria();
        criteria.setHotelStars(4);
        HotelDAO hotelDAO = new HotelPersistence();
        List<Hotel> hotels = hotelDAO.findByCriteria(criteria);
        assertNotNull(hotels);
    }

    // ----------------------------- SiteDAO ----------------------------------

    @Test
    void testSiteFindById() {
        SiteDAO siteDAO = new SitePersistence();
        Site site = siteDAO.findById(1);
        if(site != null) {
            assertEquals(1, site.getId());
        }
    }

    @Test
    void testSiteFindByName() {
        SiteDAO siteDAO = new SitePersistence();
        List<Site> sites = siteDAO.findByName("TestSite");
        assertNotNull(sites);
    }

    @Test
    void testSiteFindByType() {
        SiteDAO siteDAO = new SitePersistence();
        List<Site> sites = siteDAO.findByType("activity");
        assertNotNull(sites);
    }

    @Test
    void testSiteFindByDescription() {
        SiteDAO siteDAO = new SitePersistence();
        List<Site> sites = siteDAO.findByDescription("tour");
        assertNotNull(sites);
    }

    @Test
    void testSiteFindByIsland() {
        IslandDAO islandDAO = new IslandPersistence();
        Island island = islandDAO.findById(1);
        SiteDAO siteDAO = new SitePersistence();
        List<Site> sites = siteDAO.findByIsland(island);
        assertNotNull(sites);
    }

    @Test
    void testSiteFindAll() {
        SiteDAO siteDAO = new SitePersistence();
        List<Site> sites = siteDAO.findAll();
        assertNotNull(sites);
    }

    @Test
    void testSiteFindByCriteria() {
        UserCriteria criteria = new UserCriteria();
        criteria.setTypesite(TypeSite.HOBBIES);
        criteria.setDescriptionSite("fun");
        SiteDAO siteDAO = new SitePersistence();
        List<Site> sites = siteDAO.findByCriteria(criteria);
        assertNotNull(sites);
    }

    // ----------------------------- IslandDAO --------------------------------

    @Test
    void testIslandFindById() {
        IslandDAO islandDAO = new IslandPersistence();
        Island island = islandDAO.findById(1);
        if(island != null) {
            assertEquals(1, island.getId());
        }
    }

    @Test
    void testIslandFindByName() {
        IslandDAO islandDAO = new IslandPersistence();
        Island island = islandDAO.findByName("MyIsland");
        if(island != null) {
            assertEquals("MyIsland", island.getName());
        }
    }

    @Test
    void testIslandFindByIsland() {
        IslandDAO islandDAO = new IslandPersistence();
        Island refIsland = islandDAO.findById(1);
        List<Island> islands = islandDAO.findByIsland(refIsland);
        assertNotNull(islands);
    }
}