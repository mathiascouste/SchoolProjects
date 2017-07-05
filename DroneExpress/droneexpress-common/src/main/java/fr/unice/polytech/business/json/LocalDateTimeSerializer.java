package fr.unice.polytech.business.json;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime date, JsonGenerator jgen, SerializerProvider sp)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("year", date.getYear());
        jgen.writeNumberField("month", date.getMonthValue());
        jgen.writeNumberField("day", date.getDayOfMonth());
        jgen.writeNumberField("hour", date.getHour());
        jgen.writeNumberField("minute", date.getMinute());
        jgen.writeEndObject();
    }

}
