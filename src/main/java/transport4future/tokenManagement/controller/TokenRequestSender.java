package transport4future.tokenManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import transport4future.tokenManagement.exception.TokenEncodingException;
import transport4future.tokenManagement.exception.TokenManagementException;
import transport4future.tokenManagement.exception.TokenStorageException;
import transport4future.tokenManagement.model.*;
import transport4future.tokenManagement.model.storage.TokenAlgorythm;
import transport4future.tokenManagement.model.storage.TokenType;
import transport4future.tokenManagement.service.Crypt;
import transport4future.tokenManagement.service.TokenStorage;
import transport4future.tokenManagement.model.implementation.TokenRequestInterface;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * Step 2: This will generate a token doing a request, but it needs a TokenRequest.
 */
public class TokenRequestSender implements TokenRequestInterface {
    /**
     * TokenStorage service.
     */
    private TokenStorage tokenStorage = new TokenStorage();
    private Crypt crypt = new Crypt();

    @Override
    public String RequestToken(String inputFile) throws TokenManagementException {
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
         * Generate token issue.
         */
        TokenIssue tokenIssue;

        try {
            ObjectMapper mapper = new ObjectMapper();
            tokenIssue = mapper.readValue(json, TokenIssue.class);
        } catch(Exception e) {
            throw new TokenManagementException("El fichero de entrada no contiene los datos o el formato esperado.");
        }



        /**
         * Prepare token.
         */
        Token token;
        try {
            token = new Token();
            token.setHeader(new TokenHeader(TokenAlgorythm.HS256, TokenType.PDS));
            token.setPayload(new TokenPayload(tokenIssue));
            token.setSignature(TokenAlgorythm.HS256);
        } catch(Exception e) {
            throw new TokenManagementException("Se ha producido un error interno en la generación del token. ");
        }

        /**
         * Store token.
         */
        try {
            this.tokenStorage.add(token);
        } catch(Exception e) {
            throw new TokenManagementException("Se ha producido un error interno en el almacenamiento del token.");
        }

        /**
         * Return base64urlencode encoded token.
         */
        String encodedToken;
        try {
            encodedToken = this.crypt.encode(token);

        } catch(Exception e) {
            throw new TokenManagementException("Se ha producido un error interno en la codificación del token.");
        }
        System.out.println(encodedToken);
        return encodedToken;
    }
}