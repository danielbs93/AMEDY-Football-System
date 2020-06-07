package Exceptions;

/**
 * @author Daniel Ben Simon
 * Exception returned for empty lists if the user does'nt has any assignments
 */
public class ListIsEmptyException extends Exception {

    public ListIsEmptyException(String message) {
        super(message);
    }
}
