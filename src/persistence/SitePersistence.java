package persistence;

import java.util.LinkedList;
import java.util.List;

import business.Coordinates;
import business.Island;
import business.Site;
import business.tools.TypeSite;
import business.tools.UserCriteria;
import dao.SiteDAO;
import persistence.extendeddb.ExtendedDatabaseAPI;
import persistence.extendeddb.MixedResult;
import persistence.extendeddb.MixedResults;

public class SitePersistence implements SiteDAO {

    private SitePersistence() {
        // Constructeur privé pour empêcher l'instanciation
    }

    private static MixedResults getSitesResults(String whereClause, String withClause) {
        MixedResults mixedResults = null;
        ExtendedDatabaseAPI database = Database.getConnection();
        String query = "SELECT id, name, type, duration, entryPrice, latitude, longitude, idIsland FROM Site";

        if (!whereClause.isEmpty()) {
            query += " WHERE " + whereClause;
        }
        if (!withClause.isEmpty()) {
            query += " WITH " + withClause;
        }

        System.out.println(query);

        try {
            mixedResults = database.mixedQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mixedResults;
    }

    private static Site getSiteObject(MixedResult tuple) {
        try {
            int id = Integer.parseInt(tuple.getAttribute("id"));
            TypeSite type = TypeSite.fromString(tuple.getAttribute("type"));
            String name = tuple.getAttribute("name");
            int duration = (int) Double.parseDouble(tuple.getAttribute("duration"));
            double entryPrice = Double.parseDouble(tuple.getAttribute("entryPrice"));
            double latitude = Double.parseDouble(tuple.getAttribute("latitude"));
            double longitude = Double.parseDouble(tuple.getAttribute("longitude"));
            int idIsland = Integer.parseInt(tuple.getAttribute("idIsland"));

            Coordinates coordinates = new Coordinates(latitude, longitude);
            Island island = IslandPersistence.getIslandById(idIsland);
            String description = tuple.getContent();
            return new Site(id, name, entryPrice, duration, coordinates, island, type, description);
        } catch (NumberFormatException e) {
            System.err.println("Erreur de conversion des données: " + e.getMessage());
            return null;
        }
    }

    public static List<Site> getSites(String keywords) {
        List<Site> sites = new LinkedList<>();
        MixedResults tuples = getSitesResults("", keywords);
        for (MixedResult tuple : tuples) {
            Site s = getSiteObject(tuple);
            if (s != null) {
                sites.add(s);
            }
        }
        return sites;
    }

    public static List<Site> getActivitySites(String keywords) {
        List<Site> sites = new LinkedList<>();
        MixedResults tuples = getSitesResults("type = 'activity'", keywords);
        for (MixedResult tuple : tuples) {
            Site s = getSiteObject(tuple);
            if (s != null) {
                sites.add(s);
            }
        }
        return sites;
    }

    public static List<Site> getHistoricSites(String keywords) {
        List<Site> sites = new LinkedList<>();
        MixedResults tuples = getSitesResults("type = 'historic'", keywords);
        for (MixedResult tuple : tuples) {
            Site s = getSiteObject(tuple);
            if (s != null) {
                sites.add(s);
            }
        }
        return sites;
    }

    @Override
    public Site findById(int id) {
        MixedResults tuples = getSitesResults("id = " + id, "");
        if (tuples != null && !tuples.isEmpty()) {
            return getSiteObject(tuples.get(0));
        }
        return null;
    }

    @Override
    public List<Site> findByName(String name) {
        List<Site> sites = new LinkedList<>();
        MixedResults tuples = getSitesResults("name = '" + name + "'", "");
        for (MixedResult tuple : tuples) {
            Site s = getSiteObject(tuple);
            if (s != null) {
                sites.add(s);
            }
        }
        return sites;
    }

    @Override
    public List<Site> findByType(String type) {
        List<Site> sites = new LinkedList<>();
        MixedResults tuples = getSitesResults("type = '" + type + "'", "");
        for (MixedResult tuple : tuples) {
            Site s = getSiteObject(tuple);
            if (s != null) {
                sites.add(s);
            }
        }
        return sites;
    }

    @Override
    public List<Site> findByDescription(String description) {
        List<Site> sites = new LinkedList<>();
        MixedResults tuples = getSitesResults("", description);
        for (MixedResult tuple : tuples) {
            Site s = getSiteObject(tuple);
            if (s != null) {
                sites.add(s);
            }
        }
        return sites;
    }

    @Override
    public List<Site> findByIsland(Island island) {
        List<Site> sites = new LinkedList<>();
        if (island == null) {
            return sites;
        }
        MixedResults tuples = getSitesResults("idIsland = " + island.getId(), "");
        for (MixedResult tuple : tuples) {
            Site s = getSiteObject(tuple);
            if (s != null) {
                sites.add(s);
            }
        }
        return sites;
    }

    @Override
    public List<Site> findAll() {
        List<Site> sites = new LinkedList<>();
        MixedResults tuples = getSitesResults("", "");
        for (MixedResult tuple : tuples) {
            Site s = getSiteObject(tuple);
            if (s != null) {
                sites.add(s);
            }
        }
        return sites;
    }

    @Override
    public List<Site> findByCriteria(UserCriteria criteria) {
        List<Site> sites = new LinkedList<>();
        if (criteria == null) {
            return sites;
        }
        String where = "type = '" + criteria.getTypesite() + "'";
        String with = criteria.getDescriptionSite();
        MixedResults tuples = getSitesResults(where, with);
        for (MixedResult tuple : tuples) {
            Site s = getSiteObject(tuple);
            if (s != null) {
                sites.add(s);
            }
        }
        return sites;
    }
}