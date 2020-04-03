package transport4future.tokenManagement.exception;

public class LMException extends Exception {
    private static final long serialVersionUID = 1L;
    String message;

    public LMException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
