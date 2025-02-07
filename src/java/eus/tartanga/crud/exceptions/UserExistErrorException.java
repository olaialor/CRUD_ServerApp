package eus.tartanga.crud.exceptions;

/**
 * Exception thrown when there is an error related to an existing user.
 * This class extends the Exception class to provide more specific exception handling
 * for situations where a user already exists.
 * 
 * @author Olaia
 */
public class UserExistErrorException extends Exception {

    /**
     * Creates a new instance of UserExistErrorException without a detail message.
     */
    public UserExistErrorException() {
    }

    /**
     * Constructs a new instance of UserExistErrorException with the specified detail message.
     *
     * @param msg the detail message.
     */
    public UserExistErrorException(String msg) {
        super(msg);
    }
}
