package by.tms.diplomrestc51.exception;

public class InvalidException extends RuntimeException {
    public InvalidException(String message) {
        super(message);
    }

    public InvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidException(Throwable cause) {
        super(cause);
    }

    public InvalidException() {
        super();
    }
}
