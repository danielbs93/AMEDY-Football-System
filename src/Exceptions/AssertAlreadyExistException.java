package Exceptions;

/**
 * @author Michal Talmor
 * @throws Exception when the user try to add assert that already exist.
 * @examples - try to add player to team
 */
public class AssertAlreadyExistException extends Exception {

    public AssertAlreadyExistException() {
    }

    public AssertAlreadyExistException(String message) {
        super(message);
    }
}
