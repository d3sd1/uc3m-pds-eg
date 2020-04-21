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

package Transport4Future.TokenManagement.service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashManager {
    public byte[] md5Encode(String dataToEncode) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        String input = "Stardust" + "-" + dataToEncode;
        md.update(input.getBytes(StandardCharsets.UTF_8));
        return md.digest();
    }

    public byte[] sha256Encode(String dataToSign) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(dataToSign.getBytes(StandardCharsets.UTF_8));
        return md.digest();
    }

    public String getSha256Hex(byte[] sha256) {
        return String.format("%064x", new BigInteger(1, sha256));
    }

    public String getShaMd5Hex(byte[] md5) {
        return String.format("%32x", new BigInteger(1, md5));
    }
}
