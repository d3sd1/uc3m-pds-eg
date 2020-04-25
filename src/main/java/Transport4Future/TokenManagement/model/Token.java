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

import Transport4Future.TokenManagement.config.RegexConstants;
import Transport4Future.TokenManagement.database.TokenDatabase;
import Transport4Future.TokenManagement.exception.TokenManagementException;
import Transport4Future.TokenManagement.model.skeleton.DeserializationConstraintChecker;
import Transport4Future.TokenManagement.service.HashManager;
import Transport4Future.TokenManagement.service.PatternChecker;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;

/**
 * The type Token.
 */
public class Token implements DeserializationConstraintChecker {
    private final String alg;
    private final String typ;
    private final long iat;
    private final long exp;
    @SerializedName("Token Request")
    private final String device;
    @SerializedName("Request Date")
    private final String requestDate;
    @SerializedName("Notification e-mail")
    private final String notificationEmail;
    private String signature;
    private String tokenValue;

    /**
     * Instantiates a new Token.
     *
     * @param Device            the device
     * @param RequestDate       the request date
     * @param NotificationEmail the notification email
     */
    public Token(
            String Device,
            String RequestDate,
            String NotificationEmail) {
        this.alg = "HS256";
        this.typ = "PDS";
        this.device = Device;
        this.requestDate = RequestDate;
        this.notificationEmail = NotificationEmail;
//		this.iat = System.currentTimeMillis();
        // SOLO PARA PRUEBAS
        this.iat = 1584523340892l;
        if ((this.device.startsWith("5"))) {
            this.exp = this.iat + 604800000l;
        } else {
            this.exp = this.iat + 65604800000l;
        }
        this.signature = null;
        this.tokenValue = null;
    }

    /**
     * Gets device.
     *
     * @return the device
     */
    public String getDevice() {
        return device;
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

        return "Dev=" + this.device
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
     * Encode value.
     *
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public void encodeValue() throws NoSuchAlgorithmException {
        HashManager hashManager = new HashManager();
        byte[] sha256 = hashManager.sha256Encode(this.getHeader() + this.getPayload());
        String hex = hashManager.getSha256Hex(sha256);
        this.setSignature(hex);
        String stringToEncode = this.getHeader() + this.getPayload() + this.getSignature();
        String encodedString = Base64.getUrlEncoder().encodeToString(stringToEncode.getBytes());
        this.tokenValue = encodedString;
    }

    /**
     * Is valid boolean.
     *
     * @return the boolean
     */
    public boolean isValid() {
        return (!this.isExpired()) && (this.isGranted());
    }

    @Override
    public String toString() {
        return "Token{" +
                "alg='" + alg + '\'' +
                ", typ='" + typ + '\'' +
                ", iat=" + iat +
                ", exp=" + exp +
                ", device='" + device + '\'' +
                ", requestDate='" + requestDate + '\'' +
                ", notificationEmail='" + notificationEmail + '\'' +
                ", signature='" + signature + '\'' +
                ", tokenValue='" + tokenValue + '\'' +
                '}';
    }

    @Override
    public boolean areConstraintsPassed() throws TokenManagementException, JsonParseException {

        if (this.getDevice() == null
                && this.getNotificationEmail() == null
                && this.getRequestDate() == null) {
            throw new TokenManagementException("Error: JSON object cannot be created due to incorrect representation");
        }
        if (this.getDevice() == null
                || this.getNotificationEmail() == null
                || this.getRequestDate() == null) {
            throw new TokenManagementException("Error: invalid input data in JSON structure.");
        }
        if (this.getDevice().contains("\"")) {
            throw new TokenManagementException("Error: JSON object cannot be created due to incorrect representation");
        }

        PatternChecker patternChecker = new PatternChecker();
        if (!patternChecker.checkRegex(this.getDevice(), RegexConstants.DEVICE)) {
            throw new TokenManagementException("Error: invalid Device in token request.");
        }

        if (!patternChecker.checkRegex(this.getNotificationEmail(), RegexConstants.EMAIL_RFC822)) {
            throw new TokenManagementException("Error: invalid E-mail data in JSON structure.");
        }
        if (!patternChecker.checkRegex(this.getRequestDate(), RegexConstants.JSON_DATE_FORMAT)) {
            throw new TokenManagementException("Error: invalid date data in JSON structure.");
        }

        return false;
    }
}
