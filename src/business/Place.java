/**
 * 
 */
package business;

/**
 *
 */
public class Place {
	private int id;
	private String name;
	private boolean isHistoric;
	private int visitDuration;
	private double entrancePrice;
	private String description;
	private Coordinates coordinates;
	private Island island;
	private int score;
	
	public Place(int id) {
		this.id = id;
		this.name = null;
		this.isHistoric = false;
		this.visitDuration = 0;
		this.entrancePrice = 0.0;
		this.description = null;
		this.coordinates = null;
		this.island = null;
		this.score = 0;
	}
	
	public Place(int id,
				 String name,
				 boolean isHistoric,
				 int visitDuration,
				 double entrancePrice,
				 String description,
				 Coordinates coordinates,
				 Island island,
				 int score) {
		
		this.id = id;
		this.name = name;
		this.isHistoric = isHistoric;
		this.visitDuration = visitDuration;
		this.entrancePrice = entrancePrice;
		this.description = description;
		this.coordinates = coordinates;
		this.island = island;
		this.score = score;
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

	public boolean isHistoric() {
		return isHistoric;
	}

	public void setHistoric(boolean isHistoric) {
		this.isHistoric = isHistoric;
	}

	public int getVisitDuration() {
		return visitDuration;
	}

	public void setVisitDuration(int visitDuration) {
		this.visitDuration = visitDuration;
	}

	public double getEntrancePrice() {
		return entrancePrice;
	}

	public void setEntrancePrice(double entrancePrice) {
		this.entrancePrice = entrancePrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
