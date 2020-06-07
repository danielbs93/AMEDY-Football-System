package Exceptions;

/***
 * Exception throw if connection to Union Accounting System fail.
 */
public class FailConnectUnionAccountSystemException extends Exception {

    public FailConnectUnionAccountSystemException() {
    }

    public FailConnectUnionAccountSystemException(String message) {
        super(message);
    }
}
