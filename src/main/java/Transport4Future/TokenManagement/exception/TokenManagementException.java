/*
 * Copyright (c) 2020.
 * Content created by:
 * - Andrei García Cuadra
 * - Miguel Hernández Cassel
 *
 * For the module PDS, on university Carlos III de Madrid.
 * Do not share, review nor edit any content without implicitly asking permission to it's owners, as you can contact by this email:
 * andreigarciacuadra@gmail.com
 *
 * All rights reserved.
 */

package Transport4Future.TokenManagement.exception;

/**
 * The type Token management exception.
 */
public class TokenManagementException extends Exception {
    private static final long serialVersionUID = 1L;
    /**
     * The Message.
     */
    String message;

    /**
     * Instantiates a new Token management exception.
     *
     * @param message the message
     */
    public TokenManagementException(String message) {

        this.message = message;
    }

    /**
     *
     * @return
     */
    public String getMessage() {

        return this.message;
    }
}
