package business.tools;

import java.util.ArrayList;
import java.util.List;
import dao.HotelDAO;
import dao.SiteDAO;
import dao.IslandDAO;
import business.Hotel;
import business.Site;
import business.Island;

import persistence.HotelPersistence;
import persistence.IslandPersistence;
import persistence.SitePersistence;
import business.tools.SiteSelector;
import business.tools.HotelSelector;

public class SearchCriteria {
	
	private String critere;
	private String valeur;


	public String getCritere() {
		return critere;
	}


	public void setCritere(String critere) {
		this.critere = critere;
	}


	public String getValeur() {
		return valeur;
	}


	public void setValeur(String valeur) {
		this.valeur = valeur;
	}
	
	public Object getSearchType(String critere, String valeur) {
		HotelDAO hotelDAO ;
        SiteDAO siteDAO;        
        IslandDAO islandDAO;
        List<Hotel> hotelsResult = new ArrayList<>();
        List<Site> sitesResult = new ArrayList<>();
        hotelDAO = new HotelPersistence();
        siteDAO = new SitePersistence();
        islandDAO = new IslandPersistence();
        
        HotelSelector h = new HotelSelector();
        SiteSelector s = new SiteSelector();
        h.setHotelDAO(hotelDAO);
        s.setSiteDAO(siteDAO);
        
        switch (critere) {
            case "hotelName":
                // Recherche des hôtels par nom
                hotelsResult = h.getHotelsByName(valeur);
                return hotelsResult;

            case "hotelWithIslandName":
                // Recherche des hôtels situés sur une île spécifique
                Island island = islandDAO.findByName(valeur);
                if (island != null) {
                    hotelsResult = h.getHotelsByIsland(island);
                } else {
                    System.err.println("Île non trouvée: " + valeur);
                }
                return hotelsResult;

            case "hotelStars":
                // Recherche des hôtels par nombre d'étoiles
                try {
                    int stars = Integer.parseInt(valeur);
                    hotelsResult = h.getHotelsByStars(stars);
                } catch (NumberFormatException e) {
                    System.err.println("Valeur invalide pour les étoiles de l'hôtel: " + valeur);
                }
                return hotelsResult;

            case "siteType":
                // Recherche des sites par type
                sitesResult = s.getSitesByType(valeur);
                return sitesResult;

            case "siteDescription":
                // Recherche des sites par description
                sitesResult = s.getSitesByDescription(valeur);
                return sitesResult;

            case "siteWithIslandName":
                // Recherche des sites situés sur une île spécifique
                Island siteIsland = islandDAO.findByName(valeur);
                if (siteIsland != null) {
                    sitesResult = s.getSitesByIsland(siteIsland);
                } else {
                    System.err.println("Île non trouvée pour les sites: " + valeur);
                }
                return sitesResult;

            default:
                // Si le critère n'est pas reconnu, retourne tous les hôtels
                hotelsResult = h.getAllHotels();
                return hotelsResult;
        }
    }
}