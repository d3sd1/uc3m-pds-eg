package transport4future.tokenManagement.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import transport4future.tokenManagement.exception.TokenEncodingException;
import transport4future.tokenManagement.model.Token;
import transport4future.tokenManagement.model.TokenIssue;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class Crypt {
    public String encode(Token token) throws TokenEncodingException {
        String encodedData;
        try {
            ObjectMapper mapper = new ObjectMapper();
            encodedData = Base64.getUrlEncoder().encodeToString(mapper.writeValueAsString(token).getBytes());
        } catch (Exception e) {
            throw new TokenEncodingException("No se ha podido codificar la información dada.");
        }
        return encodedData;
    }

    public Token decode(String encodedData) throws TokenEncodingException {
        Token token;
        try {
            ObjectMapper mapper = new ObjectMapper();
            token = mapper.readValue(new String(Base64.getUrlDecoder().decode(encodedData)), Token.class);
        } catch (Exception e) {
            throw new TokenEncodingException("No se ha podido decodificar la información dada.");
        }
        return token;
    }


    public String md5Encoder(String message) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String digest;
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(message.getBytes("UTF-8"));

        //converting byte array to Hexadecimal String
        StringBuilder sb = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            sb.append(String.format("%02x", b & 0xff));
        }

        digest = sb.toString();
        return digest;
    }
}
