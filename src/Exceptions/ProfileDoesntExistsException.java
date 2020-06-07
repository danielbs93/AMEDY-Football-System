package Exceptions;

/***
 * Insertion of not valid profile.
 */
public class ProfileDoesntExistsException extends Exception {

    public ProfileDoesntExistsException() {
    }

    public ProfileDoesntExistsException(String message) {
        super(message);
    }
}
