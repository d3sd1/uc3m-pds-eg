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
import Transport4Future.TokenManagement.controller.TokenController;
import Transport4Future.TokenManagement.exception.TokenManagementException;
import Transport4Future.TokenManagement.model.Token;
import Transport4Future.TokenManagement.model.TokenRequest;
import Transport4Future.TokenManagement.model.skeleton.Database;
import Transport4Future.TokenManagement.service.FileManager;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * The type Token request database.
 */
public class TokenRequestDatabase extends Database<HashMap<String, TokenRequest>, TokenRequest> {
    /**
     * The constant database.
     */
    protected static TokenRequestDatabase database;
    /**
     * The In memory db.
     */
    protected static HashMap<String, TokenRequest> inMemoryDb;

    private TokenRequestDatabase() {
        super();
        try {
            FileManager fileManager = new FileManager();
            fileManager.createJsonFileIfNotExists(Constants.TOKEN_REQUEST_STORAGE_FILE, new HashMap<>());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        if (!inMemoryDb.containsKey(tokenRequest.getHex())) {
            inMemoryDb.put(tokenRequest.getHex(), tokenRequest);
            this.save();
        }
    }

    @Override
    protected void save() throws TokenManagementException {
        FileManager fileManager = new FileManager();
        try {
            fileManager.writeObjectToJsonFile(Constants.TOKEN_REQUEST_STORAGE_FILE, inMemoryDb);
        } catch (Exception e) {
            e.printStackTrace();
            throw new TokenManagementException("Error: Unable to save a new token in the internal licenses store");
        }
    }

    @Override
    public TokenRequest find(String tokenRequestToFind) throws TokenManagementException {
        return inMemoryDb.get(tokenRequestToFind);
    }

    @Override
    protected void reload() throws TokenManagementException {
        FileManager fileManager = new FileManager();
        try {
            inMemoryDb = fileManager.readJsonFile(Constants.TOKEN_REQUEST_STORAGE_FILE, new TypeToken<HashMap<String, TokenRequest>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
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
        if (inMemoryDb == null || !inMemoryDb.containsKey(token.getDevice())) {
            throw new TokenManagementException("Error: Token Request Not Previously Registered");
        }
    }

    @Override
    public TokenController clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

}
