package tests.junit.business.tools;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import business.Offer;
import business.exceptions.InsufficientBudgetException;
import business.tools.HotelSelector;
import business.tools.Intensity;
import business.tools.OfferBuilder;
import business.tools.SiteSelector;
import business.tools.TypeSite;
import business.tools.UserCriteria;
import dao.HotelDAO;
import dao.SiteDAO;
import persistence.HotelPersistence;
import persistence.SitePersistence;
import simulation.SimulationUtils;

public class TestGenerationOffres {

    private SiteDAO siteDAO;
    private HotelDAO hotelDAO;
    private HotelSelector hotelSelector;
    private SiteSelector siteSelector;
    private OfferBuilder offerBuilder;

    @Before
    public void setUp() {
        siteDAO = new SitePersistence();
        hotelDAO = new HotelPersistence();
        
        hotelSelector = new HotelSelector();
        siteSelector = new SiteSelector();
        
        hotelSelector.setHotelDAO(hotelDAO);
        siteSelector.setSiteDAO(siteDAO);
        
        offerBuilder = new OfferBuilder();
        offerBuilder.setHotelSelector(hotelSelector);
        offerBuilder.setSiteSelector(siteSelector);
    }

    @Test
    public void testGenerateOffers() throws InsufficientBudgetException {
		UserCriteria criteria = new UserCriteria();
        criteria.setNbDays(5);
        criteria.setMinPrice(20);     // budget min
        criteria.setMaxPrice(2000);    // budget max global
        criteria.setComfort(8);        // heures de transport tolérées/jour
        criteria.setVisitedPlacesPerDay(2);
        criteria.setIntensity(Intensity.MODERE); // ex : MODERE / INTENSE / RELAX
        criteria.setHotelStars(3);     
        criteria.setDescriptionSite("musée");
        criteria.setTypesite(TypeSite.HISTORIC);
        
        List<Offer> offers = offerBuilder.generateOffers(criteria, 3);
        assertNotNull(offers);
        assertTrue(offers.size() > 0);
        SimulationUtils.displayOffers(offers);
        
    }
}