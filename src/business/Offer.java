package business;

import java.util.ArrayList;
import java.util.List;

public class Offer {
    private List<Hotel> hotels;       // Hôtels utilisés lors du séjour
    private List<Excursion> excursions; // Excursions planifiées
    private double finalPrice; // Coût total (hôtels + transports + visites)
    private int nbDays ;

    public Offer(int nbDays) {
        this.hotels = new ArrayList<>();
        this.excursions = new ArrayList<>();
        this.finalPrice = 0.0;
        this.nbDays = nbDays;
    }

    public int getNbDays() {
		return nbDays;
	}

	public void setHotels(List<Hotel> hotels) {
		this.hotels = hotels;
	}

	public void setExcursions(List<Excursion> excursions) {
		this.excursions = excursions;
	}

	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public void setNbDays(int nbDays) {
		this.nbDays = nbDays;
	}

	public void addHotel(Hotel h) {
        if (!this.hotels.contains(h)) {
            this.hotels.add(h);
        }
    }

    public void addExcursion(Excursion ex) {
        this.excursions.add(ex);
    }

    public void addToFinalPrice(double cost) {
        this.finalPrice += cost;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public List<Excursion> getExcursions() {
        return excursions;
    }

	@Override
	public String toString() {
		return "Offer [hotels=" + hotels + ", excursions=" + excursions + ", finalPrice=" + finalPrice + ", nbDays="
				+ nbDays + "]";
	}
}
