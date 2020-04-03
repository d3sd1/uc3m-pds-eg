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

package transport4future.tokenManagement.service;

import transport4future.tokenManagement.exception.TokenEncodingException;
import transport4future.tokenManagement.exception.TokenStorageException;
import transport4future.tokenManagement.model.Token;
import transport4future.tokenManagement.model.implementation.StorageInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Token storage.
 */
public class TokenStorage implements StorageInterface<Token> {
    private static List<Token> tokenGenerationStorage = new ArrayList<>();
    private Crypt crypt = new Crypt();

    @Override
    public void add(Token obj) throws TokenStorageException {
        // throw TokenStorageException si no se pudo almacenar
        try {
            if (!tokenGenerationStorage.contains(obj)) {
                tokenGenerationStorage.add(obj);
            }
        } catch (Exception e) {
            throw new TokenStorageException("El token no se pudo almacenar.");
        }
    }

    @Override
    public boolean has(Token obj) {
        boolean exists = false;
        for (Token token : tokenGenerationStorage) {
            try {
                if (this.crypt.encode(token).equals(this.crypt.encode(obj))) {
                    exists = true;
                    break;
                }
            } catch (TokenEncodingException e) {
                e.printStackTrace();
            }
        }
        return exists;
    }

    @Override
    public void remove(Token obj) {
        tokenGenerationStorage.remove(obj);
    }
}
