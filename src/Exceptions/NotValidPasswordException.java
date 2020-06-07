package Exceptions;

public class NotValidPasswordException extends Exception {

    public NotValidPasswordException() {
    }

    public NotValidPasswordException(String message) {
        super(message);
    }
}
