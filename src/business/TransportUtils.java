package business;

public class TransportUtils {

    /**
     on utilise ici les distances euclidienne entre deux coordonnées afin d'estimer la distance entre deux endroits géographiques .
     */
    public static double distanceBetween(Coordinates c1, Coordinates c2) {
        double latDiff = c2.getLatitude() - c1.getLatitude();
        double lonDiff = c2.getLongitude() - c1.getLongitude();
        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff);
    }
}
