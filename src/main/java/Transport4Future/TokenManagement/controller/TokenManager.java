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
import Transport4Future.TokenManagement.exception.JsonIncorrectRepresentationException;
import Transport4Future.TokenManagement.exception.NullPatternException;
import Transport4Future.TokenManagement.exception.TokenManagementException;
import Transport4Future.TokenManagement.model.Token;
import Transport4Future.TokenManagement.model.TokenRequest;
import Transport4Future.TokenManagement.service.FileManager;
import Transport4Future.TokenManagement.service.HashManager;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;


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
        } catch (UnrecognizedPropertyException e) {
            throw new TokenManagementException("Error: invalid input data in JSON structure.");
        } catch (FileNotFoundException e) {
            throw new TokenManagementException("Error: input file not found.");
        } catch (JsonIncorrectRepresentationException e) {
            throw new TokenManagementException("Error: invalid input data in JSON structure.");
        } catch (IOException e) {
            throw new TokenManagementException("Error: JSON object cannot be created due to incorrect representation");
        } catch (JsonConstraintsException | NullPatternException e) {
            throw new TokenManagementException("Error: invalid input data in JSON structure.");
        }

        try {
            encodedTokenRequest = hashManager.md5Encode(tokenRequest.toString());
            hex = hashManager.getShaMd5Hex(encodedTokenRequest);
        } catch (NoSuchAlgorithmException e) {
            throw new TokenManagementException("Error: no such hashing algorithm.");
        } catch (Exception e) {
            throw new TokenManagementException("Error: could not encode token request.");
        }


//TODO: estoo hace algo fijo.
        //Generar un HashMap para guardar los objetos
        Gson gson = new Gson();
        String jsonString;
        HashMap<String, TokenRequest> clonedMap;
        String storePath = System.getProperty("user.dir") + "/Store/tokenRequestsStore.json";
        //Tengo que cargar el almacen de tokens request en memoria y añadir el nuevo si no existe
        try {
            Object object = gson.fromJson(new FileReader(storePath), Object.class);
            jsonString = gson.toJson(object);
            Type type = new TypeToken<HashMap<String, TokenRequest>>() {
            }.getType();
            clonedMap = gson.fromJson(jsonString, type);
        } catch (Exception e) {
            clonedMap = null;
        }
        if (clonedMap == null) {
            clonedMap = new HashMap();
            clonedMap.put(hex, tokenRequest);
        } else if (!clonedMap.containsKey(hex)) {
            clonedMap.put(hex, tokenRequest);
        }


        // Guardar el Tokens Requests Store actualizado
        jsonString = gson.toJson(clonedMap);
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(storePath);
            fileWriter.write(jsonString);
            fileWriter.close();
        } catch (IOException e) {
            throw new TokenManagementException("Error: Unable to save a new token in the internal licenses store");
        }/*
        try {
            tokenRequestDatabase.saveTokenRequest(tokenRequest, hex);
        } catch (Exception e) {
            throw new TokenManagementException("Error: could not store tokenRequest on database.");
        }*/

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
        } catch (UnrecognizedPropertyException e) {
            e.printStackTrace();
            throw new TokenManagementException("Error: invalid input data in JSON structure.");
        } catch (IOException e) {
            // CUANDO JsonMappingException debe mostrar mensajes especificos
            System.out.println("CLASS " + e.getClass());
            e.printStackTrace();
            throw new TokenManagementException("Error: JSON object cannot be created due to incorrect representation");
        } catch (JsonConstraintsException pe) {
            throw new TokenManagementException("Error: invalid input data in JSON structure.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new TokenManagementException("???" + e.getClass());
        }

        //TODO: esto para algo?
        /*

        //Generar un HashMap para guardar los objetos
        Gson gson = new Gson();
        String jsonString;
        HashMap<String, TokenRequest> clonedMap = null;
        String storePath = System.getProperty("user.dir") + "/Store/tokenRequestsStore.json";

        //Cargar el almacen de tokens request en memoria y añadir el nuevo si no existe
        try {
            Object object = gson.fromJson(new FileReader(storePath), Object.class);
            jsonString = gson.toJson(object);
            Type type = new TypeToken<HashMap<String, TokenRequest>>() {
            }.getType();
            clonedMap = gson.fromJson(jsonString, type);
        } catch (Exception e) {
            throw new TokenManagementException("Error: unable to recover Token Requests Store.");
        }
        if (clonedMap == null) {
            throw new TokenManagementException("Error: Token Request Not Previously Registered");
        } else if (!clonedMap.containsKey(TokenToVerify.getDevice())) {
            throw new TokenManagementException("Error: Token Request Not Previously Registered");
        }
         */

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