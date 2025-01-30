package dao;

import java.util.List;

import business.Island;

public interface IslandDAO {
	Island findById(int id);
	    
	Island findByName(String name);

	List<Island> findByIsland(Island island);
	
}
