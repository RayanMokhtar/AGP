package persistence.extendeddb.lucene;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * TextualResults class
 * 
 * Used to store multiple textual results.
 */
public class TextualResults implements Iterable<TextualResult> {
    private List<TextualResult> textualResults;
    private int currentIndex;

    /**
     * TextualResult constructor
     */
    public TextualResults() {
        this.textualResults = new LinkedList<TextualResult>();
        this.currentIndex = 0;
    }

    /**
     * add
     * 
     * Stores a TextualResult into this structure.
     * 
     * @param result A TextualResult instance.
     */
    public void add(TextualResult result) {
        textualResults.add(result);
    }

    /**
     * get
     * 
     * Retrieves a TextualResult at the specified position.
     * 
     * @param index The index number.
     * @return TextualResult
     */
    public TextualResult get(int index) {
        return textualResults.get(index);
    }

    public void init() {
        this.currentIndex = 0;
    }

    public int size() {
        return textualResults.size();
    }

    public boolean hasNext() {
        return currentIndex < size();
    }

    public TextualResult next() {
        if (hasNext()) {
            return get(currentIndex++);
        } else {
            return null;
        }
    }

    @Override
    public Iterator<TextualResult> iterator() {
        return textualResults.iterator();
    }
}