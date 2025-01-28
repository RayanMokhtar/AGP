/**
 * 
 */
package business.tools;

import java.util.LinkedList;
import java.util.List;

import business.Boat;
import business.Bus;
import business.Coordinates;
import business.Excursion;
import business.Hotel;
import business.Island;
import business.Offer;
import business.Place;
import business.Transport;
import business.Visit;
import persistence.HotelPersistence;
import persistence.IslandPersistence;
import persistence.PlacePersistence;

/**
 *
 */
public class OffersBuilder {
	private String placeType;
	private String keywords;
	private int minimumPrice;
	private int maximumPrice;
	private int numberOfDays;
	private int excursionsFrequency;
	private double maximumTransportDuration;
	
	
	public OffersBuilder(UserPreferences preferences) {
		this.placeType = preferences.getPlaceType();
		this.keywords = preferences.getKeywords();
		this.minimumPrice = preferences.getMinimumPrice();
		this.maximumPrice = preferences.getMaximumPrice();
		this.numberOfDays = preferences.getNumberOfDays();
		this.excursionsFrequency = preferences.getExcursionsFrequency();
		this.maximumTransportDuration = preferences.getMaximumTransportDuration();
	}
	
	public List<Offer> buildOffers() {
		List<Offer> offers = new LinkedList<Offer>();
		Offer offer;
		double offerPrice;
		
		initialize(placeType, keywords);
		
		// We build 3 offers
		for (int i = 0; i < 3; i++) {
			offer = buildOffer();
			offerPrice = offer.getPrice();
			
			if (offerPrice >= minimumPrice
				&& offerPrice <= maximumPrice) {
				
				offers.add(offer);
			}
		}
		
		return offers;
	}
	
	private void initialize(String placeType, String keywords) {
		Island islandR;
		List<Island> islands;
		List<Place> places;
		List<Hotel> hotels;
		Island bestIsland;
		
		// ISLANDS
		// ================
		// Saving Islands in the IslandsManager registry
		islands = IslandPersistence.getIslands();
		
		for (Island island : islands) {
			islandR = IslandsManager.getInstance(island.getId());
			islandR.setName(island.getName());
		}
		
		
		// PLACES
		// ================
		// Getting of places corresponding to the client's criteria
		switch (placeType) {
			case "activity":
				places = PlacePersistence.getActivityPlaces(keywords);
				break;
			
			case "historic":
				places = PlacePersistence.getHistoricPlaces(keywords);
				break;
			
			default:
				places = PlacePersistence.getPlaces(keywords);
				break;
		}
		
		// Saving Places in the PlacesManager registry
		for (Place place : places) {
			PlacesManager.register(place);
		}
		
		
		// HOTELS
		// ================
		// Getting of island with the highest score
		// (with the most places that the customer appreciates)
		bestIsland = IslandsManager.getIslandWithBestScore();
		
		// Getting of hotels in this island
		hotels = HotelPersistence.getHotelsFromIsland(bestIsland.getId());
		
		// Saving Hotels in the HotelsManager registry
		for (Hotel hotel : hotels) {
			HotelsManager.register(hotel);
		}
		
		// Saving the order of places (for the score)
		PlacesManager.setPlacesInOrder(places);
	}
	
	private Offer buildOffer() {
		List<Place> places = PlacesManager.getPlacesInOrder();
		Hotel hotel;
		Hotel hotelR;
		
		// Reset the score of all hotels
		HotelsManager.resetHotelsScore();
		
		// Search for the closest hotel to places to visit on the island
		for (Place place : places) {
			hotelR = HotelsManager.getNearestHotelFrom(place);
			hotelR.incrementScore();
		}
		
		hotel = HotelsManager.getHotelWithBestScore();
		HotelsManager.journalizeNearestHotel(hotel);
		
		// Build a new offer
		Offer offer = new Offer(hotel, excursionsFrequency, numberOfDays);
		
		Island hotelIsland = hotel.getIsland();
		Island currentIsland = hotel.getIsland();
		Island placeIsland;
		
		double hotelPricePerDay = hotel.getPricePerDay();
		double maximumCostPerDay = (maximumPrice * excursionsFrequency) / numberOfDays;
		@SuppressWarnings("unused")
		double maximumCostForThisDay;
		double transportDuration;
		double totalDuration;
		double totalCost;
		double moneyNotSpentLastDay = 0.0;
		
		boolean isOnTheSameIsland;
		
		Coordinates hotelCoordinates = hotel.getCoordinates();
		Coordinates currentCoordinates = hotel.getCoordinates();
		Coordinates placeCoordinates;
		
		Excursion excursion;
		Transport transport;
		Visit visit;
		Place place;
		
		
		// This list avoids to get several times the same place
		List<Place> alreadySortedPlaces = new LinkedList<Place>();
		
		// For each day
		for (int i = 1; i <= numberOfDays; i++) {
			maximumCostForThisDay = maximumCostPerDay + moneyNotSpentLastDay;
			
			if (i % excursionsFrequency != 0) {
				// Nothing this day
				continue;
			}
			
			excursion = new Excursion();
			totalCost = 0.0;
			totalDuration = 0.0;
			
			// For each places
			for (int j = 0; j < places.size(); j++) {
				place = places.get(j);
				
				// Checking if we do not exceed the budget for the day
				// We also check that all visits do not exceed 6 hours in the day
				if ((totalCost > maximumCostPerDay - hotelPricePerDay) ||
					(totalDuration > 6)) {
					
					break;
				}
				
				// Do not go back to places already visited
				if (alreadySortedPlaces.contains(place)) {
					continue;
				}
				
				// Calculation of distances
				placeCoordinates = place.getCoordinates();
				double distance = placeCoordinates.getDistanceFrom(currentCoordinates);
				
				// We determine which transport to use to access to the place
				placeIsland = place.getIsland();
				isOnTheSameIsland = placeIsland.equals(currentIsland);
				
				if (isOnTheSameIsland) {
					transport = new Bus(distance);
				} else {
					transport = new Boat(distance);
				}
				
				// Calculation of travel duration
				transportDuration = transport.getDurationForThisTravel();
				
				// We check if we are below the limits set by the user
				if (transportDuration <= maximumTransportDuration) {
					// Adding a new place (with its transport) to the excursion
					visit = new Visit(transport, place);
					excursion.addVisit(visit);
					
					// Calculation of the excursion cost and duration
					totalCost += transport.getPriceForThisTravel()
							+ place.getEntrancePrice();
					totalDuration += transportDuration
							+ place.getVisitDuration();
					
					// Adding the place in the history
					alreadySortedPlaces.add(place);
					
					// We return at the beginning of the loop
					j = 0;
					
					currentIsland = placeIsland;
					currentCoordinates = placeCoordinates;
				}
			}
			// END for each places
			
			// If the excursion is not empty, we adding the transport to return to the hotel
			if (totalCost > 0) {
				double distance = hotelCoordinates.getDistanceFrom(currentCoordinates);
				isOnTheSameIsland = hotelIsland.equals(currentIsland);
				
				if (isOnTheSameIsland) {
					transport = new Bus(distance);
				} else {
					transport = new Boat(distance);
				}
				
				excursion.setTransportReturnToHotel(transport);
			}
			
			// We adding the excursion to the offer
			offer.addExcursion(excursion);
			
			// We keep unspent money for the next day
			moneyNotSpentLastDay += maximumCostPerDay - totalCost - hotelPricePerDay;
		}
		
		return offer;
	}
}
