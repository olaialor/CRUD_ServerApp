package eus.tartanga.crud.exceptions;

/**
 * Exception thrown when an error occurs during the encryption process.
 * 
 * This exception can be instantiated without a message or with a specific 
 * detail message describing the cause of the encryption failure.
 * 
 * @author ElbireTM
 */
public class EncryptException extends Exception {

    /**
     * Creates a new instance of <code>EncryptException</code> without a detail message.
     */
    public EncryptException() {
    }

    /**
     * Constructs an instance of <code>EncryptException</code> with the 
     * specified detail message.
     *
     * @param msg the detail message describing the encryption error.
     */
    public EncryptException(String msg) {
        super(msg);
    }
}
