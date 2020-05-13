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

import Transport4Future.TokenManagement.database.TokenRequestsStore;
import Transport4Future.TokenManagement.database.TokensStore;
import Transport4Future.TokenManagement.exception.TokenManagementException;
import Transport4Future.TokenManagement.model.Token;
import Transport4Future.TokenManagement.model.TokenRequest;
import Transport4Future.TokenManagement.model.skeleton.TokenManagerInterface;
import Transport4Future.TokenManagement.service.FileManager;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;


/**
 * This class manages tokens, so it's purely a controller that handles actions on Tokens.
 */
public class TokenManager implements TokenManagerInterface {
    private static TokenManager instance;

    private TokenManager() {
        super();
    }

    public static TokenManager getInstance() {
        if (instance == null) {
            instance = new TokenManager();
        }
        return instance;
    }

    /**
     * This class generates a TokenRequest, that will be use to
     *
     * NOTE: Gettting exception "JsonSyntaxException" means deserializers got an exception,
     * that we poreviously configured the message, so we convert it to TokenManagementException and throw it,
     * since we cannot throw TokenManagementException from deserializers due to Gson issues.
     * @param inputFile File path to retrieve a valid RequestToken file. If not valid, see @throws.
     * @return tokenRequest HEX code, if request went success.
     * @throws TokenManagementException with specific message based on cases.
     * @throws TokenManagementException nested, from other project sides instead catching 'em.
     */
    public String TokenRequestGeneration(String inputFile) throws TokenManagementException {
        TokenRequest tokenRequest;
        String hex;
        FileManager fileManager = new FileManager();
        TokenRequestsStore tokenRequestsStore = TokenRequestsStore.getInstance();

        try {
            tokenRequest = fileManager.readJsonFile(inputFile, TokenRequest.class);
        } catch (JsonSyntaxException e) {
            throw new TokenManagementException(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new TokenManagementException("Error: input file not found.");
        } catch (MalformedJsonException e) {
            throw new TokenManagementException("Error: JSON object cannot be created due to incorrect representation");
        }
        catch (IOException e) {
            throw new TokenManagementException("Error: JSON object cannot be created due to incorrect representation");
        } catch (NullPointerException e) {
            throw new TokenManagementException("Error: invalid input data in JSON structure.");
        }

        try {
            hex = tokenRequest.getHex();
        } catch (NullPointerException e) {
            throw new TokenManagementException("Error: JSON object cannot be created due to incorrect representation");
        } catch (Exception e) {
            throw new TokenManagementException("Error: could not encode token request.");
        }

        tokenRequestsStore.add(tokenRequest);

        return hex;
    }

    /**
     * This method requests a token to access api after getting the tokenRequest.
     * NOTE: Gettting exception "JsonSyntaxException" means deserializers got an exception,
     * that we poreviously configured the message, so we convert it to TokenManagementException and throw it,
     * since we cannot throw TokenManagementException from deserializers due to Gson issues.
     *
     * @param inputFile File path to retrieve a valid Token file. If not valid, see @throws.
     * @return Encoded token value.
     * @throws TokenManagementException with specific message based on cases.
     * @throws TokenManagementException nested, from other project sides instead catching 'em.
     */
    public String RequestToken(String inputFile) throws TokenManagementException {
        Token token = null;
        FileManager fileManager = new FileManager();
        TokenRequestsStore tokenRequestsStore = TokenRequestsStore.getInstance();
        TokensStore myStore = TokensStore.getInstance();

        try {
            token = fileManager.readJsonFile(inputFile, Token.class);
        } catch (FileNotFoundException e) {
            throw new TokenManagementException("Error: input file not found.");
        } catch (JsonSyntaxException e) {
            throw new TokenManagementException(e.getMessage());
        } catch (IOException e) {
            throw new TokenManagementException("Error: JSON object cannot be created due to incorrect representation");
        }

        tokenRequestsStore.isRequestRegistered(token);

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
     * NOTE: Gettting exception "JsonSyntaxException" means deserializers got an exception,
     * that we poreviously configured the message, so we convert it to TokenManagementException and throw it,
     * since we cannot throw TokenManagementException from deserializers due to Gson issues.
     * @param encodedToken
     * @return Wetter is valid or not.
     * @return Wetter is valid or not.
     * @throws TokenManagementException If there is a crash during the verification.
     */
    public boolean VerifyToken(String encodedToken) throws TokenManagementException {
        Token tokenFound = TokensStore.getInstance().find(encodedToken);
        if (tokenFound != null) {
            return tokenFound.isValid();
        }
        return false;
    }

    @Override
    public TokenManager clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}