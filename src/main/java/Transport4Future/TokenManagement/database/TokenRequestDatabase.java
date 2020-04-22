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

package Transport4Future.TokenManagement.database;

import Transport4Future.TokenManagement.config.Constants;
import Transport4Future.TokenManagement.exception.TokenManagementException;
import Transport4Future.TokenManagement.model.Token;
import Transport4Future.TokenManagement.model.TokenRequest;
import Transport4Future.TokenManagement.model.skeleton.Database;
import Transport4Future.TokenManagement.service.FileManager;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;

/**
 * The type Token request database.
 */
public class TokenRequestDatabase extends Database<HashMap<String, TokenRequest>, TokenRequest> {
    /**
     * The constant database.
     */
    protected static TokenRequestDatabase database;

    private TokenRequestDatabase() {
        super();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static TokenRequestDatabase getInstance() {
        if (database == null) {
            database = new TokenRequestDatabase();
        }
        return database;
    }

    @Override
    public void add(TokenRequest tokenRequest) throws TokenManagementException {
        this.reload();
        if (!this.inMemoryDb.containsKey(tokenRequest.getHex())) {
            this.inMemoryDb.put(tokenRequest.getHex(), tokenRequest);
        }
    }

    @Override
    protected void save() throws TokenManagementException {
        FileManager fileManager = new FileManager();
        try {
            fileManager.writeObjectToJsonFile(Constants.TOKEN_REQUEST_STORAGE_FILE, this.inMemoryDb);
        } catch (Exception e) {
            throw new TokenManagementException("Error: Unable to save a new token in the internal licenses store");
        }
    }

    @Override
    public TokenRequest find(String tokenRequestToFind) throws TokenManagementException {
        return this.inMemoryDb.get(tokenRequestToFind);
    }

    @Override
    protected void reload() throws TokenManagementException {
        FileManager fileManager = new FileManager();
        try {
            this.inMemoryDb = fileManager.readJsonFile(Constants.TOKEN_REQUEST_STORAGE_FILE, new TypeReference<HashMap<String, TokenRequest>>() {
            });
        } catch (Exception e) {
            throw new TokenManagementException("Error: unable to recover Token Requests Store.");
        }
    }

    /**
     * Is request registered.
     *
     * @param token the token
     * @throws TokenManagementException the token management exception
     */
    public void isRequestRegistered(Token token) throws TokenManagementException {
        this.reload();
        if (this.inMemoryDb == null || !this.inMemoryDb.containsKey(token.getDevice())) {
            throw new TokenManagementException("Error: Token Request Not Previously Registered");
        }
    }

}
