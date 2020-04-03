package transport4future.tokenManagement.exception;

public class TokenManagementException extends Exception {
    private static final long serialVersionUID = 1L;
    String message;

    public TokenManagementException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
