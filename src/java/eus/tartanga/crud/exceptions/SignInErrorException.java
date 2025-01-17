/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.exceptions;

/**
 *
 * @author 2dam
 */
public class SignInErrorException extends Exception {

    /**
     * Creates a new instance of <code>SignInErrorException</code> without
     * detail message.
     */
    public SignInErrorException() {
    }

    /**
     * Constructs an instance of <code>SignInErrorException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SignInErrorException(String msg) {
        super(msg);
    }
}
