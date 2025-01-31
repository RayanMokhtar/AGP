/**
 * 
 */
package business;


/**
 *
 */
public class Boat extends Transport {
    public Boat() {
        super(40.0, 1.0); // ex: 40 km/h, 1 € / km
    }
    @Override
    public String getTransportType() {
        return "BOAT";
    }
	@Override
	public String toString() {
		return "Boat [speedPerKm=" + speedPerKm + ", pricePerKm=" + pricePerKm + "]";
	}
}