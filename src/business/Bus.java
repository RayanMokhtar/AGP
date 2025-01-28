/**
 * 
 */
package business;

import business.tools.Constants;

/**
 *
 */
public class Bus extends Transport {

	public Bus(double distanceTraveled) {
		super(distanceTraveled);
		
		double pricePerKM = Constants.getBusPricePerKM();
		double kmPerHour = Constants.getBusKMPerHour();
		
		this.priceForThisTravel = distanceTraveled * pricePerKM;
		this.durationForThisTravel = distanceTraveled / kmPerHour;
	}
	
	@Override
	public String toString() {
		return "Bus";
	}
}
