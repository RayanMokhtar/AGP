/**
 * 
 */
package business;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class Excursion {
	private List<Visit> placesToVisit;
	private Transport transportReturnToHotel;
	
	public Excursion() {
		this.placesToVisit = new LinkedList<Visit>();
	}
	
	public void addVisit(Visit visit) {
		placesToVisit.add(visit);
	}
	
	public double getPrice() {
		double visitsPrice = 0.0;
		
		for (Visit visit : placesToVisit) {
			visitsPrice += visit.getPrice();
		}
		
		return visitsPrice + transportReturnToHotel.getPriceForThisTravel();
	}
	
	public double getDuration() {
		double visitsDuration = 0.0;
		
		for (Visit visit : placesToVisit) {
			visitsDuration += visit.getDuration();
		}
		
		return visitsDuration + transportReturnToHotel.getDurationForThisTravel();
	}
	
	public boolean hasVisits() {
		return placesToVisit.size() > 0;
	}

	public Transport getTransportReturnToHotel() {
		return transportReturnToHotel;
	}

	public void setTransportReturnToHotel(Transport transportReturnToHotel) {
		this.transportReturnToHotel = transportReturnToHotel;
	}

	public List<Visit> getPlacesToVisit() {
		return placesToVisit;
	}
}
