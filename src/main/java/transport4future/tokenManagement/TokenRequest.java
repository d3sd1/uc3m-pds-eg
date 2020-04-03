package transport4future.tokenManagement;

import java.util.Date;

public class TokenRequest {

    private String deviceName;
    private String typeOfDevice;
    private String driverVersion;
    private String supportEmail;
    private String serialNumber;
    private String macAddress;


    /**
     * Default constructor with params.
     * @author d3sd1
     * @param deviceName The name of the device.
     * @param creationDate The creation date of the token.
     * @param serialNumber Token SN.
     * @param macAddress Associated MAC address.
     */
    public TokenRequest(String deviceName, String typeOfDevice, String driverVersion, String supportEmail, String serialNumber, String macAddress) {
        this.deviceName = deviceName;
        this.typeOfDevice = typeOfDevice;
        this.driverVersion = driverVersion;
        this.supportEmail = supportEmail;
        this.serialNumber = serialNumber;
        this.macAddress = macAddress;
    }


    @Override
    public String toString() {
        return "TokenRequest [\\n\\Device Name=" + this.deviceName
                + ",\n\t\\Type of Device=" + this.typeOfDevice + ",\n\t\\Request Date=" + this.driverVersion + ",\n\t\\Support Email="
                + this.supportEmail + ",\n\t\\Serial Number=" + this.serialNumber + ",\n\t\\MAC Address=" + this.macAddress + "\n]";
    }
}
