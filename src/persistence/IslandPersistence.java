package persistence;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import business.Island;
import dao.IslandDAO;
<<<<<<< HEAD
import persistence.extendeddb.ExtendedDatabaseAPI;
=======
import persistence.extendeddb.BdeApi;
>>>>>>> Test
import persistence.extendeddb.jdbc.SQLResult;
import persistence.extendeddb.jdbc.SQLResults;

public class IslandPersistence implements IslandDAO {

    public IslandPersistence() {
        // Constructeur privé pour empêcher l'instanciation
    }

    private static SQLResults getIslandsResults() {
        SQLResults sqlResults = null;
<<<<<<< HEAD
        ExtendedDatabaseAPI database = Database.getConnection();
=======
        BdeApi database = Database.getConnection();
>>>>>>> Test
        try {
            sqlResults = database.simpleQuery("SELECT * FROM Island");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sqlResults;
    }

    private static Island getIslandObject(SQLResult tuple) {
        int id = Integer.parseInt(tuple.getAttribute("id"));
        String name = tuple.getAttribute("name");
        return new Island(id, name);
    }

    public static List<Island> getIslands() {
        List<Island> islands = new LinkedList<>();
        SQLResults tuples = getIslandsResults();
        for (SQLResult tuple : tuples) {
            islands.add(getIslandObject(tuple));
        }
        return islands;
    }

    public static Island getIslandById(int id) {
<<<<<<< HEAD
        ExtendedDatabaseAPI database = Database.getConnection();
=======
        BdeApi database = Database.getConnection();
>>>>>>> Test
        SQLResults results = null;
        try {
            results = database.simpleQuery("SELECT * FROM Island WHERE id = " + id);
            if (results.hasNext()) {
                return getIslandObject(results.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Island findById(int id) {
        return getIslandById(id);
    }

    @Override
    public Island findByName(String name) {
        Island result = null;
<<<<<<< HEAD
        ExtendedDatabaseAPI database = Database.getConnection();
=======
        BdeApi database = Database.getConnection();
>>>>>>> Test
        try {
            SQLResults results = database.simpleQuery("SELECT * FROM Island WHERE name = '" + name + "'");
            if (results.hasNext()) {
                result = getIslandObject(results.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Island> findByIsland(Island island) {
        List<Island> islands = new LinkedList<>();
        if (island == null) {
            return islands;
        }
<<<<<<< HEAD
        ExtendedDatabaseAPI database = Database.getConnection();
=======
        BdeApi database = Database.getConnection();
>>>>>>> Test
        try {
            SQLResults results = database.simpleQuery("SELECT * FROM Island WHERE id = " + island.getId());
            while (results.hasNext()) {
                islands.add(getIslandObject(results.next()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return islands;
    }
}