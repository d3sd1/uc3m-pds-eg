package transport4future.tokenManagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import transport4future.tokenManagement.model.storage.TokenAlgorythm;
import transport4future.tokenManagement.model.storage.TokenType;

import java.util.Objects;

/**
 * The type Token header.
 */
public class TokenHeader {
    @JsonProperty(required = true)
    private TokenAlgorythm alg;
    @JsonProperty(required = true)
    private TokenType typ;

    /**
     * Instantiates a new Token header.
     *
     * @param alg the alg
     * @param typ the typ
     */
    public TokenHeader(TokenAlgorythm alg, TokenType typ) {
        this.alg = alg;
        this.typ = typ;
    }

    /**
     * Instantiates a new Token header.
     */
    public TokenHeader() {
    }

    /**
     * Gets alg.
     *
     * @return the alg
     */
    public TokenAlgorythm getAlg() {
        return alg;
    }

    /**
     * Sets alg.
     *
     * @param alg the alg
     */
    public void setAlg(TokenAlgorythm alg) {
        this.alg = alg;
    }

    /**
     * Gets typ.
     *
     * @return the typ
     */
    public TokenType getTyp() {
        return typ;
    }

    /**
     * Sets typ.
     *
     * @param typ the typ
     */
    public void setTyp(TokenType typ) {
        this.typ = typ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenHeader that = (TokenHeader) o;
        return alg == that.alg &&
                typ == that.typ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(alg, typ);
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
