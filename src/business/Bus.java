/**
 * 
 */
package business;

/**
 *
 */
public class Bus extends Transport {
    public Bus() {
        super(60.0, 0.5); // ex: 60 km/h, 0.5 € / km
    }
    @Override
    public String getTransportType() {
        return "BUS";
    }
	@Override
	public String toString() {
		return "Bus [speedPerKm=" + speedPerKm + ", pricePerKm=" + pricePerKm + "]";
	}
}