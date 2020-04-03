package transport4future.tokenManagement.model.implementation;

import transport4future.tokenManagement.exception.TokenStorageException;

public interface Storage<T> {
    void add(T obj) throws TokenStorageException;
    boolean has(T obj);
    void remove(T obj);
}
