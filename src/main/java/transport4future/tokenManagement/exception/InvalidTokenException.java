package transport4future.tokenManagement.exception;

/**
 * The type Invalid token exception.
 */
public class InvalidTokenException extends Exception {
    private static final long serialVersionUID = 1L;
    /**
     * The Message.
     */
    String message;

    /**
     * Instantiates a new Invalid token exception.
     *
     * @param message the message
     */
    public InvalidTokenException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
