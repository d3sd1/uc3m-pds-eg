package transport4future.tokenManagement.exception;

public class InvalidTokenException extends Exception {
    private static final long serialVersionUID = 1L;
    String message;

    public InvalidTokenException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
