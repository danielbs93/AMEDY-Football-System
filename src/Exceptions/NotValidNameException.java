package Exceptions;

public class NotValidNameException extends Exception {

    public NotValidNameException() {
    }

    public NotValidNameException(String message) {
        super(message);
    }
}
