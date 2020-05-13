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
import Transport4Future.TokenManagement.controller.TokenManager;
import Transport4Future.TokenManagement.exception.TokenManagementException;
import Transport4Future.TokenManagement.model.Token;
import Transport4Future.TokenManagement.model.skeleton.Database;
import Transport4Future.TokenManagement.service.FileManager;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Token database.
 */
public class TokensStore extends Database<List<Token>, Token> {
    /**
     * The constant database.
     */
    protected static TokensStore database;
    /**
     * The In memory db.
     */
    protected static List<Token> inMemoryDb;

    /**
     *
     */
    private TokensStore() {
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
    public static TokensStore getInstance() {
        if (database == null) {
            database = new TokensStore();
        }
        return database;
    }

    /**
     *
     * @param newToken
     * @throws TokenManagementException
     */
    @Override
    public void add(Token newToken) throws TokenManagementException {
        if (this.find(newToken.getTokenValue()) == null) {
            inMemoryDb.add(newToken);
            this.save();
        }
    }

    /**
     *
     * @throws TokenManagementException
     */
    @Override
    protected void save() throws TokenManagementException {
        try {
            FileManager fileManager = new FileManager();
            if(inMemoryDb != null) {
                fileManager.writeObjectToJsonFile(Constants.TOKEN_STORAGE_FILE, inMemoryDb);
            }
        } catch (Exception e) {
            throw new TokenManagementException("Error: Unable to save a new token in the internal licenses store");
        }
    }

    /**
     *
     * @param tokenToFind
     * @return
     */
    @Override
    public Token find(String tokenToFind) {
        Token result = null;
        for (Token token : inMemoryDb) {
            if (token.getTokenValue().equals(tokenToFind)) {
                result = token;
            }
        }
        return result;
    }

    /**
     *
     */
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

    /**
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public TokenManager clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
