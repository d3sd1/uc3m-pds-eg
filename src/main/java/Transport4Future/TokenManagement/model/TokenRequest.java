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
import Transport4Future.TokenManagement.exception.JsonIncorrectRepresentationException;
import Transport4Future.TokenManagement.exception.NullPatternException;
import Transport4Future.TokenManagement.exception.TokenManagementException;
import Transport4Future.TokenManagement.model.skeleton.DeserializationConstraintChecker;
import Transport4Future.TokenManagement.service.PatternChecker;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenRequest implements DeserializationConstraintChecker {
    private final String deviceName;
    private final String typeOfDevice;
    private final String driverVersion;
    private final String supportEMail;
    private final String serialNumber;
    private final String macAddress;

    @JsonCreator
    public TokenRequest(
            @JsonProperty(required = true, value = "Device Name") String deviceName,
            @JsonProperty(required = true, value = "Type of Device") String typeOfDevice,
            @JsonProperty(required = true, value = "Driver Version") String driverVersion,
            @JsonProperty(required = true, value = "Support e-mail") String supportEMail,
            @JsonProperty(required = true, value = "Serial Number") String serialNumber,
            @JsonProperty(required = true, value = "MAC Address") String macAddress) {
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
    public boolean areConstraintsPassed() throws TokenManagementException, JsonIncorrectRepresentationException, NullPatternException {

        if (this.getDeviceName() == null
                || this.getDriverVersion() == null
                || this.getSerialNumber() == null
                || this.getSupportEMail() == null
                || this.getTypeOfDevice() == null
                || this.getMacAddress() == null
        ) {
            throw new JsonIncorrectRepresentationException();
        }
        PatternChecker patternChecker = new PatternChecker();
        if (!patternChecker.checkLengthBetween(this.getDeviceName(), 1, 20)) {
            throw new TokenManagementException("Error: invalid String length for device name.");
        }

        if (!patternChecker.checkRegex(this.getSerialNumber(), RegexDatabase.SERIAL_NUMBER)) {
            throw new TokenManagementException("Error: invalid String length for serial number.");
        }

        if (!patternChecker.checkLengthBetween(this.getDriverVersion(), 1, 25)
                || !patternChecker.checkRegex(this.getDriverVersion(), RegexDatabase.DRIVER_VERSION)) {
            throw new TokenManagementException("Error: invalid String length for driver version.");
        }

        if (!patternChecker.checkRegex(this.getSupportEMail(), RegexDatabase.EMAIL_RFC822)) {
            throw new TokenManagementException("Error: invalid E-mail data in JSON structure.");
        }

        if (!patternChecker.checkValueInAccepted(this.getTypeOfDevice(), RegexDatabase.VALID_TYPE_OF_DEVICE)) {
            throw new TokenManagementException("Error: invalid type of sensor.");
        }

        if (!patternChecker.checkRegex(this.getMacAddress(), RegexDatabase.MAC_ADDRESS)) {
            throw new TokenManagementException("Error: invalid MAC Address data in JSON structure.");
        }

        return true;
    }
}
