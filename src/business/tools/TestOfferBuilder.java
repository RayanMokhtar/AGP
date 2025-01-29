package business.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import business.Boat;
import business.Bus;
import business.Coordinates;
import business.Excursion;
import business.Hotel;
import business.Island;
import business.Offer;
import business.Site;
import business.VisitTransport;

public class TestOfferBuilder {

	private List<Offer> offers = new ArrayList<>();

    public TestOfferBuilder() {

        Coordinates coord1 = new Coordinates(16.04, -61.7);
        Coordinates coord2 = new Coordinates(16.05, -61.57);
        Coordinates coord3 = new Coordinates(15.28, -61.36);
        Coordinates coord4 = new Coordinates(16.22, -61.35);
        Coordinates coord5 = new Coordinates(15.58, -61.47);

        Island island1 = new Island(0, "Island A");
        Island island2 = new Island(0, "Island B");
        Island island3 = new Island(0, "Island C");
        Island island4 = new Island(0, "Island D");
        Island island5 = new Island(0, "Island E");

        // Création d'hôtels fictifs
        Hotel hotel1 = new Hotel(0, "Fleur d'Épices Hotel", 50.5, coord1, island1, 0);
        Hotel hotel2 = new Hotel(1, "Bois d'Inde Residence", 75.0, coord2, island2, 0);
        Hotel hotel3 = new Hotel(4, "Castle of Comfort", 129.9, coord3, island3, 0);

        // Création de sites fictifs
        Site site1 = new Site(1, "Fort Delgrès", 5.0, 2.0, coord1, island1, TypeSite.HISTORIC, null);
        Site site2 = new Site(0, "Guadeloupe National Park", 10.5, 4.0, coord2, island2, TypeSite.HOBBIES, null);
        Site site3 = new Site(2, "La Caravelle Beach", 0.0, 3.0, coord4, island4, TypeSite.HOBBIES, null);
        Site site4 = new Site(5, "Cabrits National Park", 25.2, 5.0, coord5, island5, TypeSite.HOBBIES, null);

        // Création de transports fictifs
        Bus bus = new Bus();
        Boat boat = new Boat();

        // Création d'excursions fictives
        Excursion excursion1 = new Excursion(hotel1);
        excursion1.setHotelArrival(hotel1);
        excursion1.addToExcursionCost(50.5);

        Excursion excursion2 = new Excursion(hotel1);
        excursion2.setHotelArrival(hotel2);
        excursion2.addVisitation(new VisitTransport(site1, bus, 2.0, 5.0));
        excursion2.addVisitation(new VisitTransport(site2, bus, 4.0, 10.5));
        excursion2.addToExcursionCost(66.2);

        Excursion excursion3 = new Excursion(hotel2);
        excursion3.setHotelArrival(hotel2);
        excursion3.addToExcursionCost(75.0);

        Excursion excursion4 = new Excursion(hotel2);
        excursion4.setHotelArrival(hotel3);
        excursion4.addVisitation(new VisitTransport(site3, bus, 3.0, 0.14));
        excursion4.addVisitation(new VisitTransport(site4, boat, 5.0, 25.85));
        excursion4.addToExcursionCost(101.15);

        Excursion excursion5 = new Excursion(hotel3);
        excursion5.setHotelArrival(hotel3);
        excursion5.addToExcursionCost(129.9);

        // Création d'offres fictives
        Offer offer1 = new Offer(5); // 5 jours
        offer1.setHotels(Arrays.asList(hotel1, hotel2, hotel3));
        offer1.setExcursions(Arrays.asList(excursion1, excursion2, excursion3, excursion4, excursion5));
        offer1.setFinalPrice(422.76);

        Offer offer2 = new Offer(3); // 3 jours
        offer2.setHotels(Arrays.asList(hotel2, hotel3));
        offer2.setExcursions(Arrays.asList(excursion3, excursion4, excursion5));
        offer2.setFinalPrice(300.0);

        // Ajouter les offres fictives à la liste
        offers.add(offer1);
        offers.add(offer2);
    }

    // Méthode pour récupérer toutes les offres
    public List<Offer> generateOffer() {
        return new ArrayList<>(offers); // Retourne la liste des offres existantes
    }
}
