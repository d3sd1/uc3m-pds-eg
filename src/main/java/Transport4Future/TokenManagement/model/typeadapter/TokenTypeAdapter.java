package Transport4Future.TokenManagement.model.typeadapter;

import Transport4Future.TokenManagement.config.RegexConstants;
import Transport4Future.TokenManagement.model.Token;
import Transport4Future.TokenManagement.service.PatternChecker;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;

import java.io.EOFException;
import java.io.IOException;

/**
 *
 */
public class TokenTypeAdapter extends TypeAdapter<Token> {

    /**
     *
     * @param reader
     * @return
     * @throws IOException
     */
    @Override
    public Token read(JsonReader reader) throws IOException {
        String tokenRequest = "",
                notificationEmail = "",
                requestDate = "";
        boolean foundTokenRequest = false,
                foundNotificationEmail = false,
                foundRequestDate = false;
        try {
            reader.setLenient(false);
            reader.beginObject();
            String fieldname = null;

            while (reader.hasNext()) {
                JsonToken token = reader.peek();
                if (token.equals(JsonToken.NAME)) {
                    fieldname = reader.nextName();
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
                    throw new JsonSyntaxException("Error: JSON object cannot be created due to incorrect representation");
                }
            }
        } catch (IllegalStateException e) {
            throw new JsonSyntaxException("Error: JSON object cannot be created due to incorrect representation");
        } catch (EOFException e) {
            throw new JsonSyntaxException("Error: JSON object cannot be created due to incorrect representation");
        } catch (MalformedJsonException e) {
            throw new JsonSyntaxException("Error: JSON object cannot be created due to incorrect representation");
        }
        if (!foundTokenRequest
                || !foundNotificationEmail
                || !foundRequestDate) {
            throw new JsonSyntaxException("Error: invalid input data in JSON structure.");
        }
        reader.endObject();
        this.doConstraints(
                tokenRequest,
                notificationEmail,
                requestDate);
        return new Token(
                tokenRequest,
                notificationEmail,
                requestDate
        );
    }

    /**
     *
     * @param tokenRequest
     * @param notificationEmail
     * @param requestDate
     */
    private void doConstraints(String tokenRequest,
            String notificationEmail,
            String requestDate) {

        if (tokenRequest == null
                && notificationEmail == null
                && requestDate == null) {
            throw new JsonSyntaxException("Error: JSON object cannot be created due to incorrect representation");
        }
        if (tokenRequest == null
                || notificationEmail == null
                || requestDate == null) {
            throw new JsonSyntaxException("Error: invalid input data in JSON structure.");
        }
        if (tokenRequest.contains("\"")) {
            throw new JsonSyntaxException("Error: JSON object cannot be created due to incorrect representation");
        }

        PatternChecker patternChecker = new PatternChecker();
        if (patternChecker.checkRegex(tokenRequest, RegexConstants.DEVICE)) {
            throw new JsonSyntaxException("Error: invalid Device in token request.");
        }

        if (patternChecker.checkRegex(notificationEmail, RegexConstants.EMAIL_RFC822)) {
            throw new JsonSyntaxException("Error: invalid E-mail data in JSON structure.");
        }
        if (patternChecker.checkRegex(requestDate, RegexConstants.JSON_DATE_FORMAT)) {
            throw new JsonSyntaxException("Error: invalid date data in JSON structure.");
        }
    }

    /**
     *
     * @param writer
     * @param token
     * @throws IOException
     */
    @Override
    public void write(JsonWriter writer, Token token) throws IOException {
        writer.beginObject();
        writer.name("Token Request");
        writer.value(token.getTokenRequest());
        writer.name("Notification e-mail");
        writer.value(token.getNotificationEmail());
        writer.name("Request Date");
        writer.value(token.getRequestDate());
        writer.endObject();
    }
}