package transport4future.tokenManagement;

//import transport4future.tokenManagement.exception.TokenManagementException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonParsingException;


public class TokenManager {

    /**
     * Read token from json.
     * @author d3sd1, M1key
     * @param path - Physical path to token
     * @return TokenRequest Object
     * @throws TokenManagementException When file is not available.
     */

    public String TokenRequestGeneration(String path) throws TokenManagementException {
        TokenRequest Token;
        String myToken;
        Token = readTokenRequestFromJson(path);
        myToken = CodeHashMD5(Token);
        return Token.toString();
    }

    public TokenRequest readTokenRequestFromJson(String path) throws TokenManagementException {
        TokenRequest req;
        String fileContents = "";
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            throw new TokenManagementException("Error: input file not found.");
        }
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                fileContents += line;
            }
        } catch (IOException e) {
            throw new TokenManagementException("Error: input file can't be accessed.");
        }

        try {
            reader.close();
        } catch (IOException e){
            throw new TokenManagementException("Error: input file couldn't be closed.");
        }

        JsonObject jsonLicense = Json.createReader(new StringReader(fileContents)).readObject();

        try {
            String deviceName = jsonLicense.getString("Device Name");
            String typeOfDevice = jsonLicense.getString("Type of device");
            String driverVersion = jsonLicense.getString("Driver Version");
            String supportEmail = jsonLicense.getString("Support e-mail");
            String serialNumber = jsonLicense.getString("Serial Number");
            String macAddress = jsonLicense.getString("MAC Address");
            req = new TokenRequest(deviceName, typeOfDevice, driverVersion, supportEmail, serialNumber, macAddress);
        } catch (Exception pe) {
            throw new TokenManagementException("Error: invalid input data in JSON structure.");
        }
        return req;
    }

    public String CodeHashMD5(TokenRequest mytoken) throws TokenManagementException {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new TokenManagementException("Error: no such hashing algorithm.");
        }
        String input = "Stardust" + " " + mytoken.toString();

        md.update(input.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        String hex = String.format("%32x", new BigInteger(1, digest));
        return hex;
    }
}
