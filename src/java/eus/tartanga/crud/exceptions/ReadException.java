package eus.tartanga.crud.exceptions;

/**
 * Exception thrown when there is an error reading data.
 * This class extends the Exception class to provide more specific exception handling
 * for read-related operations.
 * 
 * @author Elbire
 */
public class ReadException extends Exception {

    /**
     * Creates a new instance of ReadException without a detail message.
     */
    public ReadException() {
    }

    /**
     * Constructs a new instance of ReadException with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ReadException(String msg) {
        super(msg);
    }
}
