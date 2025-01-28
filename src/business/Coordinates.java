/**
 * 
 */
package business;

/**
 *
 */
public class Coordinates {
	private double latitude;
	private double longitude;
	
	public Coordinates(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public double getDistanceFrom(Coordinates coordinates) {
		double latitude2 = coordinates.getLatitude();
		double longitude2 = coordinates.getLongitude();
		
		double earthRadius = 6371000;
		double latitudeDistance = Math.toRadians(latitude2 - latitude);
		double longitudedistance = Math.toRadians(longitude2 - longitude);
		
		double result = Math.pow(Math.sin(latitudeDistance / 2), 2.0)
						+ Math.cos(Math.toRadians(latitude))
						* Math.cos(Math.toRadians(latitude2))
						* Math.pow(Math.sin(longitudedistance / 2), 2.0);
		
		double meters = earthRadius * 2
						* Math.atan2(Math.sqrt(result),
									 Math.sqrt(1 - result));
		
		// Returns the distance between this two coordinates in km
		return meters / 1000;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	/*public static void main(String[] args) {
		Coordinates c1 = new Coordinates(48.862725, 2.287592);
		Coordinates c2 = new Coordinates(48.873792, 2.295028);
		
		System.out.println(c1.getDistanceFrom(c2));
	}*/
}
