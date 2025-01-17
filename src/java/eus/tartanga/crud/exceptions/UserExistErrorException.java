package eus.tartanga.crud.exceptions;

/**
 * Exception thrown when a user already exists in the system.
 *
 * This class extends the Exception class to provide specific error handling for
 * situations where an attempt is made to register a user that is already
 * present in the system, indicating a conflict in user creation.
 *
 * @author Elbire
 */
public class UserExistErrorException extends Exception {

    /**
     * Creates a new instance of UserExistErrorException without a detail
     * message.
     */
    public UserExistErrorException() {
    }

    /**
     * Constructs an instance of UserExistErrorException with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public UserExistErrorException(String msg) {
        super(msg);
    }
}
