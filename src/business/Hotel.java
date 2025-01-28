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
	private int score;
	
	public Hotel(int id) {
		this.id = id;
		this.name = null;
		this.pricePerDay = 0.0;
		this.coordinates = null;
		this.island = null;
		this.score = 0;
	}
	
	public Hotel(int id,
				 String name,
				 double pricePerDay,
				 Coordinates coordinates,
				 Island island) {
		
		this.id = id;
		this.name = name;
		this.pricePerDay = pricePerDay;
		this.coordinates = coordinates;
		this.island = island;
		this.score = 0;
	}
	
	public void incrementScore() {
		score++;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
