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

package Transport4Future.TokenManagement.config;

/**
 * Constants that replace environment files, which are currently not supported on P4.
 * This constants prevents ghosting and promote clear code and reusability.
 */
public class Constants {
    /**
     * The path to store all the .json files related to databases.
     */
    public static final String STORAGE_PATH = System.getProperty("user.dir") + "/database";
    /**
     * The absolute path to TokenRequest database.
     */
    public static final String TOKEN_REQUEST_STORAGE_FILE = STORAGE_PATH + "/tokenRequest.json";
    /**
     * The absolute path to Token database.
     */
    public static final String TOKEN_STORAGE_FILE = STORAGE_PATH + "/token.json";
}
