package eus.tartanga.crud.exceptions;

/**
 * Exception thrown when an error occurs during the deletion process.
 * 
 * This exception can be instantiated without a message or with a specific 
 * detail message describing the cause of the error.
 * 
 * @author Olaia
 */
public class DeleteException extends Exception {

    /**
     * Creates a new instance of <code>DeleteException</code> without a detail message.
     */
    public DeleteException() {
    }

    /**
     * Constructs an instance of <code>DeleteException</code> with the specified 
     * detail message.
     *
     * @param msg the detail message describing the error.
     */
    public DeleteException(String msg) {
        super(msg);
    }
}
