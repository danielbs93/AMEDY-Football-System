package Exceptions;

/***
 * Business Layer occur problem while tryingto add user.
 */
public class UserAddException extends Exception {

    public UserAddException() {
    }

    public UserAddException(String message) {
        super(message);
    }
}
