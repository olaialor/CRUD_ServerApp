package eus.tartanga.crud.exceptions;

/**
 * Exception thrown when there is an error during the update process.
 * This class extends the Exception class to provide more specific exception handling
 * for update-related errors.
 * 
 * @author Meylin
 */
public class UpdateException extends Exception {

    /**
     * Creates a new instance of UpdateException without a detail message.
     */
    public UpdateException() {
    }

    /**
     * Constructs a new instance of UpdateException with the specified detail message.
     *
     * @param msg the detail message.
     */
    public UpdateException(String msg) {
        super(msg);
    }
}
