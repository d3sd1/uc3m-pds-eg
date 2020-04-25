package Transport4Future.TokenManagement.model.deserializers;

import Transport4Future.TokenManagement.model.Token;
import Transport4Future.TokenManagement.model.TokenRequest;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class TokenDeserializer extends TypeAdapter<Token> {

    @Override
    public Token read(JsonReader reader) throws IOException {
        try {
            reader.beginObject();
        } catch (IllegalStateException e) {
            throw new JsonSyntaxException("Error: JSON object cannot be created due to incorrect representation");
        }
        String fieldname = null;
        reader.setLenient(false);
        String tokenRequest = "",
                notificationEmail = "",
                requestDate = "";
        boolean foundTokenRequest = false,
                foundNotificationEmail = false,
                foundRequestDate = false;

        System.out.println(reader.getPath());
        while (reader.hasNext()) {
            JsonToken token = reader.peek();
            if (token.equals(JsonToken.NAME)) {
                //get the current token
                fieldname = reader.nextName();
                System.out.println("FJSON IELD " + fieldname);
            }

            if (fieldname == null) {
                throw new JsonSyntaxException("Unexpected errorr on TokenRequestDeserializer.");
            } else if (fieldname.equals("Token Request")) {
                tokenRequest = reader.nextString();
                foundTokenRequest = true;
            } else if (fieldname.equals("Notification e-mail")) {
                notificationEmail = reader.nextString();
                foundNotificationEmail = true;
            } else if (fieldname.equals("Request Date")) {
                requestDate = reader.nextString();
                foundRequestDate = true;
            } else {
                throw new JsonSyntaxException("Error: invalid input data in JSON structure.");
            }
        }
        if (!foundTokenRequest
                || !foundNotificationEmail
                || !foundRequestDate) {
            throw new JsonSyntaxException("Error: invalid input data in JSON structure.");
        }
        reader.endObject();
        /*this.doConstraints(deviceName,
                typeOfDevice,
                driverVersion,
                supportEmail,
                serialNumber,
                macAddress);*/
        return new Token(
                tokenRequest,
                notificationEmail,
                requestDate
        );
    }

    @Override
    public void write(JsonWriter writer, Token token) throws IOException {
    }
}