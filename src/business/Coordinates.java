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
    // Getters et setters
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	@Override
	public String toString() {
		return "Coordinates [latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	
	/*public static void main(String[] args) {
		Coordinates c1 = new Coordinates(48.862725, 2.287592);
		Coordinates c2 = new Coordinates(48.873792, 2.295028);
		
		System.out.println(c1.getDistanceFrom(c2));
	}*/
}
