package transport4future.tokenManagement.exception;

public class TokenStorageException extends Exception {
    private static final long serialVersionUID = 1L;
    String message;

    public TokenStorageException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
