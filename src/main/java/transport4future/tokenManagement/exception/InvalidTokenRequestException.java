package transport4future.tokenManagement.exception;

/**
 * The type Invalid token request exception.
 */
public class InvalidTokenRequestException extends Exception {
    private static final long serialVersionUID = 1L;
    /**
     * The Message.
     */
    String message;

    /**
     * Instantiates a new Invalid token request exception.
     *
     * @param message the message
     */
    public InvalidTokenRequestException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
