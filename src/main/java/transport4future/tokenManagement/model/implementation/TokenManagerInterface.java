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

package transport4future.tokenManagement.model.implementation;

import transport4future.tokenManagement.exception.LMException;

/**
 * The interface Token manager interface.
 */
public interface TokenManagerInterface {
    /**
     * Verify token boolean.
     *
     * @param token the token
     * @return the boolean
     * @throws LMException the lm exception
     */
    boolean VerifyToken(String token) throws LMException;
}
