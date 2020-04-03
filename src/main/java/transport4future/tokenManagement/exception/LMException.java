package transport4future.tokenManagement.exception;

/**
 * The type Lm exception.
 */
public class LMException extends Exception {
    private static final long serialVersionUID = 1L;
    /**
     * The Message.
     */
    String message;

    /**
     * Instantiates a new Lm exception.
     *
     * @param message the message
     */
    public LMException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
