/**
 * 
 */
package business;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class Offer {
	private Hotel hotel;
	private List<Excursion> excursions;
	private int excursionsFrequency;
	private int numberOfDays;
	
	public Offer(Hotel hotel,
				 int excursionsFrequency,
				 int numberOfDays) {
		
		this.hotel = hotel;
		this.excursions = new LinkedList<Excursion>();
		this.excursionsFrequency = excursionsFrequency;
		this.numberOfDays = numberOfDays;
	}
	
	public void addExcursion(Excursion excursion) {
		excursions.add(excursion);
	}
	
	public boolean hasExcursionOnDay(int day) {
		if (day % excursionsFrequency == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public Excursion getExcursionOfDay(int day) {
		if (day % excursionsFrequency == 0) {
			return excursions.get(day / excursionsFrequency - 1);
		} else {
			return null;
		}
	}
	
	public double getPrice() {
		double excursionsPrice = 0.0;
		
		for (Excursion excursion : excursions) {
			if (excursion.hasVisits()) {
				excursionsPrice += excursion.getPrice();
			}
		}
		
		return excursionsPrice + hotel.getPricePerDay() * numberOfDays;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public List<Excursion> getExcursions() {
		return excursions;
	}

	public void setExcursions(List<Excursion> excursions) {
		this.excursions = excursions;
	}

	public int getExcursionsFrequency() {
		return excursionsFrequency;
	}

	public void setExcursionsFrequency(int excursionsFrequency) {
		this.excursionsFrequency = excursionsFrequency;
	}

	public int getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}
}
