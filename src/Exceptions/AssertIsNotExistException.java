package Exceptions;
/**
 * @author Michal Talmor
 * @throws Exception when the user try to add assert that not exist.
 * @examples - try to remove player from team
 */
public class AssertIsNotExistException extends Exception {

    public AssertIsNotExistException() {
    }

    public AssertIsNotExistException(String message) {
        super(message);
    }
}
