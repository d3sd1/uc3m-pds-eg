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
import Transport4Future.TokenManagement.model.skeleton.Database;
import Transport4Future.TokenManagement.service.FileManager;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Token database.
 */
public class TokenDatabase extends Database<List<Token>, Token> {
    /**
     * The constant database.
     */
    protected static TokenDatabase database;

    private TokenDatabase() {
        super();
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
        this.reload();
        if (find(newToken.getTokenValue()) == null) {
            this.inMemoryDb.add(newToken);
            this.save();
        }
    }

    @Override
    protected void save() throws TokenManagementException {
        try {
            FileManager fileManager = new FileManager();
            fileManager.writeObjectToJsonFile(Constants.TOKEN_STORAGE_FILE, this.inMemoryDb);
        } catch (IOException e) {
            throw new TokenManagementException("Error: Unable to save a new token in the internal licenses store");
        }
    }

    @Override
    public Token find(String tokenToFind) {
        Token result = null;
        this.reload();
        for (Token token : this.inMemoryDb) {
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
            this.inMemoryDb = fileManager.readJsonFile(Constants.TOKEN_STORAGE_FILE, new TypeReference<List<Token>>() {
            });
        } catch (Exception ex) {
            this.inMemoryDb = new ArrayList<Token>();
        }
    }

    @Override
    public TokenController clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
