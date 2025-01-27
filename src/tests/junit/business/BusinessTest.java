package tests.junit.business;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import business.*;


class BusinessTest {

	/* =========== TEST COORDINATES =========== */ 
	@Test
	@DisplayName("Should be approximately close")
	void testGetDistanceFrom() {
		Coordinates departure = new Coordinates(15.31, -61.35);
		Coordinates arrival = new Coordinates(14.49, -60.99);
		
		double actualResult = departure.getDistanceFrom(arrival);
		double expectedMinResult = 30; // closest distance between Dominique and Martinique (search on GoogleMap)
		double expectedMaxResult = 200; // furthest distance between Dominique and Martinique (search on GoogleMap)
		arrival.setLatitude(20);
		arrival.setLongitude(-50);
		assertTrue(actualResult >= expectedMinResult && actualResult <= expectedMaxResult);
	}

	@Test
	void testIsland() {
		Island island = new Island(0);
		Island island2 = new Island(1, "dom");

		boolean isEquals = island.equals(island2);
		island.incrementScore();
		int id = island.getId();
		String name = island.getName();
		int score = island.getScore();

		island.setId(2);
		island.setName("guadeloupe");
		island.setScore(100);
	}

	@Test
	void testHotel() {
		Coordinates hotelCoord = new Coordinates(15.31, -61.35);
		Coordinates arrival = new Coordinates(14.49, -60.99);
		Island isle = new Island(0);
		Hotel hotel = new Hotel(1, "Bateliere", 50, hotelCoord, isle);
		
		hotel.incrementScore();
		int id = hotel.getId();
		String name = hotel.getName();
		double price = hotel.getPricePerDay();
		Coordinates coord = hotel.getCoordinates();
		Island isle2 = hotel.getIsland();
		int score = hotel.getScore();
		
		hotel.setCoordinates(arrival);
		hotel.setId(3);
		hotel.setIsland(isle);
		hotel.setName("marti");
		hotel.setPricePerDay(60);
		hotel.setScore(600);
	}
	
	@Test
	void testPlace() {
		Coordinates coord = new Coordinates(14.49, -60.99);
		Coordinates coord2 = new Coordinates(17.49, -40.99);
		Island isle = new Island(0);
		Place place = new Place(0, "musée", true, 3, 20, "Cool", coord, isle, 50);
		
		int id = place.getId();
		String name = place.getName();
		boolean isH = place.isHistoric();
		String desc = place.getDescription();
		Coordinates coord3 = place.getCoordinates();
		Island isle2 = place.getIsland();
		int score = place.getScore();
		
		place.setCoordinates(coord2);
		place.setId(3);
		place.setIsland(isle);
		place.setName("marti");
		place.setScore(600);
		place.setHistoric(false);
		place.setVisitDuration(4);
		place.setDescription("Trop cool");
		place.setEntrancePrice(20);
	}   
	@Test
	@DisplayName("Transport should be equals")
	void testTransport() {
		Transport bus = new Bus(70);
		Transport boat = new Boat(200);

		double actualBusResult = bus.getPriceForThisTravel();
		double expectedBusResult = 17.5; // 70 * 0.25

		double actualBoatResult = boat.getPriceForThisTravel();
		double expectedBoatResult = 150; // 200 * 0.75
		
		double distance = boat.getDistanceTraveled();
		
		assertTrue(actualBusResult == expectedBusResult);
		assertTrue(actualBoatResult == expectedBoatResult);
	}

	@Test
	@DisplayName("Visit should be equals")
	void testVisit() {
		Transport bus = new Bus(70);
		Place museum = new Place(0); // free museum on sundays
		Visit visit = new Visit(bus, museum);

		double actualPriceResult = visit.getPrice();
		double expectedPriceResult = 17.5;

		double actualDurationResult = visit.getDuration();
		double expectedMinDurationResult = 0;
		double expectedMaxDurationResult = 1;
		
		Transport newBus = visit.getTransportDriveway();
		Place newPlace = visit.getPlaceToVisit();
		
		visit.setPlaceToVisit(new Place(0));
		visit.setTransportDriveway(bus);

		assertTrue(actualPriceResult == expectedPriceResult);
		assertTrue(actualDurationResult >= expectedMinDurationResult && actualDurationResult <= expectedMaxDurationResult);
	}

	@Test
	@DisplayName("Excursion should be smooth")
	void testExcursion() {
		Transport boat = new Boat(200);
		Place beach = new Place(0); // beach is free to go. Or is it?
		Visit visit = new Visit(boat, beach);
		Excursion excursion = new Excursion();

		excursion.addVisit(visit);

		int actualListResult = excursion.getPlacesToVisit().size();
		int expectedListResult = 1;

		Transport transport = excursion.getTransportReturnToHotel();
		excursion.setTransportReturnToHotel(boat);
		Boolean hasVisits = excursion.hasVisits();
		double price = excursion.getPrice();
		double duration = excursion.getDuration();

		assertEquals(actualListResult, expectedListResult);
	}

	@Test
	@DisplayName("Offer with a lot of excursion")
	void testOffer() {
		// For excursion
		Transport boat = new Boat(200);
		Place beach = new Place(0); // beach is free to go. Or is it?
		Visit visit = new Visit(boat, beach);

		Transport bus = new Bus(20);
		Place foret = new Place(0); // beach is free to go. Or is it?
		Visit visit2 = new Visit(bus, foret);
		
		Excursion excursion = new Excursion();

		excursion.addVisit(visit);
		excursion.addVisit(visit2);
		
		Hotel hotel = new Hotel(0);
		Offer offer = new Offer(hotel, 4, 20);

		offer.addExcursion(excursion);
		boolean bool = offer.hasExcursionOnDay(4);
		Excursion excursion2 = offer.getExcursionOfDay(4);
		Hotel hotel2 = offer.getHotel();
		List<Excursion> excursions = offer.getExcursions();
		int freq = offer.getExcursionsFrequency();
		int days = offer.getNumberOfDays();
	}
}