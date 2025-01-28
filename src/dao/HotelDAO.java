package dao;

import java.util.List;

import business.Hotel;
import business.Island;
import business.Site;
import business.tools.UserCriteria;
 

public interface HotelDAO {
    Hotel findById(int id);
    
    List<Hotel> findByName(String name);
    
    List<Hotel> findByIsland(Island island);

    // Récupère une liste d'hôtels par nombre d'étoiles
    List<Hotel> findByStars(int stars);

    // Récupère tous les hôtels
    List<Hotel> findAll();

    // Récupère une liste d'hôtels qui correspondent aux critères de l'utilisateur
    List<Hotel> findByCriteria(UserCriteria criteria);

}