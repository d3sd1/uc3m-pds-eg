package transport4future.tokenManagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import transport4future.tokenManagement.model.storage.TokenAlgorythm;
import transport4future.tokenManagement.model.storage.TokenType;

import java.util.Objects;

public class TokenHeader {
    @JsonProperty(required = true)
    private TokenAlgorythm alg;
    @JsonProperty(required = true)
    private TokenType typ;

    public TokenHeader(TokenAlgorythm alg, TokenType typ) {
        this.alg = alg;
        this.typ = typ;
    }

    public TokenHeader() {
    }

    public TokenAlgorythm getAlg() {
        return alg;
    }

    public void setAlg(TokenAlgorythm alg) {
        this.alg = alg;
    }

    public TokenType getTyp() {
        return typ;
    }

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
