/**
 * 
 */
package business;

/**
 *
 */
public class Island {
	private int id;
	private String name;
	private int score;
	
	public Island(int id) {
		this.id = id;
		this.name = null;
		this.score = 0;
	}
	
	public Island(int id, String name) {
		this.id = id;
		this.name = name;
		this.score = 0;
	}
	
	public boolean equals(Island island) {
		return (id == island.getId());
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
