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

import Transport4Future.TokenManagement.database.RegexDatabase;
import Transport4Future.TokenManagement.exception.NullPatternException;
import Transport4Future.TokenManagement.exception.TokenManagementException;
import Transport4Future.TokenManagement.model.skeleton.DeserializationConstraintChecker;
import Transport4Future.TokenManagement.service.PatternChecker;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Token implements DeserializationConstraintChecker {
    @JsonIgnore
    private final String alg;
    @JsonIgnore
    private final String typ;
    @JsonIgnore
    private final long iat;
    @JsonIgnore
    private final long exp;
    @JsonProperty("Token Request")
    private String device;
    @JsonProperty("Request Date")
    private String requestDate;
    @JsonProperty("Notification e-mail")
    private String notificationEmail;
    @JsonIgnore
    private String signature;
    @JsonIgnore
    private String tokenValue;

    public Token() {
        this.alg = "HS256";
        this.typ = "PDS";
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

    public Token(String Device, String RequestDate, String NotificationEmail) {
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

    public String getDevice() {
        return device;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public String getNotificationEmail() {
        return notificationEmail;
    }

    public boolean isGranted() {
        return this.iat < System.currentTimeMillis();
    }

    public boolean isExpired() {
        return this.exp <= System.currentTimeMillis();
    }

    public String getHeader() {
        return "Alg=" + this.alg + "\\n Typ=" + this.typ + "\\n";
    }

    public String getPayload() {
        Date iatDate = new Date(this.iat);
        Date expDate = new Date(this.exp);

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        return "Dev=" + this.device
                + "\\n iat=" + df.format(iatDate)
                + "\\n exp=" + df.format(expDate);
    }

    public String getSignature() {
        return this.signature;
    }

    public void setSignature(String value) {
        this.signature = value;
    }

    public String getTokenValue() {
        return this.tokenValue;
    }

    public void setTokenValue(String value) {
        this.tokenValue = value;
    }

    public boolean isValid() {
        return (!this.isExpired()) && (this.isGranted());
    }

    @Override
    public boolean areConstraintsPassed() throws TokenManagementException, NullPatternException {
        PatternChecker patternChecker = new PatternChecker();
        if (!patternChecker.checkRegex(this.getDevice(), RegexDatabase.DEVICE)) {
            throw new TokenManagementException("Error: invalid Device in token request.");
        }

        if (!patternChecker.checkRegex(this.getNotificationEmail(), RegexDatabase.EMAIL_RFC822)) {
            throw new TokenManagementException("Error: invalid E-mail data in JSON structure.");
        }

        if (!patternChecker.checkRegex(this.getRequestDate(), RegexDatabase.JSON_DATE_FORMAT)) {
            throw new TokenManagementException("Error: invalid date data in JSON structure.");
        }

        return false;
    }
}
