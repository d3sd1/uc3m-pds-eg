package transport4future.tokenManagement.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The type Local date serializer.
 */
public class LocalDateSerializer extends StdSerializer<LocalDateTime> {

    /**
     * Instantiates a new Local date serializer.
     */
    public LocalDateSerializer() {
        super(LocalDateTime.class);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeString(value.format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)));
    }
}