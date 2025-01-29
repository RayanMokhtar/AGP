package business.tools;

import java.util.List;

import dao.HotelDAO;
import business.Hotel;
import business.Island;

public class HotelSelector {

    // Attribut injecté par Spring (setter injection)
    private HotelDAO hotelDAO;

    // Setter appelé par Spring pour l'injection
    public void setHotelDAO(HotelDAO hotelDAO) {
        this.hotelDAO = hotelDAO;
        System.out.println("setter hotel Selector");
    }

    /**
     * Récupère un hôtel par son ID via le DAO
     */
    public Hotel getHotelById(int id) {
        return hotelDAO.findById(id);
    }
    
    
    public List<Hotel> getHotelsByIsland(Island island){
    	return hotelDAO.findByIsland(island);
    }
    /**
     * Récupère la liste d'hôtels par nom
     */
    public List<Hotel> getHotelsByName(String name) {
        return hotelDAO.findByName(name);
    }

    /**
     * Récupère la liste d'hôtels par nombre d'étoiles
     */
    public List<Hotel> getHotelsByStars(int stars) {
        return hotelDAO.findByStars(stars);
    }

    /**
     * Récupère la liste de tous les hôtels
     */
    public List<Hotel> getAllHotels() {
        return hotelDAO.findAll();
    }

    /**
     * Récupère une liste d'hôtels en fonction d'un UserCriteria
     */
    public List<Hotel> getHotelsByCriteria(UserCriteria criteria) {
        return hotelDAO.findByCriteria(criteria);
    }
}
