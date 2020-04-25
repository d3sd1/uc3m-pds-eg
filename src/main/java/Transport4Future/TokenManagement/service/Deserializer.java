package Transport4Future.TokenManagement.service;

import Transport4Future.TokenManagement.model.Token;
import Transport4Future.TokenManagement.model.TokenRequest;
import Transport4Future.TokenManagement.model.deserializers.TokenDeserializer;
import Transport4Future.TokenManagement.model.deserializers.TokenRequestDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class Deserializer {
    private Gson getDeserializer() {
        GsonBuilder builder = new GsonBuilder();
        //TODO: if dev-> enable this, if not, not
        builder.setPrettyPrinting().create();
        builder.registerTypeAdapter(Token.class, new TokenDeserializer());
        builder.registerTypeAdapter(TokenRequest.class, new TokenRequestDeserializer());
        return builder.create();
    }
    public <T> String jsonEncode(T obj) {
        return this.getDeserializer().toJson(
                obj
        );
    }
    public <T> T jsonDecode(String json, Class<T> deserializeClass) {
        return this.getDeserializer().fromJson(json, deserializeClass);
    }
    public <T> T jsonDecode(String json, Type type) {
        return this.getDeserializer().fromJson(json, type);
    }
}
