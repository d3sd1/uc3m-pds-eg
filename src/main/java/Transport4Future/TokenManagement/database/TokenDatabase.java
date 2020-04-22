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
import Transport4Future.TokenManagement.model.skeleton.Database;
import Transport4Future.TokenManagement.service.FileManager;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TokenDatabase implements Database {

    private static TokenDatabase tokenDatabase;
    private List<Token> tokensList;

    private TokenDatabase() {

    }

    public static TokenDatabase getInstance() {
        if (tokenDatabase == null) {
            tokenDatabase = new TokenDatabase();
            FileManager fileManager = new FileManager();
            try {
                fileManager.createPathRecursive(Constants.STORAGE_PATH);
                fileManager.createJsonFileIfNotExists(Constants.TOKEN_STORAGE_FILE, new TypeReference<List<Token>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tokenDatabase;
    }

    private void load() {
        try {
            FileManager fileManager = new FileManager();
            this.tokensList = fileManager.readJsonFile(Constants.TOKEN_STORAGE_FILE, new TypeReference<List<Token>>() {
            });
        } catch (Exception ex) {
            this.tokensList = new ArrayList<Token>();
        }
    }

    public void add(Token newToken) throws TokenManagementException {
        this.load();
        if (find(newToken.getTokenValue()) == null) {
            tokensList.add(newToken);
            this.save();
        }
    }

    private void save() throws TokenManagementException {
        try {
            FileManager fileManager = new FileManager();
            fileManager.writeObjectToJsonFile(Constants.TOKEN_STORAGE_FILE, this.tokensList);
        } catch (IOException e) {
            e.printStackTrace();
            throw new TokenManagementException("Error: Unable to save a new token in the internal licenses store");
        }
    }

    public Token find(String tokenToFind) {
        Token result = null;
        this.load();
        System.out.println("TO FIND: " + tokenToFind);
        System.out.println("TOKENS " + this.tokensList);
        for (Token token : this.tokensList) {
            if (token.getTokenValue().equals(tokenToFind)) {
                result = token;
            }
        }
        return result;
    }
}
