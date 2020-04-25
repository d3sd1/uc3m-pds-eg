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
import Transport4Future.TokenManagement.exception.TokenManagementException;
import Transport4Future.TokenManagement.model.skeleton.DeserializationConstraintChecker;
import Transport4Future.TokenManagement.service.Sha256Hasher;
import Transport4Future.TokenManagement.service.PatternChecker;
import Transport4Future.TokenManagement.service.TypeChecker;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.security.NoSuchAlgorithmException;

/**
 * The type Token request.
 */
public class TokenRequest implements DeserializationConstraintChecker {
    @SerializedName("Device Name")
    private final String deviceName;
    @SerializedName("Type of Device")
    private final String typeOfDevice;
    @SerializedName("Driver Version")
    private final String driverVersion;
    @SerializedName("Support e-mail")
    private final String supportEMail;
    @SerializedName("Serial Number")
    private final String serialNumber;
    @SerializedName("MAC Address")
    private final String macAddress;
    private String hex;

    /**
     * Instantiates a new Token request.
     *
     * @param deviceName    the device name
     * @param typeOfDevice  the type of device
     * @param driverVersion the driver version
     * @param supportEMail  the support e mail
     * @param serialNumber  the serial number
     * @param macAddress    the mac address
     */
    public TokenRequest(
            String deviceName,
            String typeOfDevice,
            String driverVersion,
            String supportEMail,
            String serialNumber,
            String macAddress) {
        this.deviceName = deviceName;
        this.typeOfDevice = typeOfDevice;
        this.driverVersion = driverVersion;
        this.supportEMail = supportEMail;
        this.serialNumber = serialNumber;
        this.macAddress = macAddress;
        try {
            this.updateHex();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets device name.
     *
     * @return the device name
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * Gets type of device.
     *
     * @return the type of device
     */
    public String getTypeOfDevice() {
        return typeOfDevice;
    }

    /**
     * Gets driver version.
     *
     * @return the driver version
     */
    public String getDriverVersion() {
        return driverVersion;
    }

    /**
     * Gets support e mail.
     *
     * @return the support e mail
     */
    public String getSupportEMail() {
        return supportEMail;
    }

    /**
     * Gets serial number.
     *
     * @return the serial number
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Gets mac address.
     *
     * @return the mac address
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * Update hex string.
     *
     * @return the string
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public String updateHex() throws NoSuchAlgorithmException {
        Sha256Hasher sha256Hasher = new Sha256Hasher();
        this.hex = sha256Hasher.getShaMd5Hex(sha256Hasher.md5Encode(this.toString()));
        return this.hex;
    }

    /**
     * Gets hex.
     *
     * @return the hex
     */
    public String getHex() {
        return hex;
    }

    @Override
    public String toString() {
        return "TokenRequest [\\n\\Device Name=" + this.deviceName +
                ",\n\t\\Type of Device=" + this.typeOfDevice +
                ",\n\t\\Driver Version=" + this.driverVersion +
                ",\n\t\\Support e-Mail=" + this.supportEMail +
                ",\n\t\\Serial Number=" + this.serialNumber +
                ",\n\t\\MAC Address=" + this.macAddress + "\n]";
    }
    //@Override
    public boolean areConstraintsPassed() throws TokenManagementException, JsonParseException {
        TypeChecker typeChecker = new TypeChecker();
        if (this.getDeviceName() == null
                && this.getDriverVersion() == null
                && this.getSerialNumber() == null
                && this.getSupportEMail() == null
                && this.getTypeOfDevice() == null
                && this.getMacAddress() == null) {
            throw new TokenManagementException("Error: JSON object cannot be created due to incorrect representation");
        }
        // we must check for integers since gson reflection does not divide strings nor ints, it threats it as the same
        if (this.getDeviceName() == null
                || this.getDriverVersion() == null
                || this.getSerialNumber() == null
                || this.getSupportEMail() == null
                || this.getTypeOfDevice() == null
                || this.getMacAddress() == null
                || typeChecker.isInteger(this.getSerialNumber())
                || typeChecker.isInteger(this.getDriverVersion())
                || typeChecker.isInteger(this.getMacAddress())
                || typeChecker.isInteger(this.getTypeOfDevice())
                || typeChecker.isInteger(this.getSupportEMail())
                || typeChecker.isInteger(this.getDeviceName())
        ) {
            throw new TokenManagementException("Error: invalid input data in JSON structure.");
        }
        PatternChecker patternChecker = new PatternChecker();
        if (!patternChecker.checkLengthBetween(this.getDeviceName(), 1, 20)) {
            throw new TokenManagementException("Error: invalid String length for device name.");
        }

        if (!patternChecker.checkRegex(this.getSerialNumber(), RegexConstants.SERIAL_NUMBER)) {
            throw new TokenManagementException("Error: invalid String length for serial number.");
        }
        if (!patternChecker.checkLengthBetween(this.getDriverVersion(), 1, 25)
                || !patternChecker.checkRegex(this.getDriverVersion(), RegexConstants.DRIVER_VERSION)) {
            throw new TokenManagementException("Error: invalid String length for driver version.");
        }

        if (!patternChecker.checkRegex(this.getSupportEMail(), RegexConstants.EMAIL_RFC822)) {
            throw new TokenManagementException("Error: invalid E-mail data in JSON structure.");
        }

        if (!patternChecker.checkValueInAccepted(this.getTypeOfDevice(), RegexConstants.VALID_TYPE_OF_DEVICE)) {
            throw new TokenManagementException("Error: invalid type of sensor.");
        }

        if (!patternChecker.checkRegex(this.getMacAddress(), RegexConstants.MAC_ADDRESS)) {
            throw new TokenManagementException("Error: invalid MAC Address data in JSON structure.");
        }

        return true;
    }
}
