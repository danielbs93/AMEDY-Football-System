package Exceptions;

/***
 * Exception for user insert mismatch password.
 */
public class PasswordNotMatchException extends  Exception {

    public PasswordNotMatchException() {
    }

    public PasswordNotMatchException(String error) {
        super(error);
    }
}
