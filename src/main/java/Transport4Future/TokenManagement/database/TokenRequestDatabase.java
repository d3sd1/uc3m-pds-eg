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

import java.io.IOException;
import java.util.HashMap;

public class TokenRequestDatabase implements Database {

    private static TokenRequestDatabase tokenDatabase;

    private TokenRequestDatabase() {

    }

    public static TokenRequestDatabase getInstance() {
        if (tokenDatabase == null) {
            tokenDatabase = new TokenRequestDatabase();
            FileManager fileManager = new FileManager();
            try {
                fileManager.createPathRecursive(Constants.STORAGE_PATH);
                fileManager.createJsonFileIfNotExists(Constants.TOKEN_REQUEST_STORAGE_FILE, new HashMap<>());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tokenDatabase;
    }

    public void add(TokenRequest tokenRequest, String hex) throws TokenManagementException {
        FileManager fileManager = new FileManager();
        HashMap<String, TokenRequest> clonedMap = this.getStore();
        if (!clonedMap.containsKey(hex)) {
            clonedMap.put(hex, tokenRequest);
        }

        try {
            fileManager.writeObjectToJsonFile(Constants.TOKEN_REQUEST_STORAGE_FILE, clonedMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new TokenManagementException("Error: Unable to save a new token in the internal licenses store");
        }
    }

    public void has(Token token) throws TokenManagementException {
        HashMap<String, TokenRequest> clonedMap = this.getStore();
        if (clonedMap == null || !clonedMap.containsKey(token.getDevice())) {
            throw new TokenManagementException("Error: Token Request Not Previously Registered");
        }
    }

    private HashMap<String, TokenRequest> getStore() throws TokenManagementException {
        FileManager fileManager = new FileManager();
        HashMap<String, TokenRequest> clonedMap;
        try {
            clonedMap = fileManager.readJsonFile(Constants.TOKEN_REQUEST_STORAGE_FILE, new TypeReference<HashMap<String, TokenRequest>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new TokenManagementException("Error: unable to recover Token Requests Store.");
        }
        return clonedMap;
    }
}
