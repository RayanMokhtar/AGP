/**
 * 
 */
package business;

/**
 *
 */
public class Hotel {
	private int id;
	private String name;
	private double pricePerDay;
	private Coordinates coordinates;
	private Island island;
	private int stars ; 
	
	
	public Hotel(int id) {
		this.id = id;
		this.name = null;
		this.pricePerDay = 0.0;
		this.coordinates = null;
		this.island = null;
		this.stars = 3;
	}
	
	public Hotel(int id,
				 String name,
				 double pricePerDay,
				 Coordinates coordinates,
				 Island island , int stars ) {
		
		this.id = id;
		this.name = name;
		this.pricePerDay = pricePerDay;
		this.coordinates = coordinates;
		this.island = island;
		this.stars = stars;
	}
	


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public Island getIsland() {
		return island;
	}

	public void setIsland(Island island) {
		this.island = island;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}
}

