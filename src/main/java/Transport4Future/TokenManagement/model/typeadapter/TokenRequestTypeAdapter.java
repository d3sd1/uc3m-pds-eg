package Transport4Future.TokenManagement.model.typeadapter;

import Transport4Future.TokenManagement.config.RegexConstants;
import Transport4Future.TokenManagement.model.TokenRequest;
import Transport4Future.TokenManagement.service.PatternChecker;
import Transport4Future.TokenManagement.service.TypeChecker;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 *
 */
public class TokenRequestTypeAdapter extends TypeAdapter<TokenRequest> {

    /**
     * we must check for integers since gson reflection does not divide strings nor ints, it threats it as the same
     *
     * @param reader
     * @return
     * @throws IOException
     */
    @Override
    public TokenRequest read(JsonReader reader) throws IOException {
        try {
            reader.beginObject();
        } catch (IllegalStateException e) {
            throw new JsonSyntaxException("Error: JSON object cannot be created due to incorrect representation");
        }
        String fieldname = null;
        reader.setLenient(false);
        String deviceName = "",
                typeOfDevice = "",
                driverVersion = "",
                supportEmail = "",
                serialNumber = "",
                macAddress = "";
        boolean foundDeviceName = false,
                foundTypeOfDevice = false,
                foundDriverVersion = false,
                foundSupportEmail = false,
                foundSerialNumber = false,
                foundMacAddress = false;

        while (reader.hasNext()) {
            JsonToken token = reader.peek();
            if (token.equals(JsonToken.NAME)) {
                fieldname = reader.nextName();
            }

            if (fieldname == null) {
                throw new JsonSyntaxException("Unexpected errorr on TokenRequestDeserializer.");
            } else if (fieldname.equals("Device Name")) {
                deviceName = reader.nextString();
                foundDeviceName = true;
            } else if (fieldname.equals("Type of Device")) {
                typeOfDevice = reader.nextString();
                foundTypeOfDevice = true;
            } else if (fieldname.equals("Driver Version")) {
                driverVersion = reader.nextString();
                foundDriverVersion = true;
            } else if (fieldname.equals("Support e-mail")) {
                supportEmail = reader.nextString();
                foundSupportEmail = true;
            } else if (fieldname.equals("Serial Number")) {
                serialNumber = reader.nextString();
                foundSerialNumber = true;
            } else if (fieldname.equals("MAC Address")) {
                macAddress = reader.nextString();
                foundMacAddress = true;
            } else {
                throw new JsonSyntaxException("Error: invalid input data in JSON structure.");
            }
        }
        if (!foundDeviceName
                || !foundTypeOfDevice
                || !foundDriverVersion
                || !foundSupportEmail
                || !foundSerialNumber
                || !foundMacAddress) {
            throw new JsonSyntaxException("Error: invalid input data in JSON structure.");
        }
        reader.endObject();
        this.doConstraints(deviceName,
                typeOfDevice,
                driverVersion,
                supportEmail,
                serialNumber,
                macAddress);
        return new TokenRequest(
                deviceName,
                typeOfDevice,
                driverVersion,
                supportEmail,
                serialNumber,
                macAddress
        );
    }

    /**
     *
     * @param deviceName
     * @param typeOfDevice
     * @param driverVersion
     * @param supportEmail
     * @param serialNumber
     * @param macAddress
     * @throws JsonSyntaxException
     */
    private void doConstraints(String deviceName,
                               String typeOfDevice,
                               String driverVersion,
                               String supportEmail,
                               String serialNumber,
                               String macAddress) throws JsonSyntaxException {

        TypeChecker typeChecker = new TypeChecker();
        if (deviceName == null
                && typeOfDevice == null
                && serialNumber == null
                && supportEmail == null
                && driverVersion == null
                && macAddress == null) {
            throw new JsonSyntaxException("Error: JSON object cannot be created due to incorrect representation");
        }
        if (deviceName == null
                || typeOfDevice == null
                || serialNumber == null
                || supportEmail == null
                || driverVersion == null
                || macAddress == null
                || typeChecker.isInteger(serialNumber)
                || typeChecker.isInteger(driverVersion)
                || typeChecker.isInteger(macAddress)
                || typeChecker.isInteger(typeOfDevice)
                || typeChecker.isInteger(supportEmail)
                || typeChecker.isInteger(deviceName)
        ) {
            throw new JsonSyntaxException("Error: invalid input data in JSON structure.");
        }
        PatternChecker patternChecker = new PatternChecker();
        if (!patternChecker.checkLengthBetween(deviceName, 1, 20)) {
            throw new JsonSyntaxException("Error: invalid String length for device name.");
        }

        if (patternChecker.checkRegex(serialNumber, RegexConstants.SERIAL_NUMBER)) {
            throw new JsonSyntaxException("Error: invalid String length for serial number.");
        }
        if (!patternChecker.checkLengthBetween(driverVersion, 1, 25)
                || patternChecker.checkRegex(driverVersion, RegexConstants.DRIVER_VERSION)) {
            throw new JsonSyntaxException("Error: invalid String length for driver version.");
        }

        if (patternChecker.checkRegex(supportEmail, RegexConstants.EMAIL_RFC822)) {
            throw new JsonSyntaxException("Error: invalid E-mail data in JSON structure.");
        }

        if (!patternChecker.checkValueInAccepted(typeOfDevice, RegexConstants.VALID_TYPE_OF_DEVICE)) {
            throw new JsonSyntaxException("Error: invalid type of sensor.");
        }

        if (patternChecker.checkRegex(macAddress, RegexConstants.MAC_ADDRESS)) {
            throw new JsonSyntaxException("Error: invalid MAC Address data in JSON structure.");
        }
    }

    /**
     *
     * @param writer
     * @param token
     * @throws IOException
     */
    @Override
    public void write(JsonWriter writer, TokenRequest token) throws IOException {
        writer.beginObject();
        writer.name("Device Name");
        writer.value(token.getDeviceName());
        writer.name("Type of Device");
        writer.value(token.getTypeOfDevice());
        writer.name("Driver Version");
        writer.value(token.getDriverVersion());
        writer.name("Support e-mail");
        writer.value(token.getSupportEMail());
        writer.name("Serial Number");
        writer.value(token.getSerialNumber());
        writer.name("MAC Address");
        writer.value(token.getMacAddress());
        writer.endObject();
    }
}