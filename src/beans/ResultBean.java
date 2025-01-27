/**
 * 
 */
package beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import business.Excursion;
import business.Offer;
import business.Place;

/**
 *
 */
@ManagedBean
@RequestScoped
public class ResultBean {
	
	@ManagedProperty(value = "#{entryBean.place}")
	private List<Place> place;
	
	@ManagedProperty(value = "#{entryBean.offer}")
	private List<Offer> offers;

	private Offer offer;
	
	@PostConstruct
    public void init() {
		offer = offers.get(0);
    }
	
	public List<SelectItem> getOffersSelected() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		Iterator<Offer> supportedLocales = offers.iterator();
		int index = 0;
		
		while (supportedLocales.hasNext()) {
			Offer offer  = supportedLocales.next();	
			items.add(new SelectItem(offer,offer.getHotel().getName()));
			index++;
		}
		
		return items;
	}
	
	public List<Excursion> getExcursions() {
		return offer.getExcursions();
	}

	public void setExcursions(List<Excursion> excursions) {
		offer.setExcursions(excursions);
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		System.out.println(offer);
		this.offer= offer;
	}

	public void startSelection(int index) {
		this.offer = offers.get(index);
	}
	
	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

	public List<Place> getPlace() {
		return place;
	}

	public void setPlace(List<Place> place) {
		this.place = place;
	}
}
