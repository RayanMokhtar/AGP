/**
 * 
 */
package business.tools;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import business.exceptions.InsufficientBudgetException;
import business.Boat;
import business.Bus;
import business.Coordinates;
import business.Excursion;
import business.Hotel;
import business.Island;
import business.Offer;
import business.Site;
import business.Transport;
import business.VisitTransport;
import business.TransportFactory;
import persistence.HotelPersistence;
import persistence.IslandPersistence;
import persistence.SitePersistence;
import java.util.Comparator;
import static business.TransportUtils.distanceBetween;
import business.tools.HotelSelector;
import business.tools.SiteSelector;
import dao.HotelDAO;
import dao.SiteDAO;

/**
 *
 */

public class OfferBuilder {
	

	 private HotelSelector hotelSelector;
	 private SiteSelector siteSelector;

	    // Setters pour l'injection
	    public void setHotelSelector(HotelSelector hotelSelector) {
	        this.hotelSelector = hotelSelector;
	        System.out.println("HotelSelector injecté dans OfferBuilder");
	    }

	    public void setSiteSelector(SiteSelector siteSelector) {
	        this.siteSelector = siteSelector;
	        System.out.println("SiteSelector injecté dans OfferBuilder");
	    }
    // ---------------------------------------------------------------------------
    //                1) Méthode pour construire une seule offre
    // ---------------------------------------------------------------------------
    /**
     * Construit une offre (Offer) à partir de critères utilisateur (UserCriteria)
     * en démarrant à un hôtel particulier (startHotel).
     * 
     * Étapes clés :
     *  - Vérifier budget min (si l'hôtel dépasse le maxPrice, on lève l'exception).
     *  - Pour chaque jour, soit on fait une excursion (visites de sites), soit on reste à l'hôtel selon la fréquence et le budget .
     *  - En fin de journée, on cherche l'hôtel le plus proche (si budget OK) sinon on revient à l'hôtel du matin.
     *  - On retire les sites visités de la liste `sites`.
     *  - On ajoute chaque hôtel utilisé dans l'Offer.
     *  - On calcule le prix final de l'offre en cumulant le coût de l'hôtel chaque jour + transport + entrées de sites.
     */
    public Offer buildOffer(UserCriteria criteria, Hotel startHotel) throws InsufficientBudgetException {
    	
    	
    	
        // --- 1) Récupérer la liste d'hôtels et de sites, filtrés par les critères ---
        List<Hotel> hotels = hotelSelector.getHotelsByCriteria(criteria);
        List<Site> sites = siteSelector.getSitesByCriteria(criteria);

        // Vérif de base
        if (hotels.isEmpty() || sites.isEmpty()) {
            throw new InsufficientBudgetException("Aucun hôtel ou site disponible selon les critères !");
        }

        // --- 2) Vérifier si l'hôtel le moins cher dépasse le budget total (cas extrême) ---
        double hotelCostForAllDays = startHotel.getPricePerDay() * criteria.getNbDays();
        if (hotelCostForAllDays > criteria.getMaxPrice()) {
            throw new InsufficientBudgetException("L'hôtel de départ dépasse le budget maximum !");
        }

        // --- 3) Création de l'Offer ---
        Offer offer = new Offer(criteria.getNbDays());

        // On ajoute l'hôtel de départ dans la liste d'hôtels de cette offre
        offer.addHotel(startHotel);

        // --- 4) Calcul du budget par jour ---
        double dailyBudget = criteria.getMaxPrice() / criteria.getNbDays();

        // --- 5) Déterminer la fréquence d'excursion selon l'intensité ---
        int excursionFrequency = 1;
        switch (criteria.getIntensity()) {
            case RELAX:
                excursionFrequency = 3;
                break;
            case MODERE:
                excursionFrequency = 2;
                break;
            case INTENSE:
                excursionFrequency = 1;
                break;
        }

        // --- 6) Variables pour la boucle de jours ---
        Hotel currentHotel = startHotel; // hôtel où on se trouve en début de journée
        double totalOfferCost = 0.0;     // On cumulera tous les coûts (hôtel + transport + sites) ici

        // --- 7) Boucle sur chaque jour ---
        for (int day = 1; day <= criteria.getNbDays(); day++) {

            // Crée l'excursion pour la journée (on part du currentHotel)
            Excursion excursion = new Excursion(currentHotel);

            // On paye l'hôtel de départ pour ce jour (une nuit par jour).
            excursion.addToExcursionCost(currentHotel.getPricePerDay());
            double dailyCost = currentHotel.getPricePerDay(); // cumul transport + site + hotel sur la journée
            double dailyTransportDuration = 0.0;

            // Vérifie si on fait une excursion (visite de sites)
            boolean doExcursion = (day % excursionFrequency == 0 || excursionFrequency == 1);

            // Position courante
            Coordinates currentPosition = currentHotel.getCoordinates();
            Island currentIsland = currentHotel.getIsland();

            if (doExcursion) {
                // On visite jusqu'à visitedPlacesPerDay sites max
                for (int p = 1; p <= criteria.getVisitedPlacesPerDay(); p++) {
                    if (sites.isEmpty()) {
                        // plus de sites à visiter
                        break;
                    }

                    // 1) Trouver le site le plus proche de la position courante
                    Site nearestSite = getNearestSiteFromSomePlace(currentPosition, sites);
                    if (nearestSite == null) {
                        break; // plus de sites
                    }

                    // 2) Calculer la distance
                    double distance = distanceBetween(currentPosition, nearestSite.getCoordinates());

                    // Budget restant sur la journée
                    double remainingBudgetForDay = dailyBudget - dailyCost;

                    // 3) Choix du transport
                    Transport transport = TransportFactory.getTransport(currentIsland, nearestSite.getIsland(), remainingBudgetForDay);
                    if (transport == null) {
                        // pas assez de budget => on arrête la visite
                        break;
                    }

                    // 4) Calcul du temps de transport + coût
                    double transportTime = distance / transport.getSpeedPerKm();
                    double costTransport = distance * transport.getPricePerKm();

                    double totalDurationVisit = transportTime + nearestSite.getDuration(); // sert à determiner temps de la visite 
                    double visitCost = costTransport + nearestSite.getEntryPrice(); //cout site + transport 

                    // 5) Vérifications
                    if (totalDurationVisit < 16.0
                        && (dailyCost + visitCost) <= dailyBudget
                        && (dailyTransportDuration + transportTime) <= criteria.getComfort()) {

                        // OK on visite
                        dailyCost += visitCost;
                        dailyTransportDuration += transportTime;

                        VisitTransport vt = new VisitTransport(nearestSite, transport, totalDurationVisit, visitCost);
                        excursion.addVisitation(vt);

                        // On retire le site de la liste globale => plus revisit
                        sites.remove(nearestSite);

                        // Mise à jour de la position courante
                        currentPosition = nearestSite.getCoordinates();
                        currentIsland = nearestSite.getIsland();
                    } else {
                        // On ne peut pas visiter => break
                        break;
                    }
                }
            }
            // Fin des éventuelles visites de sites

            // --- Retour (ou changement) d'hôtel en fin de journée ---
            double remainingBudget = dailyBudget - dailyCost;  // budget qu'il reste pour le retour
            Hotel arrivalHotel = findNearestHotelIfPossible(currentPosition, currentIsland, hotels, remainingBudget, 
                                                            dailyTransportDuration, criteria.getComfort());
            if (arrivalHotel == null) {
                // Si c'est null => on reste dans le currentHotel (trop cher pour bouger et on suppose qu'on rentre à pieds) .
                excursion.setHotelArrival(currentHotel);
            } else {
                // On a trouvé un nouvel hôtel => on l'ajoute à l'offre
                excursion.setHotelArrival(arrivalHotel);
                offer.addHotel(arrivalHotel);

                // On doit calculer et ajouter le coût du trajet vers cet hôtel
                double distToHotel = distanceBetween(currentPosition, arrivalHotel.getCoordinates());
                Transport arrTransport = TransportFactory.getTransport(currentIsland, arrivalHotel.getIsland(), remainingBudget);
                double arrTime = distToHotel / arrTransport.getSpeedPerKm();
                double arrCost = distToHotel * arrTransport.getPricePerKm();

                dailyCost += arrCost; 
                excursion.addToExcursionCost(arrCost);

                // On met à jour le currentHotel pour le lendemain
                currentHotel = arrivalHotel;
            }

            // On ajoute le coût de cette excursion dans le total
            totalOfferCost += excursion.getExcursionCost();

            // Ajoute l'excursion à l'offre
            offer.addExcursion(excursion);
        }

        // --- 8) On met le total calculé dans l'Offer ---
        offer.addToFinalPrice(totalOfferCost);

        return offer;
    }



    
//méthode pour générer plusieurs offres , ce qui change c'est mon hotel de base je vais prendre le moins cher du plus cher
// et si j'ai pas assez dhotels pour générer mes offres je coupe l'algorithme
    
    public List<Offer> generateOffers(UserCriteria criteria, int numberOfOffers) {
        List<Offer> offers = new ArrayList<>();
        
        // Récupération et filtrage des hôtels selon les critères
        List<Hotel> hotels = hotelSelector.getHotelsByCriteria(criteria);
        

        // Si aucun hôtel, on retourne une liste vide
        if (hotels.isEmpty()) {
            return offers;
        }

        // 1) Trier la liste d’hôtels par ordre croissant de pricePerDay
        hotels.sort(Comparator.comparingDouble(Hotel::getPricePerDay));
        // Cela place l’hôtel le moins cher en premier et le plus cher en dernier.

        // 2) Générer les offres
        int count = 0;
        while (count < numberOfOffers && !hotels.isEmpty()) {
            // On prend le premier hôtel de la liste (le moins cher actuellement)
            Hotel currentHotel = hotels.get(0);

            try {
                // On construit l’offre avec cet hôtel
                Offer offer = buildOffer(criteria, currentHotel);
                offers.add(offer);
            } catch (InsufficientBudgetException e) {
                // Gestion spécifique de InsufficientBudgetException
                System.err.println("Impossible de générer l'offre n°" + (count + 1) 
                                   + " avec l'hôtel " + currentHotel.getName()
                                   + " => " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Erreur inattendue lors de la génération de l'offre n°" + (count + 1) 
                                   + " avec l'hôtel " + currentHotel.getName()
                                   + " => " + e.toString());
                e.printStackTrace();
            }

            // On retire cet hôtel de la liste pour ne pas le réutiliser
            hotels.remove(0);

            // Incrémente le compteur d’offres générées
            count++;
        }

        // 3) Trier les offres par ordre croissant de finalPrice
        offers.sort(Comparator.comparingDouble(Offer::getFinalPrice));

        return offers;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    // ---------------------------------------------------------------------------
    //       3) Méthodes utilitaires internes
    // ---------------------------------------------------------------------------

    /**
     * Cherche l'hôtel le plus proche de la position courante,
     * puis vérifie si on peut y aller en transport (distance, budget, confort).
     * Si c'est trop cher, ou le transport est null => renvoie null => on reste sur place.
     *
     * Sinon, renvoie l'hôtel trouvé.
     */
    private static Hotel findNearestHotelIfPossible(Coordinates currentPos, 
                                                    Island currentIsland,
                                                    List<Hotel> hotels,
                                                    double remainingBudget,
                                                    double dailyTransportDuration,
                                                    double maxComfort) 
    {
        Hotel nearest = null;
        double minDist = Double.MAX_VALUE;

        // 1) Trouver l'hôtel le plus proche avec un algorithme simplifié de dJikistra 
        for (Hotel h : hotels) {
            double dist = distanceBetween(currentPos, h.getCoordinates());
            if (dist < minDist) {
                minDist = dist;
                nearest = h;
            }
        }

        if (nearest == null) {
            return null;
        }

        // 2) Calculer transport possible
        Transport transport = TransportFactory.getTransport(currentIsland, nearest.getIsland(), remainingBudget);
        if (transport == null) {
            // Pas de budget => on ne peut pas bouger
            return null;
        }

        double travelTime = minDist / transport.getSpeedPerKm();
        double travelCost = minDist * transport.getPricePerKm();

        // 3) Vérifier le confort et budget
        if (travelTime > 16.0 
            || (dailyTransportDuration + travelTime) > maxComfort
            || (travelCost > remainingBudget)) {

        	return null;
        }

        // Sinon, on peut bouger vers cet hôtel
        return nearest;
    }

    
    /**
     * Retourne le site le plus proche dans la liste `availableSites`
     * en fonction de la position courante `currentPos`.
     */
    public static Site getNearestSiteFromSomePlace(Coordinates currentPos, List<Site> availableSites) {
        Site nearest = null;
        double minDist = Double.MAX_VALUE;
        for (Site s : availableSites) {
            double dist = distanceBetween(currentPos, s.getCoordinates());
            if (dist < minDist) {
                minDist = dist;
                nearest = s;
            }
        }
        return nearest;
    }
    
    public static Site getCheapestSite(List<Site> sites) {
        if (sites.isEmpty()) {
            return null;
        }

        Site cheapest = sites.get(0);
        for (Site s : sites) {
            if (s.getEntryPrice() < cheapest.getEntryPrice()) {
                cheapest = s;
            }
        }
        return cheapest;
    }
    
    public static Hotel getCheapestHotel(List<Hotel> hotels) {
        if (hotels.isEmpty()) {
            return null;
        }

        Hotel cheapest = hotels.get(0);
        for (Hotel h : hotels) {
            if (h.getPricePerDay() < cheapest.getPricePerDay()) {
                cheapest = h;
            }
        }
        return cheapest;
    }
    
}