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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import transport4future.tokenManagement.exception.InvalidTokenRequestException;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * The type Token request.
 */
public class TokenRequest {

    @JsonProperty(required = true)
    @JsonAlias({"Device Name"})
    private String deviceName;

    @JsonProperty(required = true)
    @JsonAlias({"Type of Device"})
    private String typeOfDevice;

    @JsonProperty(required = true)
    @JsonAlias({"Driver Version"})
    private String driverVersion;

    @JsonProperty(required = true)
    @JsonAlias({"Support e-mail"})
    private String supportEmail;

    @JsonProperty(required = true)
    @JsonAlias({"Serial Number"})
    private String serialNumber;

    @JsonProperty(required = true)
    @JsonAlias({"MAC Address"})
    private String macAddress;

    /**
     * Instantiates a new Token request.
     */
    public TokenRequest() {
    }

    /**
     * Instantiates a new Token request.
     *
     * @param deviceName    the device name
     * @param typeOfDevice  the type of device
     * @param driverVersion the driver version
     * @param supportEmail  the support email
     * @param serialNumber  the serial number
     * @param macAddress    the mac address
     */
    public TokenRequest(String deviceName,
                        String typeOfDevice,
                        String driverVersion,
                        String supportEmail,
                        String serialNumber,
                        String macAddress) {
        this.deviceName = deviceName;
        this.typeOfDevice = typeOfDevice;
        this.driverVersion = driverVersion;
        this.supportEmail = supportEmail;
        this.serialNumber = serialNumber;
        this.macAddress = macAddress;
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
     * Sets device name.
     *
     * @param deviceName the device name
     * @throws InvalidTokenRequestException the invalid token request exception
     */
    public void setDeviceName(String deviceName) throws InvalidTokenRequestException {
        if (!Pattern.compile("\\b[A-Za-z]{1,20}\\b").matcher(deviceName).matches()) {
            throw new InvalidTokenRequestException("El nombre del dispositivo no es válido para la solicitud de creación de token.");
        }
        this.deviceName = deviceName;
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
     * Sets type of device.
     *
     * @param typeOfDevice the type of device
     * @throws InvalidTokenRequestException the invalid token request exception
     */
    public void setTypeOfDevice(String typeOfDevice) throws InvalidTokenRequestException {
        if (!typeOfDevice.equals("Sensor") && !typeOfDevice.equals("Actuator")) {
            throw new InvalidTokenRequestException("El tipo de dispositivo no es válido para la solicitud de creación de token.");
        }
        this.typeOfDevice = typeOfDevice;
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
     * Sets driver version.
     *
     * @param driverVersion the driver version
     * @throws InvalidTokenRequestException the invalid token request exception
     */
    public void setDriverVersion(String driverVersion) throws InvalidTokenRequestException {
        if (!Pattern.compile("\\b^\\d+(\\.\\d{1,25})+$\\b").matcher(driverVersion).matches()) {
            throw new InvalidTokenRequestException("El driver del dispositivo no es válido para la solicitud de creación de token.");
        }
        this.driverVersion = driverVersion;
    }

    /**
     * Gets support email.
     *
     * @return the support email
     */
    public String getSupportEmail() {
        return supportEmail;
    }

    /**
     * Sets support email.
     *
     * @param supportEmail the support email
     * @throws InvalidTokenRequestException the invalid token request exception
     */
    public void setSupportEmail(String supportEmail) throws InvalidTokenRequestException {
        if (!Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])").matcher(supportEmail).matches()) {
            throw new InvalidTokenRequestException("El email de soporte no es válido para la solicitud de creación de petición del token.");
        }

        this.supportEmail = supportEmail;
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
     * Sets serial number.
     *
     * @param serialNumber the serial number
     * @throws InvalidTokenRequestException the invalid token request exception
     */
    public void setSerialNumber(String serialNumber) throws InvalidTokenRequestException {
        if (!Pattern.compile("^[A-Za-z0-9\\-]+").matcher(serialNumber).matches()) {
            throw new InvalidTokenRequestException("El serial no es válido para la solicitud de creación de petición del token.");
        }
        this.serialNumber = serialNumber;
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
     * Sets mac address.
     *
     * @param macAddress the mac address
     * @throws InvalidTokenRequestException the invalid token request exception
     */
    public void setMacAddress(String macAddress) throws InvalidTokenRequestException {
        if (!Pattern.compile("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$").matcher(macAddress).matches()) {
            throw new InvalidTokenRequestException("La dirección MAC es válida para la solicitud de creación de petición del token.");
        }


        this.macAddress = macAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenRequest that = (TokenRequest) o;
        return Objects.equals(deviceName, that.deviceName) &&
                Objects.equals(typeOfDevice, that.typeOfDevice) &&
                Objects.equals(driverVersion, that.driverVersion) &&
                Objects.equals(supportEmail, that.supportEmail) &&
                Objects.equals(serialNumber, that.serialNumber) &&
                Objects.equals(macAddress, that.macAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceName, typeOfDevice, driverVersion, supportEmail, serialNumber, macAddress);
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
