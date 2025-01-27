/**
 * 
 */
package business;

/**
 *
 */
public abstract class Transport {
	protected double distanceTraveled;
	protected double priceForThisTravel;
	protected double durationForThisTravel;
	
	public Transport(double distanceTraveled) {
		this.distanceTraveled = distanceTraveled;
	}

	public double getDistanceTraveled() {
		return distanceTraveled;
	}

	public double getPriceForThisTravel() {
		return priceForThisTravel;
	}

	public double getDurationForThisTravel() {
		return durationForThisTravel;
	}
}
