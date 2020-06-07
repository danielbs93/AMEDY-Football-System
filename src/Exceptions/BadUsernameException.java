package Exceptions;

public class BadUsernameException extends Exception {

    public BadUsernameException() {
    }

    public BadUsernameException(String message) {
        super(message);
    }
}
