package Exceptions;

/**
 * @author Michal Talmor
 * throw when try to connect profile to user that already has a same profile type
 */
public class ProfileTypeExistException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ProfileTypeExistException(String message) {
        super(message);
    }

    public ProfileTypeExistException() {

    }
}
