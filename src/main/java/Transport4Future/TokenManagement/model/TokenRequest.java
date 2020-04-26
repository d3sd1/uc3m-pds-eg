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
import Transport4Future.TokenManagement.service.Md5Hasher;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * The type Token request.
 */
public class TokenRequest {
    private final String deviceName;
    private final String typeOfDevice;
    private final String driverVersion;
    private final String supportEMail;
    private final String serialNumber;
    private final String macAddress;

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
     * Returns hex string.
     *
     * @return the string
     */
    public String getHex() {
        Hasher md5Hasher = new Md5Hasher();
        try {
            return md5Hasher.hex(md5Hasher.encode(
                    Constants.TOKEN_REQUEST_ENCODER_HEX
                            .replace("{{DEVICE_NAME}}", this.deviceName)
                            .replace("{{TYPE_OF_DEVICE}}", this.typeOfDevice)
                            .replace("{{DRIVER_VERSION}}", this.driverVersion)
                            .replace("{{SUPPORT_EMAIL}}", this.supportEMail)
                            .replace("{{SERIAL_NUMBER}}", this.serialNumber)
                            .replace("{{MAC_ADDRESS}}", this.macAddress)
            ));
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * Encoded string, previously used to retuen hex, but now it's self-independent.
     *
     * @return String value of the object and it's fields.
     */
    @Override
    public String toString() {
        return "TokenRequest{" +
                "deviceName='" + deviceName + '\'' +
                ", typeOfDevice='" + typeOfDevice + '\'' +
                ", driverVersion='" + driverVersion + '\'' +
                ", supportEMail='" + supportEMail + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", macAddress='" + macAddress + '\'' +
                '}';
    }

    /**
     * Object comparator.
     *
     * @param o other object to compare to this.
     * @return wetter object is nor not equals.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenRequest)) return false;
        TokenRequest that = (TokenRequest) o;
        return Objects.equals(getDeviceName(), that.getDeviceName()) &&
                Objects.equals(getTypeOfDevice(), that.getTypeOfDevice()) &&
                Objects.equals(getDriverVersion(), that.getDriverVersion()) &&
                Objects.equals(getSupportEMail(), that.getSupportEMail()) &&
                Objects.equals(getSerialNumber(), that.getSerialNumber()) &&
                Objects.equals(getMacAddress(), that.getMacAddress());
    }

    /**
     * Unique hash code object to JVM.
     *
     * @return int value of the hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getDeviceName(), getTypeOfDevice(), getDriverVersion(), getSupportEMail(), getSerialNumber(), getMacAddress());
    }
}
