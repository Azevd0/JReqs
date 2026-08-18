package utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import jakarta.json.Json;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import jakarta.json.stream.JsonGenerator;

public class JsonFormatter {
    public static String formatJson(String jsonString) {
        if (jsonString == null || jsonString.isBlank()) {
            return "";
        }
        try (StringReader stringReader = new StringReader(jsonString);
             JsonReader jsonReader = Json.createReader(stringReader)){
            JsonValue jsonValue = jsonReader.readValue();

            Map<String, Object> config = new HashMap<>();
            config.put(JsonGenerator.PRETTY_PRINTING, true);

            StringWriter stringWriter = new StringWriter();
            var writerFactory = Json.createWriterFactory(config);
            try (var jsonWriter = writerFactory.createWriter(stringWriter)) {
                jsonWriter.write(jsonValue);
            }
            return stringWriter.toString();
        } catch (Exception e) {
            return jsonString;
        }
    }
}
