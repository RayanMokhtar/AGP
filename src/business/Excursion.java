/**
 * 
 */
package business;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Excursion {
    private Hotel hotelDeparture;
    private Hotel hotelArrival;
    private List<VisitTransport> placesVisited;
    private double durationTransport; // cumul des transports durée totale transport par excursion
    private double excursionCost;     // somme des coûts (transport + éventuels frais)

    public Excursion(Hotel hotelDeparture) {
        this.hotelDeparture = hotelDeparture;
        this.hotelArrival = hotelDeparture; // par défaut le même
        this.placesVisited = new ArrayList<>();
        this.durationTransport = 0.0;
        this.excursionCost = 0.0;
    }

    /**
     * Ajoute une étape (site + transport) à l'excursion.
     */
    public void addVisitation(VisitTransport visitTransport) {
        placesVisited.add(visitTransport);
        durationTransport += visitTransport.getDurationVisit();
        excursionCost += visitTransport.getCostVisit();
    }

    // Getters & Setters
    public void setHotelArrival(Hotel hotelArrival) {
        this.hotelArrival = hotelArrival;
    }

    public Hotel getHotelDeparture() {
        return hotelDeparture;
    }

    public Hotel getHotelArrival() {
        return hotelArrival;
    }

    public double getDurationTransport() {
        return durationTransport;
    }

    public double getExcursionCost() {
        return excursionCost;
    } 

    public List<VisitTransport> getPlacesVisited() {
        return placesVisited;
    }

    public void addToExcursionCost(double cost) {
        this.excursionCost += cost;
    }

	@Override
	public String toString() {
		return "Excursion [hotelDeparture=" + hotelDeparture + ", hotelArrival=" + hotelArrival + ", placesVisited="
				+ placesVisited + ", durationTransport=" + durationTransport + ", excursionCost=" + excursionCost + "]";
	}
}
