package fr.unice.polytech.business.json;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException, JsonProcessingException {

        LocalDateTime date = LocalDateTime.now();

        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String name = jsonParser.getCurrentName();
            jsonParser.nextToken();
            if ("year".equals(name)) {
                date = date.withYear(jsonParser.getIntValue());
            } else if ("month".equals(name)) {
                date = date.withMonth(jsonParser.getIntValue());
            } else if ("day".equals(name)) {
                date = date.withDayOfMonth(jsonParser.getIntValue());
            } else if ("hour".equals(name)) {
                date = date.withHour(jsonParser.getIntValue());
            } else if ("minute".equals(name)) {
                date = date.withMinute(jsonParser.getIntValue());
            }
        }
        return date;
    }

}
