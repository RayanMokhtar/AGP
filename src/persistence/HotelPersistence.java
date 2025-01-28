package persistence;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import business.Coordinates;
import business.Hotel;
import business.Island;
import persistence.extendeddb.ExtendedDatabaseAPI;
import persistence.extendeddb.jdbc.SQLResult;
import persistence.extendeddb.jdbc.SQLResults;

/**
 *
 */
public class HotelPersistence {
    
    private HotelPersistence() {
        
    }
    
    private static SQLResults getHotelsResults(String whereClause) {
        SQLResults sqlResults = null;
        ExtendedDatabaseAPI database = Database.getConnection();
        
        String query = "SELECT * FROM Hotel";
        
        if (!whereClause.isEmpty()) {
            query += " WHERE " + whereClause;
        }
        
        try {
            sqlResults = database.simpleQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return sqlResults;
    }

    private static Hotel getHotelObject(SQLResult tuple) {
        int id;
        String name;
        int stars;
        double pricePerDay;
        double latitude;
        double longitude;
        int idIsland;
        Coordinates coordinates;
        Island island;
        
        String idAttr = tuple.getAttribute("id");
        if (idAttr == null || idAttr.isEmpty()) {
            System.err.println("Erreur : impossible de parser l'ID (valeur nulle ou vide).");
            return null;
        }
        id = Integer.parseInt(idAttr);
        
        name = tuple.getAttribute("name");
        
        String stars_attr = tuple.getAttribute("stars");
        
        String priceAttr = tuple.getAttribute("pricePerDay");
        if (priceAttr == null || priceAttr.isEmpty()) {
            System.err.println("Erreur : impossible de parser pricePerDay (valeur nulle ou vide).");
            return null;
        }
        pricePerDay = Double.parseDouble(priceAttr);
        
        String latAttr = tuple.getAttribute("latitude");
        String lonAttr = tuple.getAttribute("longitude");
        if (latAttr == null || latAttr.isEmpty() 
                || lonAttr == null || lonAttr.isEmpty()) {
            System.err.println("Erreur : latitude ou longitude invalide (nulle ou vide).");
            return null;
        }
        latitude = Double.parseDouble(latAttr);
        longitude = Double.parseDouble(lonAttr);
        coordinates = new Coordinates(latitude, longitude);
        
        String islandAttr = tuple.getAttribute("idIsland");
        if (islandAttr == null || islandAttr.isEmpty()) {
            System.err.println("Erreur : impossible de parser idIsland (valeur nulle ou vide).");
            return null;
        }
        idIsland = Integer.parseInt(islandAttr);
        island = IslandPersistence.getIslandById(idIsland);
        stars = Integer.parseInt(stars_attr);
        
        
        return new Hotel(id, name, pricePerDay, coordinates, island, stars);
    }
    
    public static List<Hotel> getHotels() {
        List<Hotel> hotels = new LinkedList<>();
        SQLResults tuples = getHotelsResults("");
        
        for (SQLResult tuple : tuples) {
            Hotel hotel = getHotelObject(tuple);
            if (hotel != null) {
                hotels.add(hotel);
            }
        }
        
        return hotels;
    }
    
    public static List<Hotel> getHotelsFromIsland(int idIsland) {
        List<Hotel> hotels = new LinkedList<>();
        SQLResults tuples = getHotelsResults("idIsland = " + idIsland);
        
        for (SQLResult tuple : tuples) {
            Hotel hotel = getHotelObject(tuple);
            if (hotel != null) {
                hotels.add(hotel);
            }
        }
        
        return hotels;
    }
}