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

package transport4future.tokenManagement.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import transport4future.tokenManagement.exception.InvalidTokenException;
import transport4future.tokenManagement.exception.LMException;
import transport4future.tokenManagement.utils.LocalDateDeserializer;
import transport4future.tokenManagement.utils.LocalDateSerializer;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * The type Token issue.
 */
public class TokenIssue {
    @JsonProperty(required = true)
    @JsonAlias({"Token Request"})
    private String tokenRequest;

    @JsonProperty(required = true)
    @JsonAlias({"Notification e-mail"})
    private String notificationEmail;

    @JsonProperty(required = true)
    @JsonAlias({"Request Date"})
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/mm/yyyy HH:MM:SS")
    private LocalDateTime requestDate;

    /**
     * Instantiates a new Token issue.
     */
    public TokenIssue() {
    }

    /**
     * Instantiates a new Token issue.
     *
     * @param tokenRequest      the token request
     * @param notificationEmail the notification email
     * @param requestDate       the request date
     */
    public TokenIssue(String tokenRequest, String notificationEmail, LocalDateTime requestDate) {
        this.tokenRequest = tokenRequest;
        this.notificationEmail = notificationEmail;
        this.requestDate = requestDate;
    }

    /**
     * Gets token request.
     *
     * @return the token request
     */
    public String getTokenRequest() {
        return tokenRequest;
    }

    /**
     * Sets token request.
     *
     * @param tokenRequest the token request
     * @throws InvalidTokenException the invalid token exception
     * @throws LMException           the lm exception
     */
    public void setTokenRequest(String tokenRequest) throws InvalidTokenException, LMException {
        // Sólo se permiten dos dígitos
        if (tokenRequest == null || tokenRequest.equals("")) {
            throw new InvalidTokenException("La solicitud de token no es válida.");
        }
        this.tokenRequest = tokenRequest;
    }

    /**
     * Gets notification email.
     *
     * @return the notification email
     */
    public String getNotificationEmail() {
        return notificationEmail;
    }

    /**
     * Sets notification email.
     *
     * @param notificationEmail the notification email
     * @throws InvalidTokenException the invalid token exception
     */
    public void setNotificationEmail(String notificationEmail) throws InvalidTokenException {
        if (!Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])").matcher(notificationEmail).matches()) {
            throw new InvalidTokenException("El email de notificación del token no es válido.");
        }
        this.notificationEmail = notificationEmail;
    }

    /**
     * Gets request date.
     *
     * @return the request date
     */
    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    /**
     * Sets request date.
     *
     * @param requestDate the request date
     * @throws InvalidTokenException the invalid token exception
     */
    public void setRequestDate(LocalDateTime requestDate) throws InvalidTokenException {
        if (requestDate == null) {
            throw new InvalidTokenException("La fecha de solicitud del token no es válida.");
        }
        this.requestDate = requestDate;
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
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
