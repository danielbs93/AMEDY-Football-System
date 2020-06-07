package Exceptions;

/**
 * @author Michal Talmor
 * try to close team with "CLOSE" or "CLOSE_PERMANENTLY" status
 * try to open team with "CLOSE_PERMANENTLY" status
 */
public class TeamStatusException extends Exception {

    public TeamStatusException(){}

    public TeamStatusException(String message){super(message);}


}
