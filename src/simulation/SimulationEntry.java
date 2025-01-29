package simulation;

import business.tools.Intensity;
import business.tools.TypeSite;

public class SimulationEntry {
	
	private int nbDays;
    private int minPrice;
    private int maxPrice;
    private Intensity intensity; // Nombre d'excursions durant le séjour
    private int confort;
    private String descriptionSite;
    private int visitedPlacesPerDay;
    private TypeSite typeSite;
    private int hotelStars;
    
	public SimulationEntry(int nbDays, int minPrice, int maxPrice, Intensity intensity, int confort,
			String descriptionSite, int visitedPlacesPerDay, TypeSite typeSite, int hotelStars) {
		this.nbDays = nbDays;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.intensity = intensity;
		this.confort = confort;
		this.descriptionSite = descriptionSite;
		this.visitedPlacesPerDay = visitedPlacesPerDay;
		this.typeSite = typeSite;
		this.hotelStars = hotelStars;
	}
	public int getNbDays() {
		return nbDays;
	}
	public void setNbDays(int nbDays) {
		this.nbDays = nbDays;
	}
	public int getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}
	public int getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}
	public Intensity getIntensity() {
		return intensity;
	}
	public void setIntensity(Intensity intensity) {
		this.intensity = intensity;
	}
	public int getConfort() {
		return confort;
	}
	public void setConfort(int confort) {
		this.confort = confort;
	}
	public String getDescriptionSite() {
		return descriptionSite;
	}
	public void setDescriptionSite(String descriptionSite) {
		this.descriptionSite = descriptionSite;
	}
	public int getVisitedPlacesPerDay() {
		return visitedPlacesPerDay;
	}
	public void setVisitedPlacesPerDay(int visitedPlacesPerDay) {
		this.visitedPlacesPerDay = visitedPlacesPerDay;
	}
	public TypeSite getTypeSite() {
		return typeSite;
	}
	public void setTypeSite(TypeSite typeSite) {
		this.typeSite = typeSite;
	}
	public int getHotelStars() {
		return hotelStars;
	}
	public void setHotelStars(int hotelStars) {
		this.hotelStars = hotelStars;
	}


}
