package persistence.extendeddb;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * MixedResults class
 * 
 * Used to store multiple mixed results.
 */
public class CombinedResults implements Iterable<CombinedResult> {
    private List<CombinedResult> tuples;
    private int currentIndex;

    /**
     * MixedResults constructor
     */
    public CombinedResults() {
        this.tuples = new LinkedList<CombinedResult>();
        this.currentIndex = 0;
    }

    /**
     * addTuple
     * 
     * Stores a MixedResult into this structure.
     * 
     * @param tuple A MixedResult instance.
     */
    public void addTuple(CombinedResult tuple) {
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
    public CombinedResult get(int index) {
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

    public CombinedResult next() {
        if (hasNext()) {
            return get(currentIndex++);
        } else {
            return null;
        }
    }

    @Override
    public Iterator<CombinedResult> iterator() {
        return tuples.iterator();
    }

    public boolean isEmpty() {
        return tuples.isEmpty();
    }
}