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

package Transport4Future.TokenManagement.model;

import Transport4Future.TokenManagement.config.Constants;
import Transport4Future.TokenManagement.model.skeleton.Hasher;
import Transport4Future.TokenManagement.service.Sha256Hasher;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

/**
 * The type Token.
 */
public class Token {
    private final TokenAlgorytm alg;
    private final TokenType typ;
    private final long iat;
    private final long exp;
    private final String tokenRequest;
    private final String requestDate;
    private final String notificationEmail;
    private String signature;
    private String tokenValue;

    /**
     * Instantiates a new Token.
     *
     * @param tokenRequest      the device
     * @param RequestDate       the request date
     * @param NotificationEmail the notification email
     */
    public Token(
            String tokenRequest,
            String RequestDate,
            String NotificationEmail) {
        this.alg = TokenAlgorytm.HS256;
        this.typ = TokenType.PDS;
        this.tokenRequest = tokenRequest;
        this.requestDate = RequestDate;
        this.notificationEmail = NotificationEmail;
        if (Constants.IS_DEV) {
            this.iat = 1584523340892l;
            if ((this.tokenRequest.startsWith("5"))) {
                this.exp = this.iat + 604800000l;
            } else {
                this.exp = this.iat + 65604800000l;
            }
        } else {
            this.iat = System.currentTimeMillis();
        }
        try {
            this.encodeValue();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        this.signature = null;
        this.tokenValue = null;
    }

    /**
     * Gets device.
     *
     * @return the device
     */
    public String getTokenRequest() {
        return tokenRequest;
    }

    /**
     * Gets request date.
     *
     * @return the request date
     */
    public String getRequestDate() {
        return requestDate;
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
     * Is granted boolean.
     *
     * @return the boolean
     */
    public boolean isGranted() {
        return this.iat < System.currentTimeMillis();
    }

    /**
     * Is expired boolean.
     *
     * @return the boolean
     */
    public boolean isExpired() {
        return this.exp <= System.currentTimeMillis();
    }

    /**
     * Gets header.
     *
     * @return the header
     */
    public String getHeader() {
        return "Alg=" + this.alg + "\\n Typ=" + this.typ + "\\n";
    }

    /**
     * Gets payload.
     *
     * @return the payload
     */
    public String getPayload() {
        Date iatDate = new Date(this.iat);
        Date expDate = new Date(this.exp);

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        return "Dev=" + this.tokenRequest
                + "\\n iat=" + df.format(iatDate)
                + "\\n exp=" + df.format(expDate);
    }

    /**
     * Gets signature.
     *
     * @return the signature
     */
    public String getSignature() {
        return this.signature;
    }

    /**
     * Sets signature.
     *
     * @param value the value
     */
    public void setSignature(String value) {
        this.signature = value;
    }

    /**
     * Gets token value.
     *
     * @return the token value
     */
    public String getTokenValue() {
        return this.tokenValue;
    }


    /**
     * Encode value hash for current token.
     *
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public void encodeValue() throws NoSuchAlgorithmException {
        Hasher sha256Hasher = new Sha256Hasher();
        byte[] sha256 = sha256Hasher.encode(this.getHeader() + this.getPayload());
        String hex = sha256Hasher.hex(sha256);
        this.setSignature(hex);
        String stringToEncode = this.getHeader() + this.getPayload() + this.getSignature();
        String encodedString = Base64.getUrlEncoder().encodeToString(stringToEncode.getBytes());
        this.tokenValue = encodedString;
    }


    /**
     * Check if token is valid, granted, on database and not expired.
     *
     * @return the boolean
     */
    public boolean isValid() {
        return (!this.isExpired()) && (this.isGranted());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        Token token = (Token) o;
        return iat == token.iat &&
                exp == token.exp &&
                Objects.equals(alg, token.alg) &&
                Objects.equals(typ, token.typ) &&
                Objects.equals(getTokenRequest(), token.getTokenRequest()) &&
                Objects.equals(getRequestDate(), token.getRequestDate()) &&
                Objects.equals(getNotificationEmail(), token.getNotificationEmail()) &&
                Objects.equals(getSignature(), token.getSignature()) &&
                Objects.equals(getTokenValue(), token.getTokenValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(alg, typ, iat, exp, getTokenRequest(), getRequestDate(), getNotificationEmail(), getSignature(), getTokenValue());
    }

    @Override
    public String toString() {
        return "Token{" +
                "alg='" + alg + '\'' +
                ", typ='" + typ + '\'' +
                ", iat=" + iat +
                ", exp=" + exp +
                ", device='" + tokenRequest + '\'' +
                ", requestDate='" + requestDate + '\'' +
                ", notificationEmail='" + notificationEmail + '\'' +
                ", signature='" + signature + '\'' +
                ", tokenValue='" + tokenValue + '\'' +
                '}';
    }
}
