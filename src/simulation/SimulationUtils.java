package simulation;

import java.util.List;

import business.Excursion;
import business.Offer;
import business.VisitTransport;

public class SimulationUtils {
 public static void offerDisplay(Offer offer) {
        System.out.println("========================================");
        System.out.println("Offre");
        System.out.println("Nombre de jours: " + offer.getNbDays());
        System.out.println("Prix final: " + offer.getFinalPrice() + " €");
        System.out.println("Excursions:");
        
        int day = 1;
        for (Excursion excursion : offer.getExcursions()) {
            System.out.println("----------------------------------------");
            System.out.println("  Excursion du jour " + day + ":");
            System.out.println("    - Départ de l'hôtel: " + excursion.getHotelDeparture().getName());
            System.out.println("    - Arrivée à l'hôtel: " + excursion.getHotelArrival().getName());
            System.out.println("    - Coût de l'excursion: " + excursion.getExcursionCost() + " €");
            System.out.println("    - Lieux visités:");
            if (excursion.getPlacesVisited().isEmpty()) {
                System.out.println("      * Pas de lieu à visiter aujourd'hui");
            } else {
                for (VisitTransport visit : excursion.getPlacesVisited()) {
                    System.out.println("      * " + visit.getSite().getName() + " (Transport: " + visit.getTransport().getTransportType() + ", Coût: " + visit.getCostVisit() + " €, Durée: " + visit.getDurationVisit() + " heures)");
                }
            }
            day++;
        }
        System.out.println("========================================");
    }
    public static void displayOffers(List<Offer> offers) {
        for (Offer offer : offers) {
            offerDisplay(offer);
        }
    }
}
    
