package com.tecnositaf.rayonit.ruleengine.jsengine.utils;

public class ScriptUtils {

    public static String DEFAULT_DECLARATION_SCRIPT = "let payload;" +
            "let metaData;" +
            "let result;";

    public static String DEFAULT_SCRIPT = "payload = JSON.parse(message.getPayload());" +
            "metaData = message.getMetaData();" +
            "console.log('default')";

    public static String OUTPUT_SCRIPT_FIRST =
            "function processMessage(message){" +
            "var result;" +
            "payload = JSON.parse(message.getPayload());" +
            "metaData = message.getMetaData();" +
            "console.log('payload=> ' ,payload.toString());"
            //add logic for the result
          ;

    public static String OUTPUT_SCRIPT_SECOND =
            //add logic for the result
            "console.log('result=> ' ,result);"+
            "return result;"+
            "}" +
            "result = processMessage(message);";

}
