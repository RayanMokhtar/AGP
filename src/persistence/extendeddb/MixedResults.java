/**
 * 
 */
package persistence.extendeddb;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * MixedResults class
 * 
 * Used to store multiple mixed results.
 */
public class MixedResults implements Iterable<MixedResult> {
	private List<MixedResult> tuples;
	private List<Integer> scoreList;
	private int currentIndex;
	
	/**
	 * MixedResults constructor
	 */
	public MixedResults() {
		this.tuples = new LinkedList<MixedResult>();
		this.currentIndex = 0;
	}
	
	/**
	 * addTuple
	 * 
	 * Stores a MixedResult into this structure.
	 * 
	 * @param tuple A MixedResult instance. 
	 */
	public void addTuple(MixedResult tuple) {
		tuples.add(tuple);
	}
	
	/**
	 * get
	 * 
	 * Retrieves a MixedResult at the specified position.
	 * 
	 * @param index The index number.
	 * @return MixedResult
	 */
	public MixedResult get(int index) {
		return tuples.get(index);
	}
	
	public void init() {
		this.currentIndex = 0;
	}
	
	public int size() {
		return tuples.size();
	}
	
	public boolean hasNext() {
		return currentIndex < size();
	}
	
	public MixedResult next() {
		if (hasNext()) {
			return get(currentIndex++);
		} else {
			return null;
		}
	}

	@Override
	public Iterator<MixedResult> iterator() {
		return tuples.iterator();
	}

	public List<Integer> getScoreList() {
		return scoreList;
	}

	public void setScoreList(List<Integer> scoreList) {
		this.scoreList = scoreList;
	}
}
