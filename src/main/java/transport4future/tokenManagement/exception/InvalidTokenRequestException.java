package transport4future.tokenManagement.exception;

public class InvalidTokenRequestException extends Exception {
    private static final long serialVersionUID = 1L;
    String message;

    public InvalidTokenRequestException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
