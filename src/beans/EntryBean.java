package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import java.util.ArrayList;
import java.util.List;

import business.Offer;
import business.tools.Intensity;
import business.tools.TypeSite;
import business.tools.UserCriteria;
import business.tools.OfferBuilder;
import business.tools.TestOfferBuilder;

@ManagedBean
@ViewScoped // pour le AJAX
public class EntryBean {
	private String searchType;
	private String hotelName;
	private String island;
	private Double hotelPrice;
	private String siteDescription;
	private Double sitePrice;

	// Nouveaux champs basés sur UserCriteria
	private Integer nbDays;
	private Integer minPrice;
	private Integer maxPrice;
	private String intensity;
	private Integer comfort = 1;
	private String descriptionSite;
	private Integer visitedPlacesPerDay;
	private Integer hotelStars;
	private String typeSite;
	
	
	private List<Offer> results = new ArrayList<>();

    public List<Offer> getResults() {
        return results;
    }

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getIsland() {
		return island;
	}

	public void setIsland(String island) {
		this.island = island;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public Double getHotelPrice() {
		return hotelPrice;
	}

	public void setHotelPrice(Double hotelPrice) {
		this.hotelPrice = hotelPrice;
	}


	public String getSiteDescription() {
		return siteDescription;
	}

	public void setSiteDescription(String siteDescription) {
		this.siteDescription = siteDescription;
	}


	public Double getSitePrice() {
		return sitePrice;
	}

	public void setSitePrice(Double sitePrice) {
		this.sitePrice = sitePrice;
	}

	// Getters et setters pour les nouveaux champs
	public Integer getNbDays() {
		return nbDays;
	}

	public void setNbDays(Integer nbDays) {
		this.nbDays = nbDays;
	}

	public Integer getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}

	public Integer getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getIntensity() {
		return intensity;
	}

	public void setIntensity(String intensity) {
		this.intensity = intensity;
	}

	public Integer getcomfort() {
		return comfort;
	}

	public void setcomfort(Integer comfort) {
		this.comfort = comfort;
	}

	public String getDescriptionSite() {
		return descriptionSite;
	}

	public void setDescriptionSite(String descriptionSite) {
		this.descriptionSite = descriptionSite;
	}

	public Integer getVisitedPlacesPerDay() {
		return visitedPlacesPerDay;
	}

	public void setVisitedPlacesPerDay(Integer visitedPlacesPerDay) {
		this.visitedPlacesPerDay = visitedPlacesPerDay;
	}

	public String getTypeSite() {
		return typeSite;
	}

	public void setTypeSite(String typeSite) {
		this.typeSite = typeSite;
	}

	public Integer getHotelStars() {
		return hotelStars;
	}

	public void setHotelStars(Integer hotelStars) {
		this.hotelStars = hotelStars;
	}

	public void search() {
		System.out.println("nbr de jours" + nbDays);
	}

	// Méthode de soumission
	public String submit() {

		// Afficher les données dans la console
		// System.out.println("nbr de jours"+ nbDays);
		// System.out.println("Prix minimum: " + minPrice);
		// System.out.println("Prix max: " + maxPrice);
		// System.out.println("comfort: " + comfort);
		// System.out.println("Description site: " + descriptionSite);
		// System.out.println("Nombre de lieux a visité: " + visitedPlacesPerDay);
		// System.out.println("Type de site: " + typeSite);
		// System.out.println("Nombre d'étoiles: " + starsHotel);

		UserCriteria criteria = new UserCriteria();
		criteria.setNbDays(nbDays != null ? nbDays : 0);
		criteria.setMinPrice(minPrice != null ? minPrice : 0);
		criteria.setMaxPrice(maxPrice != null ? maxPrice : 0);
		criteria.setIntensity(Intensity.valueOf(intensity.toUpperCase())); // Convertir String en enum
		criteria.setComfort(comfort != null ? comfort : 0);
		criteria.setVisitedPlacesPerDay(visitedPlacesPerDay != null ? visitedPlacesPerDay : 0);
		criteria.setDescriptionSite(descriptionSite);
		criteria.setTypesite(TypeSite.valueOf(typeSite.toUpperCase())); // Convertir String en enum
		criteria.setHotelStars(hotelStars != null ? hotelStars : 0);

		// Afficher les données dans la console
		System.out.println("User Criteria:");
		System.out.println("NbDays: " + criteria.getNbDays());
		System.out.println("MinPrice: " + criteria.getMinPrice());
		System.out.println("MaxPrice: " + criteria.getMaxPrice());
		System.out.println("Intensity: " + criteria.getIntensity());
		System.out.println("Comfort: " + criteria.getComfort());
		System.out.println("VisitedPlacesPerDay: " + criteria.getVisitedPlacesPerDay());
		System.out.println("DescriptionSite: " + criteria.getDescriptionSite());
		System.out.println("TypeSite: " + criteria.getTypesite());
		System.out.println("HotelStars: " + criteria.getHotelStars());

		// Génération des offres avec OfferBuilder
        TestOfferBuilder offerBuilder = new TestOfferBuilder();
        results = offerBuilder.generateOffer(); // Met à jour results
	  
		return "null";
	}
	
	 // Méthode pour soumettre les critères de recherche des hôtels
    public String searchHotels() {
        System.out.println("Recherche d'Hôtels:");
        System.out.println("Île: " + island);
        System.out.println("Nom de l'hôtel: " + hotelName);
        System.out.println("Prix minimum: " + minPrice);
        System.out.println("Prix maximum: " + maxPrice);
        System.out.println("Nombre d'étoiles: " + hotelStars);

        return "null"; // Reste sur la même page après la recherche
    }

	 // Méthode pour soumettre les critères de recherche des hôtels
      public String searchSites() {
        System.out.println("Recherche de Sites:");
        System.out.println("Île: " + island);
        System.out.println("Mot-clé du site: " + siteDescription);
        System.out.println("Type de site: " + typeSite);
        System.out.println("Prix minimum: " + minPrice);
        System.out.println("Prix maximum: " + maxPrice);


        return "null"; // Reste sur la même page après la recherche
    }
	
}
