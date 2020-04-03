package transport4future.tokenManagement.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import transport4future.tokenManagement.controller.TokenManager;
import transport4future.tokenManagement.exception.InvalidTokenException;
import transport4future.tokenManagement.exception.LMException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Pattern;

public class TokenIssue {
    @JsonProperty(required = true)
    @JsonAlias({ "Token Request" })
    private String tokenRequest;

    @JsonProperty(required = true)
    @JsonAlias({ "Notification e-mail" })
    private String notificationEmail;

    @JsonProperty(required = true)
    @JsonAlias({ "Request Date" })
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/mm/yyyy HH:MM:SS")
    private LocalDateTime requestDate;

    public TokenIssue() {
    }

    public String getTokenRequest() {
        return tokenRequest;
    }

    public void setTokenRequest(String tokenRequest) throws InvalidTokenException, LMException {
        // Sólo se permiten dos dígitos
        if(tokenRequest == null || tokenRequest.equals("")) {
            throw new InvalidTokenException("La solicitud de token no es válida.");
        }
        this.tokenRequest = tokenRequest;
    }

    public String getNotificationEmail() {
        return notificationEmail;
    }

    public void setNotificationEmail(String notificationEmail) throws InvalidTokenException {
        if(!Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])").matcher(notificationEmail).matches()) {
            throw new InvalidTokenException("El email de notificación del token no es válido.");
        }
        this.notificationEmail = notificationEmail;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) throws InvalidTokenException {
        if(requestDate == null) {
            throw new InvalidTokenException("La fecha de solicitud del token no es válida.");
        }
        this.requestDate = requestDate;
    }

    /**
     * Used for JacksonMapping.
     * @param requestDateStr
     * @throws InvalidTokenException
     */
    public void setRequestDate(String requestDateStr) throws InvalidTokenException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime requestDate = LocalDateTime.parse(requestDateStr, formatter);
        this.setRequestDate(requestDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenIssue tokenIssue = (TokenIssue) o;
        return Objects.equals(tokenRequest, tokenIssue.tokenRequest) &&
                Objects.equals(notificationEmail, tokenIssue.notificationEmail) &&
                Objects.equals(requestDate, tokenIssue.requestDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenRequest, notificationEmail, requestDate);
    }

    @Override
    public String toString() {
        return "TokenIssue{" +
                "tokenRequest='" + tokenRequest + '\'' +
                ", notificationEmail='" + notificationEmail + '\'' +
                ", requestDate=" + requestDate +
                '}';
    }
}
