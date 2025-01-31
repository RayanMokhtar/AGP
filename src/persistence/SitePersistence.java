package persistence;

import java.util.LinkedList;
import java.util.List;

import business.Coordinates;
import business.Island;
import business.Site;
import business.tools.TypeSite;
import business.tools.UserCriteria;
import dao.SiteDAO;
import persistence.extendeddb.BdeApi;
import persistence.extendeddb.CombinedResult;
import persistence.extendeddb.CombinedResults;

public class SitePersistence implements SiteDAO {

    public SitePersistence() {
    }

    private static CombinedResults getSitesResults(String whereClause, String withClause) {
        CombinedResults mixedResults = null;
        BdeApi database = Database.getConnection();
        String query = "SELECT id, name, type, duration, entryPrice, latitude, longitude, idIsland FROM Site";
       

        if (!whereClause.isEmpty()) {
            query += " WHERE " + whereClause;
        }
        if (!withClause.isEmpty()) {
            query += " WITH " + withClause;
        }

        System.out.println(query);

        try {
            mixedResults = database.combinedQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mixedResults;
    }

    private static Site getSiteObject(CombinedResult tuple) {
        try {
            int id = Integer.parseInt(tuple.getAttribute("id"));
            TypeSite type;
            if (tuple.getAttribute("type").toString().toLowerCase().equals("historic")){
            	type = TypeSite.HISTORIC;
            }
            else { type = TypeSite.HOBBIES;}
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
        CombinedResults tuples = getSitesResults("", keywords);
        for (CombinedResult tuple : tuples) {
            Site s = getSiteObject(tuple);
            if (s != null) {
                sites.add(s);
            }
        }
        return sites;
    }

    public List<Site> getHobbiesSites() {
        List<Site> sites = new LinkedList<>();
        CombinedResults tuples = getSitesResults("type = 'hobbies'", "");
        for (CombinedResult tuple : tuples) {
            Site s = getSiteObject(tuple);
            if (s != null) {
                sites.add(s);
            }
        }
        return sites;
    }

    public List<Site> getHistoricSites() {
        List<Site> sites = new LinkedList<>();
        CombinedResults tuples = getSitesResults("type = 'historic'", "");
        for (CombinedResult tuple : tuples) {
            Site s = getSiteObject(tuple);
            if (s != null) {
                sites.add(s);
            }
        }
        return sites;
    }

    @Override
    public Site findById(int id) {
        CombinedResults tuples = getSitesResults("id = " + id, "");
        if (tuples != null && !tuples.isEmpty()) {
            return getSiteObject(tuples.get(0));
        }
        return null;
    }

    @Override
    public List<Site> findByName(String name) {
        List<Site> sites = new LinkedList<>();
        CombinedResults tuples = getSitesResults("name = '" + name + "'", "");
        for (CombinedResult tuple : tuples) {
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
        CombinedResults tuples = getSitesResults("type = '" + type + "'", "");
        for (CombinedResult tuple : tuples) {
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
        CombinedResults tuples = getSitesResults("", description);
        for (CombinedResult tuple : tuples) {
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
        CombinedResults tuples = getSitesResults("idIsland = " + island.getId(), "");
        for (CombinedResult tuple : tuples) {
            Site s = getSiteObject(tuple);
            if (s != null) {
                sites.add(s);
            }
        }
        return sites;
    }

    @Override
    public List<Site> findAll() {
        return getSites("");
    }

    @Override
    public List<Site> findByCriteria(UserCriteria criteria) {
        List<Site> sites = new LinkedList<>();
        if (criteria == null) {
            return sites;
        }
        String where = "type = '" + criteria.getTypesite() + "'";
        String with = criteria.getDescriptionSite();
        CombinedResults tuples = getSitesResults(where, with);
        for (CombinedResult tuple : tuples) {
            Site s = getSiteObject(tuple);
            if (s != null) {
                sites.add(s);
            }
        }
        return sites;
    }
}