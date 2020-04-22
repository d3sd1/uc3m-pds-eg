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

package Transport4Future.TokenManagement.controller;

import Transport4Future.TokenManagement.database.TokenDatabase;
import Transport4Future.TokenManagement.database.TokenRequestDatabase;
import Transport4Future.TokenManagement.exception.TokenManagementException;
import Transport4Future.TokenManagement.model.Token;
import Transport4Future.TokenManagement.model.TokenRequest;
import Transport4Future.TokenManagement.model.skeleton.TokenManager;
import Transport4Future.TokenManagement.service.FileManager;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;


/**
 * This class manages tokens, so it's purely a controller that handles actions on Tokens.
 */
public class TokenController implements TokenManager {
    private static TokenController instance;

    private TokenController() {
        super();
    }

    public static TokenController getInstance() {
        if (instance == null) {
            instance = new TokenController();
        }
        return instance;
    }

    /**
     * This class generates a TokenRequest, that will be use to
     *
     * @param inputFile File path to retrieve a valid RequestToken file. If not valid, see @throws.
     * @return tokenRequest HEX code, if request went success.
     * @throws TokenManagementException with specific message based on cases.
     * @throws TokenManagementException nested, from other project sides instead catching 'em.
     */
    public String generate(String inputFile) throws TokenManagementException {
        TokenRequest tokenRequest;
        byte[] encodedTokenRequest;
        String hex;
        FileManager fileManager = new FileManager();
        TokenRequestDatabase tokenRequestDatabase = TokenRequestDatabase.getInstance();

        try {
            tokenRequest = fileManager.readJsonFileWithConstraints(inputFile, TokenRequest.class);
        } catch (JsonMappingException e) {
            throw new TokenManagementException("Error: invalid input data in JSON structure.");
        } catch (FileNotFoundException e) {
            throw new TokenManagementException("Error: input file not found.");
        } catch (IOException e) {
            throw new TokenManagementException("Error: JSON object cannot be created due to incorrect representation");
        }

        try {
            hex = tokenRequest.updateHex();
        } catch (NoSuchAlgorithmException e) {
            throw new TokenManagementException("Error: no such hashing algorithm.");
        } catch (Exception e) {
            throw new TokenManagementException("Error: could not encode token request.");
        }

        tokenRequestDatabase.add(tokenRequest);

        return hex;
    }

    /**
     * This method requests a token to access api after getting the tokenRequest.
     *
     * @param inputFile File path to retrieve a valid Token file. If not valid, see @throws.
     * @return Encoded token value.
     * @throws TokenManagementException with specific message based on cases.
     * @throws TokenManagementException nested, from other project sides instead catching 'em.
     */
    public String request(String inputFile) throws TokenManagementException {
        Token token;
        FileManager fileManager = new FileManager();
        TokenRequestDatabase tokenRequestDatabase = TokenRequestDatabase.getInstance();
        TokenDatabase myStore = TokenDatabase.getInstance();

        try {
            token = fileManager.readJsonFileWithConstraints(inputFile, Token.class);
        } catch (FileNotFoundException e) {
            throw new TokenManagementException("Error: input file not found.");
        } catch (UnrecognizedPropertyException e) {
            throw new TokenManagementException("Error: invalid input data in JSON structure.");
        } catch (IOException e) {
            throw new TokenManagementException("Error: JSON object cannot be created due to incorrect representation");
        }

        tokenRequestDatabase.isRequestRegistered(token);

        try {
            token.encodeValue();
            myStore.add(token);
        } catch (NoSuchAlgorithmException e) {
            throw new TokenManagementException("Error: no such hashing algorithm.");
        } catch (Exception e) {
            throw new TokenManagementException("Error: could not encode token request.");
        }

        return token.getTokenValue();
    }


    /**
     * This method verifies that a token is valid, not expired and stored in database.
     *
     * @param encodedToken
     * @return Wetter is valid or not.
     * @throws TokenManagementException If there is a crash during the verification.
     */
    public boolean verify(String encodedToken) throws TokenManagementException {
        Token tokenFound = TokenDatabase.getInstance().find(encodedToken);
        if (tokenFound != null) {
            return tokenFound.isValid();
        }
        return false;
    }

    @Override
    public TokenController clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}