package transport4future.tokenManagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import transport4future.tokenManagement.model.storage.TokenAlgorythm;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

/**
 * The type Token.
 */
public class Token {

    @JsonProperty(required = true)
    private TokenHeader header;

    @JsonProperty(required = true)
    private TokenPayload payload;

    @JsonProperty(required = true)
    private TokenAlgorythm signature;

    /**
     * Instantiates a new Token.
     */
    public Token() {
    }


    /**
     * Gets header.
     *
     * @return the header
     */
    public TokenHeader getHeader() {
        return header;
    }

    /**
     * Sets header.
     *
     * @param header the header
     */
    public void setHeader(TokenHeader header) {
        this.header = header;
    }


    /**
     * Gets payload.
     *
     * @return the payload
     */
    public TokenPayload getPayload() {
        return payload;
    }

    /**
     * Sets payload.
     *
     * @param payload the payload
     */
    public void setPayload(TokenPayload payload) {
        this.payload = payload;
    }

    /**
     * Gets signature.
     *
     * @return the signature
     */
    public TokenAlgorythm getSignature() {
        return signature;
    }

    /**
     * Sets signature.
     *
     * @param signature the signature
     */
    public void setSignature(TokenAlgorythm signature) {
        this.signature = signature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(header, token.header) &&
                Objects.equals(payload, token.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, payload, signature);
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}