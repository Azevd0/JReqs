package utils;

import java.io.StringReader;
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


        return jsonString;
    }
}
