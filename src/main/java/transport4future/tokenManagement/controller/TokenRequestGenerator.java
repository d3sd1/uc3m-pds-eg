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

package transport4future.tokenManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import transport4future.tokenManagement.exception.TokenManagementException;
import transport4future.tokenManagement.model.TokenRequest;
import transport4future.tokenManagement.model.implementation.TokenRequestGeneratorInterface;
import transport4future.tokenManagement.service.Crypt;
import transport4future.tokenManagement.utils.Constants;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This is STEP 1 for token management process.
 * This generates a Token Request that will be used to use it when we want the token.
 */
public class TokenRequestGenerator implements TokenRequestGeneratorInterface {
    private Crypt crypt;

    /**
     * Instantiates a new Token request generator.
     */
    public TokenRequestGenerator() {
        this.crypt = new Crypt();
    }

    @Override
    public String TokenRequestGeneration(String inputFile) throws TokenManagementException {
        /**
         * GET AND READ JSON FILE.
         */
        String json;
        try {
            json = new String(Files.readAllBytes(Paths.get(inputFile)));
        } catch (Exception e) {
            throw new TokenManagementException("No se encuentra el fichero con los datos de entrada.");
        }

        /**
         * TRANSFORM JSON TO MODEL.
         */
        TokenRequest tokenRequest;
        try {
            ObjectMapper mapper = new ObjectMapper();
            tokenRequest = mapper.readValue(json, TokenRequest.class);
        } catch (Exception e) {
            throw new TokenManagementException("El fichero de entrada no contiene los datos o el formato esperado.");
        }

        String hash;
        try {
            hash = this.crypt.md5Encoder(
                    String.format("%s-%s", tokenRequest.toString(), Constants.HASH_PASSWORD)
            );
        } catch (Exception e) {
            throw new TokenManagementException("Se ha producido un error interno en la generación del Token Request.");
        }

        System.out.println(hash);


        return hash;
    }
}
