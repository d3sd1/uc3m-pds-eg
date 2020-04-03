package transport4future.tokenManagement.model;

import transport4future.tokenManagement.model.storage.TokenAlgorythm;

import java.util.Objects;

public class Token {
    private TokenHeader header;
    private TokenPayload payload;
    private TokenAlgorythm signature;

    public TokenHeader getHeader() {
        return header;
    }

    public void setHeader(TokenHeader header) {
        this.header = header;
    }

    public TokenPayload getPayload() {
        return payload;
    }

    public void setPayload(TokenPayload payload) {
        this.payload = payload;
    }

    public TokenAlgorythm getSignature() {
        return signature;
    }

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
        return "Token{" +
                "header=" + header +
                ", payload=" + payload +
                ", signature=" + signature +
                '}';
    }
}