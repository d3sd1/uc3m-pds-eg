package transport4future.tokenManagement.exception;

/**
 * The type Token management exception.
 */
public class TokenManagementException extends Exception {
    private static final long serialVersionUID = 1L;
    /**
     * The Message.
     */
    String message;

    /**
     * Instantiates a new Token management exception.
     *
     * @param message the message
     */
    public TokenManagementException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
