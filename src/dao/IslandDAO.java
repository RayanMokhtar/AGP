package dao;

import business.Island;

public interface IslandDAO {
	Island findById(int id);
	    
	Island findByName(String name);
	
}
