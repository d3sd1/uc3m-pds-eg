package transport4future.tokenManagement.exception;

public class TokenEncodingException extends Exception {
    private static final long serialVersionUID = 1L;
    String message;

    public TokenEncodingException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
