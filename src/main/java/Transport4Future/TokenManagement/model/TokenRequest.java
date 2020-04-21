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

import Transport4Future.TokenManagement.model.skeleton.DeserializationConstraintChecker;

public class TokenRequest implements DeserializationConstraintChecker {

    private final String deviceName;
    private final String typeOfDevice;
    private final String driverVersion;
    private final String supportEMail;
    private final String serialNumber;
    private final String macAddress;

    public TokenRequest(String deviceName, String typeOfDevice, String driverVersion, String supportEMail, String serialNumber, String macAddress) {
        this.deviceName = deviceName;
        this.typeOfDevice = typeOfDevice;
        this.driverVersion = driverVersion;
        this.supportEMail = supportEMail;
        this.serialNumber = serialNumber;
        this.macAddress = macAddress;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getTypeOfDevice() {
        return typeOfDevice;
    }

    public String getDriverVersion() {
        return driverVersion;
    }

    public String getSupportEMail() {
        return supportEMail;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getMacAddress() {
        return macAddress;
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

    @Override
    public boolean areConstraintsPassed() {
        //TODO: debe revisar que no tiene campos nulos y lo de abajo
        //+ esto
        /*
        PatternChecker patternChecker = new PatternChecker();
        if (!patternChecker.checkLengthBetween(Request.getDeviceName(), 1, 20)) {
            throw new TokenManagementException("Error: invalid String length for device name.");
        }

        if (patternChecker.checkRegex(Request.getSerialNumber(), RegexDatabase.SERIAL_NUMBER)) {
            throw new TokenManagementException("Error: invalid String length for serial number.");
        }

        if (!patternChecker.checkLengthBetween(Request.getDriverVersion(), 1, 25)
                || !patternChecker.checkRegex(Request.getSerialNumber(), RegexDatabase.DRIVER_VERSION)) {
            throw new TokenManagementException("Error: invalid String length for driver version.");
        }

        if (!patternChecker.checkRegex(Request.getSerialNumber(), RegexDatabase.EMAIL_RFC822)) {
            throw new TokenManagementException("Error: invalid E-mail data in JSON structure.");
        }

        if (!patternChecker.checkValueInAccepted(Request.getTypeOfDevice(), RegexDatabase.VALID_TYPE_OF_DEVICE)) {
            throw new TokenManagementException("Error: invalid type of sensor.");
        }

        if (!patternChecker.checkRegex(Request.getMacAddress(), RegexDatabase.MAC_ADDRESS)) {
            throw new TokenManagementException("Error: invalid MAC Address data in JSON structure.");
        }
         */
        return false;
    }
}
