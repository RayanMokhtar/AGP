/**
 * 
 */
package business.tools;

/**
 *
 */
public class Constants {
	private static double boatPricePerKM = 0.75;
	private static double boatKMPerHour = 30.0;
	private static double busPricePerKM = 0.25;
	private static double busKMPerHour = 80.0;
	
	private Constants() {
		
	}

	public static double getBoatPricePerKM() {
		return boatPricePerKM;
	}

	public static double getBusPricePerKM() {
		return busPricePerKM;
	}

	public static double getBoatKMPerHour() {
		return boatKMPerHour;
	}

	public static double getBusKMPerHour() {
		return busKMPerHour;
	}
}
