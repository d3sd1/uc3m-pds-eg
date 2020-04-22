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

package Transport4Future.TokenManagement.model.skeleton;

import Transport4Future.TokenManagement.config.Constants;
import Transport4Future.TokenManagement.exception.TokenManagementException;
import Transport4Future.TokenManagement.service.FileManager;

import java.io.IOException;
import java.util.HashMap;

public abstract class Database<T, L> {
    protected T inMemoryDb;

    protected Database() {
        FileManager fileManager = new FileManager();
        try {
            fileManager.createPathRecursive(Constants.STORAGE_PATH);
            fileManager.createJsonFileIfNotExists(Constants.TOKEN_REQUEST_STORAGE_FILE, new HashMap<>());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void add(L obj) throws TokenManagementException;

    public abstract <K> K find(String stringToFind) throws TokenManagementException;

    protected abstract void save() throws TokenManagementException;

    protected abstract void reload() throws TokenManagementException;
}
