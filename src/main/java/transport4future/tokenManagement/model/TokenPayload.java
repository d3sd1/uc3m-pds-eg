package transport4future.tokenManagement.model;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.Objects;

public class TokenPayload {
    private TokenIssue tokenRequest;
    private LocalDateTime issuedAt;
    private LocalDateTime expirationDate;

    public TokenPayload(TokenIssue tokenRequest) {
        this.tokenRequest = tokenRequest;
        this.issuedAt = tokenRequest.getRequestDate();
    }

    public TokenIssue getTokenRequest() {
        return tokenRequest;
    }

    public void setTokenRequest(TokenIssue tokenRequest) {
        this.tokenRequest = tokenRequest;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

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
        return "TokenPayload{" +
                "tokenRequest='" + tokenRequest + '\'' +
                ", issuedAt=" + issuedAt +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
