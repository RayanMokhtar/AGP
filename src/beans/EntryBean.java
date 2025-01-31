package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import business.Hotel;
import business.Offer;
import business.Site;
import business.tools.HotelSelector;
import business.tools.Intensity;
import business.tools.TypeSite;
import business.tools.UserCriteria;
import dao.HotelDAO;
import dao.SiteDAO;
import persistence.HotelPersistence;
import persistence.SitePersistence;
import business.tools.OfferBuilder;
import business.tools.SearchCriteria;
import business.tools.SiteSelector;

@ManagedBean
@SessionScoped
public class EntryBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	
	private String critere;
	private String valeur;
	
	public String getCritere() {
		return critere;
	}

	public String getValeur() {
		return valeur;
	}

	public void setCritere(String critere) {
		this.critere = critere;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	private List<Offer> results = new ArrayList<>();
	private List<Hotel> hotelResults;
	private Object siteResults;

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
		System.out.println("ok");
		this.nbDays = nbDays;
	}

	public Integer getMinPrice() {
		System.out.println("dasn getminPrice");
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
	

    public String search() {
    	SearchCriteria searchCriteria = new SearchCriteria(); 
        searchCriteria.getSearchType(critere, valeur);
        Object results = searchCriteria.getSearchType(this.critere, this.valeur);
        if (results instanceof List<?>) {
            if (!((List<?>) results).isEmpty()) {
                if (((List<?>) results).get(0) instanceof Hotel) {
                    this.hotelResults = (List<Hotel>) results;
                    this.siteResults = null;
                    System.out.println("cas1");
                    System.out.println(hotelResults);
                    System.out.println(siteResults);
                } else if (((List<?>) results).get(0) instanceof Site) {
                    System.out.println("cas2");
                    this.siteResults = (List<Site>) results;
                    this.hotelResults = null;
                    System.out.println(hotelResults);
                    System.out.println(siteResults);
                }
            } else {
            	System.out.println("cas3");
                this.hotelResults = null;
                this.siteResults = null;
            }
            
        }
     System.out.println("cas4");
       


    return "null";
    }

	// Méthode de soumission
	public String submit() {

		System.out.println("nbr de jours");
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
		
		System.out.println(criteria);

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
		SiteDAO siteDAO = new SitePersistence();
		HotelDAO hotelDAO = new HotelPersistence();
		
		HotelSelector hotelselector = new HotelSelector();
		SiteSelector siteselector = new SiteSelector();
		
		hotelselector.setHotelDAO(hotelDAO);
		siteselector.setSiteDAO(siteDAO);
		
		OfferBuilder offerbuilder = new OfferBuilder();
		
		offerbuilder.setHotelSelector(hotelselector);
		offerbuilder.setSiteSelector(siteselector);
		
		results = offerbuilder.generateOffers(criteria,5);
		
		for (Offer offer : results) {
		    System.out.println("Offer\n");
		    System.out.println(offer);
		}
		
	  
		return "null";
	}
	
}
