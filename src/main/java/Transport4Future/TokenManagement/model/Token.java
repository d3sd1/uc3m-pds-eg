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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Token {
    private final String alg;
    private final String typ;
    private final String device;
    private final String requestDate;
    private final String notificationEmail;
    private final long iat;
    private final long exp;
    private String signature;
    private String tokenValue;

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
}
