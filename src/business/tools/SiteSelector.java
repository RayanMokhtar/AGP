package business.tools;

import java.util.List;

import dao.SiteDAO;
import business.Island;
import business.Site;

public class SiteSelector {

    // Attribut injecté par Spring (setter injection)
    private SiteDAO siteDAO;

    // Setter appelé par Spring pour l'injection
    public void setSiteDAO(SiteDAO siteDAO) {
        this.siteDAO = siteDAO;
        System.out.println("setter site Selector");
    }

    /**
     * Récupère un site par son ID
     */
    public Site getSiteById(int id) {
        return siteDAO.findById(id);
    }

    /**
     * Récupère les sites par nom
     */
    public List<Site> getSitesByName(String name) {
        return siteDAO.findByName(name);
    }

    /**
     * Récupère les sites par type (culturel, sportif, etc.)
     */
    public List<Site> getSitesByType(String type) {
        return siteDAO.findByType(type);
    }

    /**
     * Récupère tous les sites
     */
    public List<Site> getAllSites() {
        return siteDAO.findAll();
    }
    
    public List<Site> getSitesByDescription(String description) {
        return siteDAO.findByDescription(description);
    }
    
   public List<Site> getSitesByIsland(Island island){
	   return siteDAO.findByIsland(island);
   }

    
    public List<Site> getSitesByCriteria(UserCriteria criteria) {
        return siteDAO.findByCriteria(criteria);
    }
}
