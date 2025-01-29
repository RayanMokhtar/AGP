/**
 * 
 */
package business;

/**
 *
 */
import business.tools.TypeSite;

public class Site {
    private int id;
    private String name;
    private double entryPrice;     // Prix d'entrée
    private double duration;  // Durée de la visite en heures
    private Coordinates coordinates;
    private Island island;
    private TypeSite type;
    private String description ; 
    
    public Site(int id, String name, double entryPrice, double duration, Coordinates coordinates, Island island, TypeSite type , String description) {
        this.id = id;
        this.name = name;
        this.entryPrice = entryPrice;
        this.duration = duration;
        this.coordinates = coordinates;
        this.island = island;
        this.type = type;
        this.description = description;
    }

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getEntryPrice() {
		return entryPrice;
	}

	public double getDuration() {
		return duration;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public Island getIsland() {
		return island;
	}

	public TypeSite getType() {
		return type;
	}
	
	public String getDescription() {
		return description ; 
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEntryPrice(double entryPrice) {
		this.entryPrice = entryPrice;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public void setIsland(Island island) {
		this.island = island;
	}

	public void setType(TypeSite type) {
		this.type = type;
	}
	
	public void setDescription(String Description) {
		this.description = Description ; 
	}

	@Override
	public String toString() {
		return "Site [id=" + id + ", name=" + name + ", entryPrice=" + entryPrice + ", duration=" + duration
				+ ", coordinates=" + coordinates + ", island=" + island + ", type=" + type + ", description="
				+ description + "]";
	}
}

    