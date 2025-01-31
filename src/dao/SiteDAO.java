package dao;
import java.util.List;
import business.Site;
import business.tools.UserCriteria;
import business.Island;

public interface SiteDAO {
	
	Site findById(int id);

    // Récupère une liste de sites par nom
    List<Site> findByName(String name);

    // Récupère une liste de sites par type (culturel, sportif, etc.)
    List<Site> findByType(String type);
    
    List<Site> findByDescription(String description);
    
    List<Site> findByIsland(Island island);

    // Récupère tous les sites
    List<Site> findAll();

    // Récupère une liste de sites qui correspondent aux critères de l'utilisateur
    List<Site> findByCriteria(UserCriteria criteria);
    
    List<Site> getHistoricSites();
    
    List<Site> getHobbiesSites();
    
    
}
