package transport4future.tokenManagement.service;

import transport4future.tokenManagement.exception.TokenStorageException;
import transport4future.tokenManagement.model.Token;
import transport4future.tokenManagement.model.implementation.StorageInterface;

import java.util.ArrayList;
import java.util.List;

public class TokenStorageInterface implements StorageInterface<Token> {
    private static List<Token> tokenGenerationStorage = new ArrayList<>();
    @Override
    public void add(Token obj) throws TokenStorageException {
        // throw TokenStorageException si no se pudo almacenar
        try {
            if(!this.tokenGenerationStorage.contains(obj)) {
                this.tokenGenerationStorage.add(obj);
            }
        } catch(Exception e) {
            throw new TokenStorageException("El token no se pudo almacenar.");
        }
    }

    @Override
    public boolean has(Token obj) {
        return false;
    }

    @Override
    public void remove(Token obj) {

    }
}
