package Transport4Future.TokenManagement.service;

import Transport4Future.TokenManagement.config.Constants;
import Transport4Future.TokenManagement.model.Token;
import Transport4Future.TokenManagement.model.TokenRequest;
import Transport4Future.TokenManagement.model.deserializers.TokenDeserializer;
import Transport4Future.TokenManagement.model.deserializers.TokenRequestDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 *
 */
public class Deserializer {
    /**
     *
     * @return
     */
    private Gson getDeserializer() {
        GsonBuilder builder = new GsonBuilder();
        if (Constants.IS_DEV) {
            builder.setPrettyPrinting();
        }
        builder.create();
        builder.registerTypeAdapter(Token.class, new TokenDeserializer());
        builder.registerTypeAdapter(TokenRequest.class, new TokenRequestDeserializer());
        return builder.create();
    }

    /**
     *
     * @param obj
     * @param <T>
     * @return
     */
    public <T> String jsonEncode(T obj) {
        return this.getDeserializer().toJson(
                obj
        );
    }

    /**
     *
     * @param json
     * @param deserializeClass
     * @param <T>
     * @return
     */
    public <T> T jsonDecode(String json, Class<T> deserializeClass) {
        return this.getDeserializer().fromJson(json, deserializeClass);
    }

    /**
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public <T> T jsonDecode(String json, Type type) {
        return this.getDeserializer().fromJson(json, type);
    }
}
