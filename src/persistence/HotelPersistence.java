package persistence;

import business.Coordinates;
import business.Hotel;
import business.Island;
import business.tools.UserCriteria;
import dao.HotelDAO;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import persistence.extendeddb.BdeApi;
import persistence.extendeddb.jdbc.SQLResult;
import persistence.extendeddb.jdbc.SQLResults;


public class HotelPersistence implements HotelDAO {

    public HotelPersistence() {
    }

    private static SQLResults getHotelsResults(String whereClause) {
        SQLResults sqlResults = null;
        BdeApi database = Database.getConnection();

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

        String starsAttr = tuple.getAttribute("stars");
        if (starsAttr == null || starsAttr.isEmpty()) {
            System.err.println("Erreur : impossible de parser stars.");
            return null;
        }
        stars = Integer.parseInt(starsAttr);

        String priceAttr = tuple.getAttribute("pricePerDay");
        if (priceAttr == null || priceAttr.isEmpty()) {
            System.err.println("Erreur : impossible de parser pricePerDay (valeur nulle ou vide).");
            return null;
        }
        pricePerDay = Double.parseDouble(priceAttr);

        String latAttr = tuple.getAttribute("latitude");
        String lonAttr = tuple.getAttribute("longitude");
        if (latAttr == null || latAttr.isEmpty() || lonAttr == null || lonAttr.isEmpty()) {
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

        return new Hotel(id, name, pricePerDay, coordinates, island, stars);
    }

    @Override
    public Hotel findById(int id) {
        Hotel result = null;
        SQLResults tuples = getHotelsResults("id = " + id);
        if (tuples != null && tuples.hasNext()) {
            result = getHotelObject(tuples.next());
        }
        return result;
    }

    @Override
    public List<Hotel> findByName(String name) {
        List<Hotel> hotels = new LinkedList<>();
        SQLResults tuples = getHotelsResults("name = '" + name + "'");
        while (tuples != null && tuples.hasNext()) {
            Hotel h = getHotelObject(tuples.next());
            if (h != null) {
                hotels.add(h);
            }
        }
        return hotels;
    }

    @Override
    public List<Hotel> findByIsland(Island island) {
        List<Hotel> hotels = new LinkedList<>();
        if (island == null) {
            return hotels;
        }
        SQLResults tuples = getHotelsResults("idIsland = " + island.getId());
        while (tuples != null && tuples.hasNext()) {
            Hotel h = getHotelObject(tuples.next());
            if (h != null) {
                hotels.add(h);
            }
        }
        return hotels;
    }

    @Override
    public List<Hotel> findByStars(int stars) {
        List<Hotel> hotels = new LinkedList<>();
        SQLResults tuples = getHotelsResults("stars = " + stars);
        while (tuples != null && tuples.hasNext()) {
            Hotel h = getHotelObject(tuples.next());
            if (h != null) {
                hotels.add(h);
            }
        }
        return hotels;
    }

    @Override
    public List<Hotel> findAll() {
        List<Hotel> hotels = new LinkedList<>();
        SQLResults tuples = getHotelsResults("");
        if (tuples != null) {
            while (tuples.hasNext()) {
                Hotel hotel = getHotelObject(tuples.next());
                if (hotel != null) {
                    hotels.add(hotel);
                }
            }
        }
        return hotels;
    }

    @Override
    public List<Hotel> findByCriteria(UserCriteria criteria) {
        List<Hotel> hotels = new LinkedList<>();
        if (criteria == null) {
            return hotels;
        }	
        hotels = findByStars(criteria.getHotelStars());
        return hotels;
    }

    // Méthode statique supplémentaire
    public List<Hotel> getHotelsFromIsland(int idIsland) {
        List<Hotel> hotels = new LinkedList<>();
        SQLResults tuples = getHotelsResults("idIsland = " + idIsland);
        while (tuples != null && tuples.hasNext()) {
            Hotel hotel = getHotelObject(tuples.next());
            if (hotel != null) {
                hotels.add(hotel);
            }
        }
        return hotels;
    }
}