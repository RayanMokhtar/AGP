/**
 * 
 */
package business;

/**
 *
 */
public abstract class Transport {
    protected double speedPerKm;   // km/h
    protected double pricePerKm;   // coût par km

    public Transport(double speedPerKm, double pricePerKm) {
        this.speedPerKm = speedPerKm;
        this.pricePerKm = pricePerKm;
    }

    public abstract String getTransportType();

    public double getSpeedPerKm() {
        return speedPerKm;
    }

    public double getPricePerKm() {
        return pricePerKm;
    }
    
    @Override
	public String toString() {
		return "Transport [speedPerKm=" + speedPerKm + ", pricePerKm=" + pricePerKm + "]";
	}

	public static double distanceBetween(Coordinates c1, Coordinates c2) {
        double latDiff = c2.getLatitude() - c1.getLatitude();
        double lonDiff = c2.getLongitude() - c1.getLongitude();
        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff); // calcul de la distance euclidienne . TODO faire des tests afin de trouver une estimation des mesures de distances . 
    }
}
