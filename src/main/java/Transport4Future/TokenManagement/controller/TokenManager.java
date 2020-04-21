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

import Transport4Future.TokenManagement.controller.skeleton.ITokenManagement;
import Transport4Future.TokenManagement.database.TokenDatabase;
import Transport4Future.TokenManagement.database.TokenRequestDatabase;
import Transport4Future.TokenManagement.exception.JsonConstraintsException;
import Transport4Future.TokenManagement.exception.TokenManagementException;
import Transport4Future.TokenManagement.model.Token;
import Transport4Future.TokenManagement.model.TokenRequest;
import Transport4Future.TokenManagement.service.FileManager;
import Transport4Future.TokenManagement.service.HashManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class TokenManager implements ITokenManagement {

    public String generate(String inputFile) throws TokenManagementException {
        TokenRequest tokenRequest;
        byte[] encodedTokenRequest;
        String hex;

        FileManager fileManager = new FileManager();
        HashManager hashManager = new HashManager();
        TokenRequestDatabase tokenRequestDatabase = TokenRequestDatabase.getInstance();

        try {
            tokenRequest = fileManager.readJsonFileWithConstraints(inputFile, TokenRequest.class);
        } catch (FileNotFoundException e) {
            throw new TokenManagementException("Error: input file not found.");
        } catch (IOException e) {
            throw new TokenManagementException("Error: input file could not be accessed.");
        } catch (JsonConstraintsException pe) {
            throw new TokenManagementException("Error: invalid input data in JSON structure.");
        } catch (Exception e) {
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

        try {
            tokenRequestDatabase.saveTokenRequest(tokenRequest, hex);
        } catch (Exception e) {
            throw new TokenManagementException("Error: could not store tokenRequest on database.");
        }

        return hex;
    }

    public String request(String inputFile) throws TokenManagementException {
        Token token;
        String encodedTokenRequest = "";

        FileManager fileManager = new FileManager();
        HashManager hashManager = new HashManager();
        TokenRequestDatabase tokenRequestDatabase = TokenRequestDatabase.getInstance();

        try {
            token = fileManager.readJsonFileWithConstraints(inputFile, Token.class);
        } catch (FileNotFoundException e) {
            throw new TokenManagementException("Error: input file not found.");
        } catch (IOException e) {
            throw new TokenManagementException("Error: input file could not be accessed.");
        } catch (JsonConstraintsException pe) {
            throw new TokenManagementException("Error: invalid input data in JSON structure.");
        } catch (Exception e) {
            throw new TokenManagementException("Error: JSON object cannot be created due to incorrect representation");
        }

        try {
            byte[] sha256 = hashManager.sha256Encode(token.getHeader() + token.getPayload());
            String hex = hashManager.getSha256Hex(sha256);

            //TODO: tocar esto?
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


    public boolean verify(String encodedToken) throws TokenManagementException {
        Token tokenFound = TokenDatabase.getInstance().find(encodedToken);
        if (tokenFound != null) {
            return tokenFound.isValid();
        }
        return false;
    }
}