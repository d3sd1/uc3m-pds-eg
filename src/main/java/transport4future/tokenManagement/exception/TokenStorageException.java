package transport4future.tokenManagement.exception;

/**
 * The type Token storage exception.
 */
public class TokenStorageException extends Exception {
    private static final long serialVersionUID = 1L;
    /**
     * The Message.
     */
    String message;

    /**
     * Instantiates a new Token storage exception.
     *
     * @param message the message
     */
    public TokenStorageException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
