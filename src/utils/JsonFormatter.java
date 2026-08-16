package utils;

import java.io.StringReader;

public class JsonFormatter {
    public static String formatJson(String jsonString){
        if(jsonString == null || jsonString.isBlank()){
            return "";
        }
//        try(StringReader jsonReader = new StringReader(jsonString)){
//
//        }catch (Exception ex){
//
//        }
        return jsonString;
    }
}
