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
import Transport4Future.TokenManagement.service.HashManager;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


/**
 * This class manages tokens, so it's purely a controller that handles actions on Tokens.
 */
public class TokenController implements TokenManager {

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
        HashManager hashManager = new HashManager();
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
            encodedTokenRequest = hashManager.md5Encode(tokenRequest.toString());
            hex = hashManager.getShaMd5Hex(encodedTokenRequest);
        } catch (NoSuchAlgorithmException e) {
            throw new TokenManagementException("Error: no such hashing algorithm.");
        } catch (Exception e) {
            throw new TokenManagementException("Error: could not encode token request.");
        }
        tokenRequestDatabase.add(tokenRequest, hex);

        return hex;
    }

    /**
     * @param inputFile
     * @return
     * @throws TokenManagementException
     */
    public String request(String inputFile) throws TokenManagementException {
        Token token;
        FileManager fileManager = new FileManager();
        HashManager hashManager = new HashManager();
        TokenRequestDatabase tokenRequestDatabase = TokenRequestDatabase.getInstance();

        try {
            token = fileManager.readJsonFileWithConstraints(inputFile, Token.class);
        } catch (FileNotFoundException e) {
            throw new TokenManagementException("Error: input file not found.");
        } catch (UnrecognizedPropertyException e) {
            throw new TokenManagementException("Error: invalid input data in JSON structure.");
        } catch (IOException e) {
            throw new TokenManagementException("Error: JSON object cannot be created due to incorrect representation");
        }


        tokenRequestDatabase.has(token);

        try {
            byte[] sha256 = hashManager.sha256Encode(token.getHeader() + token.getPayload());
            String hex = hashManager.getSha256Hex(sha256);
            token.setSignature(hex);
            String stringToEncode = token.getHeader() + token.getPayload() + token.getSignature();
            String encodedString = Base64.getUrlEncoder().encodeToString(stringToEncode.getBytes());
            token.setTokenValue(encodedString);
            TokenDatabase myStore = TokenDatabase.getInstance();
            myStore.add(token);
        } catch (NoSuchAlgorithmException e) {
            throw new TokenManagementException("Error: no such hashing algorithm.");
        } catch (Exception e) {
            throw new TokenManagementException("Error: could not encode token request.");
        }

        return token.getTokenValue();
    }


    /**
     * @param encodedToken
     * @return
     * @throws TokenManagementException
     */
    public boolean verify(String encodedToken) throws TokenManagementException {
        Token tokenFound = TokenDatabase.getInstance().find(encodedToken);
        if (tokenFound != null) {
            return tokenFound.isValid();
        }
        return false;
    }
}