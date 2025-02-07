package eus.tartanga.crud.exceptions;

/**
 * Exception thrown when an error occurs during the creation process.
 * 
 * This exception can be instantiated without a message or with a specific 
 * detail message describing the cause of the error.
 * 
 * @author Meylin
 */
public class CreateException extends Exception {

    /**
     * Creates a new instance of <code>CreateException</code> without a detail message.
     */
    public CreateException() {
    }

    /**
     * Constructs an instance of <code>CreateException</code> with the specified 
     * detail message.
     *
     * @param msg the detail message describing the error.
     */
    public CreateException(String msg) {
        super(msg);
    }
}
