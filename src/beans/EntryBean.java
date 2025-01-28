/**
 * 
 */
package beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import business.Offer;
import business.Site;
import business.tools.OffersBuilder;
import business.tools.UserCriteria;
import persistence.PlacePersistence;

/**
 *
 */
@ManagedBean
@SessionScoped
public class EntryBean {
	private UserCriteria user = new UserCriteria();

	private String intensity;
	private List<Site> place = new LinkedList<Site>(); 
	private List<Offer> offer = new LinkedList<Offer>();
	private Offer firstoffer;
	
	public EntryBean() {
		
	}

	public String startOffer() {
		int exurcionintensity = 0;
		
		if(intensity.equals("faible")) {
			exurcionintensity = 1;
		} else if(intensity.equals("moyen")) {
			exurcionintensity = 2;
		} else {
			exurcionintensity = 3;
		}
		
		user.setExcursionsFrequency(exurcionintensity);
		OffersBuilder build = new OffersBuilder(user);
		offer=build.buildOffers();

		if(!offer.isEmpty()) {
			firstoffer=offer.get(0);
			
			return "offer";
		}
		else {
			return "error";
		}
    }
	
	public String startSimulation() {
		if(user.getPlaceType().equals("historic")) {
			place=PlacePersistence.getHistoricPlaces(getKeyWord());
			System.out.println(place.size());
		}
		else if(user.getPlaceType().equals("activity")) {
			place=PlacePersistence.getActivityPlaces(getKeyWord());
			System.out.println(place.size());
		}
		else {
			place=PlacePersistence.getPlaces(getKeyWord());
			System.out.println(place);
		}
		return "result";
    }
	
	public List<SelectItem> getTypes(){
		List<SelectItem> items = new ArrayList<SelectItem>();
		LinkedList<String> types = new LinkedList<String>();
		String type;
		
		types.add("all");
		types.add("historic");
		types.add("activity");
		
		Iterator<String> supportedLocales = types.iterator();
		
		while (supportedLocales.hasNext()) {
			type = supportedLocales.next();	
			items.add(new SelectItem(type, type));
		}
		
		return items;
	}
	
	public List<SelectItem> getIntensities(){
		List<SelectItem> items = new ArrayList<SelectItem>();
		LinkedList<String> intensities = new LinkedList<String>();
		String type;
		
		intensities.add("faible");
		intensities.add("moyenne");
		intensities.add("fort");
		
		Iterator<String> supportedLocales = intensities.iterator();
		
		while (supportedLocales.hasNext()) {
			type  = supportedLocales.next();	
			items.add(new SelectItem(type, type));
		}
		
		return items;
	}

	public UserCriteria getUser() {
		return user;
	}

	public void setUser(UserCriteria user) {
		this.user = user;
	}
	
	public double getTransport() {
		return user.getMaximumTransportDuration();
	}

	public void setTransport(double transport) {
		user.setMaximumTransportDuration(transport);
	}
	
	public Offer getFirstoffer() {
		return firstoffer;
	}

	public void setFirstoffer(Offer firstoffer) {
		this.firstoffer = firstoffer;
	}
	
	public String getKeyWord() {
		return user.getKeywords();
	}


	public void setKeyWord(String keyWord) {
		user.setKeywords(keyWord);
	}


	public int getNumberDay() {
		return user.getNumberOfDays();
	}


	public void setNumberDay(int numberDay) {
		user.setNumberOfDays(numberDay);
	}


	public String getType() {
		return user.getPlaceType();
	}


	public void setType(String type) {
		user.setPlaceType(type);
	}


	public int getPriceMin() {
		return user.getMinimumPrice();
	}


	public void setPriceMin(int priceMin) {
		user.setMinimumPrice(priceMin);
	}


	public int getPriceMax() {
		return user.getMaximumPrice();
	}


	public void setPriceMax(int priceMax) {
		user.setMaximumPrice(priceMax);
	}


	public String getIntensity() {
		return intensity;
	}


	public void setIntensity(String intensity) {
		this.intensity = intensity;
	}
	
	public List<Site> getPlace() {
		return place;
	}
	
	public void setPlace(List<Site> place) {
		this.place = place;
	}
	
	public List<Offer> getOffer() {
		return offer;
	}

	public void setOffer(List<Offer> offer) {
		this.offer = offer;
	}
}
