package eus.tartanga.crud.exceptions;

/**
 * Exception thrown when a sign-in error occurs.
 *
 * This class extends the Exception class to provide specific error handling for
 * situations where a user fails to sign in, typically due to incorrect
 * credentials or other related issues.
 *
 * @author Elbire
 */
public class SignInErrorException extends Exception {

    /**
     * Creates a new instance of SignInErrorException without a detail message.
     */
    public SignInErrorException() {
    }

    /**
     * Constructs an instance of SignInErrorException with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public SignInErrorException(String msg) {
        super(msg);
    }
}
