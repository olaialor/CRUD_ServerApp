package eus.tartanga.crud.exceptions;

/**
 * Exception thrown when there is an error during the sign-in process.
 * This class extends the Exception class to provide more specific exception handling
 * for sign-in related errors.
 * 
 * @author Irati
 */
public class SignInErrorException extends Exception {

    /**
     * Creates a new instance of SignInErrorException without a detail message.
     */
    public SignInErrorException() {
    }

    /**
     * Constructs a new instance of SignInErrorException with the specified detail message.
     *
     * @param msg the detail message.
     */
    public SignInErrorException(String msg) {
        super(msg);
    }
}
