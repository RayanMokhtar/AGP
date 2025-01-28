package business.exceptions;

public class InsufficientBudgetException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientBudgetException(String message) {
        super(message);
    }
}