/**
 * 
 */
package simulation;

import java.util.List;

import business.Excursion;
import business.Offer;
import business.Site;
import business.Transport;
import business.Visit;

/**
 *
 */
public class Tools {
	
	private Tools() {
		
	}
	
	public static void displayOffers(List<Offer> offers) {
		if (offers.size() == 0) {
			System.out.println("There are no offers matching your search criteria.");
			return;
		}
		
		int offerNum = 1;
		
		for (Offer offer : offers) {
			displayOffer(offer, offerNum);
			offerNum++;
		}
	}
	
	public static void displayOffer(Offer offer, int offerNum) {
		Excursion excursionOfDay;
		
		System.out.println(
				"\n\n\n####################################################\n"
				+ "|| Offer n� " + offerNum + "		|| price = " + (float) offer.getPrice() + "	  ||\n"
				+ "####################################################"
		);
		
		System.out.println("Hotel : " + offer.getHotel().getName() + "\n");
		
		for (int day = 1; day <= offer.getNumberOfDays(); day++) {
			System.out.println("DAY " + day + " ==>");
			
			if (!offer.hasExcursionOnDay(day)) {
				System.out.println(
						"	|Break day, go to the beach !\n"
						+ "	|-----------------------------------------------------------"
				);
				
				continue;
			}
			
			excursionOfDay = offer.getExcursionOfDay(day);
			
			if (!excursionOfDay.hasVisits()) {
				System.out.println(
						"	|Break day, go to the beach ! (due to your budget or preferences)\n"
						+ "	|-----------------------------------------------------------"
				);
				
				continue;
			}
			
			System.out.println(
					"Excursion :	Time : " + excursionOfDay.getDuration()
					+ "	Price : " + excursionOfDay.getPrice()
			);
			
			System.out.println("	/ Start of the excursion from your hotel");
			
			for (Visit currentVisit : excursionOfDay.getPlacesToVisit()) {
				Site currentPlace = currentVisit.getPlaceToVisit();
				Transport currentTransport = currentVisit.getTransportDriveway();
				
				System.out.println(
						"	|----Visit on " + currentPlace.getName()
						+ " for " + currentPlace.getVisitDuration()
						+ " hours with a " + currentTransport
				);
				
				/*
				System.out.println(
						"	|_____With a " + currentTransport
						+ " [Transport cost] " + currentTransport.getPriceForThisTravel()
						+ " [Transport duration] " + currentTransport.getDurationForThisTravel()
				);
				*/
			}
			
			System.out.println(
					"	| Return to hotel\n"
					+ "	\\----------------------------------------------------------\n"
			);
		}
	}
}
