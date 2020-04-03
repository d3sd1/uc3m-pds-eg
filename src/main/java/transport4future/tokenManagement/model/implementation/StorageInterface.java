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

import transport4future.tokenManagement.exception.TokenStorageException;

/**
 * The interface Storage interface.
 *
 * @param <T> the type parameter
 */
public interface StorageInterface<T> {
    /**
     * Add.
     *
     * @param obj the obj
     * @throws TokenStorageException the token storage exception
     */
    void add(T obj) throws TokenStorageException;

    /**
     * Has boolean.
     *
     * @param obj the obj
     * @return the boolean
     */
    boolean has(T obj);

    /**
     * Remove.
     *
     * @param obj the obj
     */
    void remove(T obj);
}
