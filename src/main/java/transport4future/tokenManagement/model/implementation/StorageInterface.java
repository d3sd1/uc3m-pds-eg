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
