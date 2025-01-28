/**
 * 
 */
package business.tools;

/**
 *
 */
public class UserPreferences {
	private String placeType;
	private String keywords;
	private int minimumPrice;
	private int maximumPrice;
	private int numberOfDays;
	private int excursionsFrequency;
	private double maximumTransportDuration;
	
	public UserPreferences() {
		
	}

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public int getMinimumPrice() {
		return minimumPrice;
	}

	public void setMinimumPrice(int minimumPrice) {
		this.minimumPrice = minimumPrice;
	}

	public int getMaximumPrice() {
		return maximumPrice;
	}

	public void setMaximumPrice(int maximumPrice) {
		this.maximumPrice = maximumPrice;
	}

	public int getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public int getExcursionsFrequency() {
		return excursionsFrequency;
	}

	public void setExcursionsFrequency(int excursionsFrequency) {
		this.excursionsFrequency = excursionsFrequency;
	}

	public double getMaximumTransportDuration() {
		return maximumTransportDuration;
	}

	public void setMaximumTransportDuration(double maximumTransportDuration) {
		this.maximumTransportDuration = maximumTransportDuration;
	}
}
