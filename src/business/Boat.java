/**
 * 
 */
package business;

import business.tools.Constants;

/**
 *
 */
public class Boat extends Transport {

	public Boat(double distanceTraveled) {
		super(distanceTraveled);
		
		double pricePerKM = Constants.getBoatPricePerKM();
		double kmPerHour = Constants.getBoatKMPerHour();
		
		this.priceForThisTravel = distanceTraveled * pricePerKM;
		this.durationForThisTravel = distanceTraveled / kmPerHour;
	}
	
	@Override
	public String toString() {
		return "Bateau";
	}
}
