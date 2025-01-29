/**
 * 
 */
package business.tools;

/**
 *
 */
public class UserCriteria {
	private int nbDays ; 
	private int minPrice;
	private int maxPrice;
	private Intensity intensity;
	private int comfort; // nombre d'heures maximal passé dans  transport toléré  par jour . 
	private int visitedPlacesPerDay;
	private String descriptionSite ;
	private TypeSite typesite;
	private int hotelStars ; 
	
	
	
	public UserCriteria() {
		
		
	}



	public int getNbDays() {
		return nbDays;
	}



	public int getMinPrice() {
		return minPrice;
	}



	public int getMaxPrice() {
		return maxPrice;
	}



	public Intensity getIntensity() {
		return intensity;
	}



	public int getComfort() {
		return comfort;
	}



	public int getVisitedPlacesPerDay() {
		return visitedPlacesPerDay;
	}



	public String getDescriptionSite() {
		return descriptionSite;
	}



	public TypeSite getTypesite() {
		return typesite;
	}



	public int getHotelStars() {
		return hotelStars;
	}



	public void setNbDays(int nbDays) {
		this.nbDays = nbDays;
	}



	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}



	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}



	public void setIntensity(Intensity intensity) {
		this.intensity = intensity;
	}



	public void setComfort(int comfort) {
		this.comfort = comfort;
	}



	public void setVisitedPlacesPerDay(int visitedPlacesPerDay) {
		this.visitedPlacesPerDay = visitedPlacesPerDay;
	}



	public void setDescriptionSite(String descriptionSite) {
		this.descriptionSite = descriptionSite;
	}



	public void setTypesite(TypeSite typesite) {
		this.typesite = typesite;
	}



	@Override
	public String toString() {
		return "UserCriteria [nbDays=" + nbDays + ", minPrice=" + minPrice + ", maxPrice=" + maxPrice + ", intensity="
				+ intensity + ", comfort=" + comfort + ", visitedPlacesPerDay=" + visitedPlacesPerDay
				+ ", descriptionSite=" + descriptionSite + ", typesite=" + typesite + ", hotelStars=" + hotelStars
				+ "]";
	}



	public void setHotelStars(int hotelStars) {
		this.hotelStars = hotelStars;
	}
	

	
}
