package com.tecnositaf.rayonit.ruleengine.nodetype.action.sendmail;

import org.json.simple.JSONObject;

public class EmailBuilder {

    private final static String OPENING_WILDCARD = "{{$";

    private final static String CLOSING_WILDCARD = "}}";

    private final static char OPENING_BRACKET = '{';

    private final static char CLOSING_BRACKET = '}';

    private final static String DELIMITER = "\\.";

    private final static String PAYLOAD = "payload";


    public static String getEmailText(String text, JSONObject payload) {
        StringBuilder stringBuilder = null;
        String oldText = text;
        int length = oldText.length();
        String key;
        boolean writingMode = false;
        for (int i = 0; i < length; i++) {
            if (writingMode) {
                stringBuilder.append(oldText.charAt(i));
            }
            if (oldText.charAt(i) == OPENING_BRACKET) {
                stringBuilder = new StringBuilder();
                if (i + 3 < length) {
                    if (oldText.substring(i, i + 3).equals(OPENING_WILDCARD)) {
                        i = i + 2;
                        writingMode = true;
                        continue;
                        // stringBuilder.append(text.charAt(i));
                    }
                }
            } else if (i + 1 < length && oldText.charAt(i + 1) == CLOSING_BRACKET) {
                if (i + 3 <= length) {
                    if (oldText.substring(i + 1, i + 3).equals(CLOSING_WILDCARD)) {
                        i = i + 1;
                        writingMode = false;
                        assert stringBuilder != null;
                        key = stringBuilder.toString();
                        key.replace("}", "");
                        String wildcard = OPENING_WILDCARD + key + CLOSING_WILDCARD;
                        String value = getValueForKey(key, payload);
                        text = text.replace(wildcard, value);
                    }
                }
            }

        }

        return text;
    }


    private static String getValueForKey(String key, JSONObject payload) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean con = key.contains(".");
        if (key.contains(".")) {
            String[] splittedValues = key.split(DELIMITER);
            for (int i = 1; i < splittedValues.length; i++) {
                stringBuilder.append(splittedValues[i]);
            }
            return getValueForKey(stringBuilder.toString(), (JSONObject) payload.get(splittedValues[0]));
        } else {
            Object value = payload.get(key);
            if (value != null) {
                return value.toString();
            }
            return "";
        }
    }


}
