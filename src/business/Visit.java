/**
 * 
 */
package business;

/**
 *
 */
public class Visit {
	private Transport transportDriveway;
	private Place placeToVisit;
	
	public Visit(Transport transportDriveway, Place placeToVisit) {
		this.transportDriveway = transportDriveway;
		this.placeToVisit = placeToVisit;
	}
	
	public double getPrice() {
		return transportDriveway.getPriceForThisTravel()
			   + placeToVisit.getEntrancePrice();
	}
	
	public double getDuration() {
		return transportDriveway.getDurationForThisTravel()
			   + placeToVisit.getVisitDuration();
	}

	public Transport getTransportDriveway() {
		return transportDriveway;
	}

	public void setTransportDriveway(Transport transportDriveway) {
		this.transportDriveway = transportDriveway;
	}

	public Place getPlaceToVisit() {
		return placeToVisit;
	}

	public void setPlaceToVisit(Place placeToVisit) {
		this.placeToVisit = placeToVisit;
	}
}
