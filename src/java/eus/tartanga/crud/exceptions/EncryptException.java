/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.exceptions;

/**
 *
 * @author ElbireTM
 */
public class EncryptException extends Exception {

    /**
     * Creates a new instance of <code>EncryptException</code> without detail
     * message.
     */
    public EncryptException() {
    }

    /**
     * Constructs an instance of <code>EncryptException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EncryptException(String msg) {
        super(msg);
    }
}
