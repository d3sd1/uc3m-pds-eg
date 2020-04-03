package transport4future.tokenManagement.exception;

/**
 * The type Token encoding exception.
 */
public class TokenEncodingException extends Exception {
    private static final long serialVersionUID = 1L;
    /**
     * The Message.
     */
    String message;

    /**
     * Instantiates a new Token encoding exception.
     *
     * @param message the message
     */
    public TokenEncodingException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
