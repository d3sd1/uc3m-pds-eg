package transport4future.tokenManagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import transport4future.tokenManagement.utils.Constants;
import transport4future.tokenManagement.utils.LocalDateDeserializer;
import transport4future.tokenManagement.utils.LocalDateSerializer;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.Objects;

/**
 * The type Token payload.
 */
public class TokenPayload {
    @JsonProperty(required = true)
    private TokenIssue tokenRequest;
    @JsonProperty(required = true)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDateTime issuedAt;
    @JsonProperty(required = true)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDateTime expirationDate;

    /**
     * Instantiates a new Token payload.
     *
     * @param tokenRequest the token request
     */
    public TokenPayload(TokenIssue tokenRequest) {
        this.tokenRequest = tokenRequest;
        this.issuedAt = tokenRequest.getRequestDate();
        this.expirationDate = tokenRequest.getRequestDate().plusDays(Constants.TOKEN_EXPIRATION_DAYS);
    }

    /**
     * Instantiates a new Token payload.
     */
    public TokenPayload() {
    }

    /**
     * Gets token request.
     *
     * @return the token request
     */
    public TokenIssue getTokenRequest() {
        return tokenRequest;
    }

    /**
     * Sets token request.
     *
     * @param tokenRequest the token request
     */
    public void setTokenRequest(TokenIssue tokenRequest) {
        this.tokenRequest = tokenRequest;
    }

    /**
     * Gets issued at.
     *
     * @return the issued at
     */
    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    /**
     * Sets issued at.
     *
     * @param issuedAt the issued at
     */
    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    /**
     * Gets expiration date.
     *
     * @return the expiration date
     */
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets expiration date.
     *
     * @param expirationDate the expiration date
     */
    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenPayload that = (TokenPayload) o;
        return Objects.equals(tokenRequest, that.tokenRequest) &&
                Objects.equals(issuedAt, that.issuedAt) &&
                Objects.equals(expirationDate, that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenRequest, issuedAt, expirationDate);
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
