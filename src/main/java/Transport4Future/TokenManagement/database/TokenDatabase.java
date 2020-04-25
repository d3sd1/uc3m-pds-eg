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
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The type Token database.
 */
public class TokenDatabase extends Database<List<Token>, Token> {
    /**
     * The constant database.
     */
    protected static TokenDatabase database;
    /**
     * The In memory db.
     */
    protected static List<Token> inMemoryDb;

    private TokenDatabase() {
        super();
        try {
            FileManager fileManager = new FileManager();
            fileManager.createJsonFileIfNotExists(Constants.TOKEN_STORAGE_FILE, new ArrayList<>());
            this.reload();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static TokenDatabase getInstance() {
        if (database == null) {
            database = new TokenDatabase();
        }
        return database;
    }

    @Override
    public void add(Token newToken) throws TokenManagementException {
        if (this.find(newToken.getTokenValue()) == null) {
            inMemoryDb.add(newToken);
            this.save();
        }
    }

    @Override
    protected void save() throws TokenManagementException {
        try {
            FileManager fileManager = new FileManager();
            if(inMemoryDb != null) {
                fileManager.writeObjectToJsonFile(Constants.TOKEN_STORAGE_FILE, inMemoryDb);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new TokenManagementException("Error: Unable to save a new token in the internal licenses store");
        }
    }

    @Override
    public Token find(String tokenToFind) {
        Token result = null;
        for (Token token : inMemoryDb) {
            try {
                token.encodeValue();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            if (token.getTokenValue().equals(tokenToFind)) {
                result = token;
            }
        }
        return result;
    }

    @Override
    protected void reload() {
        FileManager fileManager = new FileManager();
        try {
            List<Token> tokens = fileManager.readJsonFile(Constants.TOKEN_STORAGE_FILE, new TypeToken<List<Token>>(){}.getType());
            if(tokens == null) {
                throw new JsonSyntaxException("No tokens previously stored");
            }
        } catch (Exception e) {
            inMemoryDb = new ArrayList<Token>();
        }
    }

    @Override
    public TokenController clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
