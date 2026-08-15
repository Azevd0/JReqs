package utils;

public class JsonFormatter {
    private static final String INDENT = "  ";

    public static String format(String json){
        if(json == null || json.trim().isEmpty()){
            return "";
        }
        StringBuilder formattedJson = new StringBuilder();
        int indentLevel = 0;
        boolean isEscaped = false;
        boolean inQuotes = false;

        for(int i = 0; i < json.length(); i ++){
            char currentChar = json.charAt(i);

            if (isEscaped) {
                formattedJson.append(currentChar);
                isEscaped = false;
                continue;
            }

            if (currentChar == '\\') {
                isEscaped = true;
                formattedJson.append(currentChar);
                continue;
            }

            if (currentChar == '"') {
                inQuotes = !inQuotes;
                formattedJson.append(currentChar);
                continue;
            }

            if (inQuotes) {
                formattedJson.append(currentChar);
                continue;
            }

            if (Character.isWhitespace(currentChar)) {
                continue;
            }
            switch (currentChar){
                case '[':
                case '{':
                    formattedJson.append(currentChar).append("\n");
                    indentLevel ++;
                    appendIndent(formattedJson, indentLevel);
                    break;
                case ']':
                case '}':
                    formattedJson.append("\n");
                    formattedJson.append(currentChar);
                    indentLevel --;
                    appendIndent(formattedJson, indentLevel);
                    break;
                case ',':
                    formattedJson.append(currentChar).append("\n");
                    appendIndent(formattedJson, indentLevel);
                    break;
                case ':':
                    formattedJson.append(": ");
                    break;
                default:
                    formattedJson.append(currentChar);
                    break;
            }

        }
        return formattedJson.toString();
    }
    private static void appendIndent(StringBuilder value, int count) {
        for (int i = 0; i < count; i++) {
            value.append(INDENT);
        }
    }
}
