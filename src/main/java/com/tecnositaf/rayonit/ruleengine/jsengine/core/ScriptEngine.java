package com.tecnositaf.rayonit.ruleengine.jsengine.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.tecnositaf.rayonit.ruleengine.message.Message;

import javax.script.ScriptException;
import java.util.Set;

public interface ScriptEngine {

    Message executeUpdate(Message msg) throws ScriptException;

    Message executeGenerate(Message prevMsg) throws ScriptException;

    Message executeFilter(Message msg, String script) throws ScriptException;

    Set<String> executeSwitch(Message msg) throws ScriptException;

    JsonNode executeJson(Message msg) throws ScriptException;

    String executeToString(Message msg) throws ScriptException;

    void destroy();

}
