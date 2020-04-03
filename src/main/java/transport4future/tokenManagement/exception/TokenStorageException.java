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

package transport4future.tokenManagement.exception;

/**
 * The type Token storage exception.
 */
public class TokenStorageException extends Exception {
    private static final long serialVersionUID = 1L;
    /**
     * The Message.
     */
    String message;

    /**
     * Instantiates a new Token storage exception.
     *
     * @param message the message
     */
    public TokenStorageException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
