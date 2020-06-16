package com.tecnositaf.rayonit.ruleengine.jsengine;

import com.fasterxml.jackson.databind.JsonNode;
import com.tecnositaf.rayonit.ruleengine.jsengine.core.ScriptEngine;
import com.tecnositaf.rayonit.ruleengine.jsengine.utils.ScriptUtils;
import com.tecnositaf.rayonit.ruleengine.message.Message;
import com.tecnositaf.rayonit.ruleengine.message.MetaDataKeyVal;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.springframework.stereotype.Component;

import javax.script.ScriptException;
import java.util.Map;
import java.util.Set;

@Component
public class JavaScriptEngine implements ScriptEngine {

    private Context jsContext;

    public JavaScriptEngine() {
        this.jsContext = Context.create("js");
        runScript(ScriptUtils.DEFAULT_DECLARATION_SCRIPT);
    }

    @Override
    public Message executeUpdate(Message msg) throws ScriptException {
        return null;
    }

    @Override
    public Message executeGenerate(Message prevMsg) throws ScriptException {
        return null;
    }

    @Override
    public Message executeFilter(Message msg, String script) throws ScriptException {
        getBindings().putMember("message", msg);
        runScript(script);
       // Value processedMessage = getBindings().getMember("message");

        Value outputResult = getBindings().getMember("result");
        if(outputResult.isBoolean()) {
            if (outputResult.asBoolean()) {
                msg.setOutputResult(MetaDataKeyVal.OUTPUT_TRUE);
            } else if (!outputResult.asBoolean()) {
                msg.setOutputResult(MetaDataKeyVal.OUTPUT_FALSE);
            }
        } else {
            msg.setOutputResult(outputResult.asHostObject());
        }

        return msg;
    }

    @Override
    public Set<String> executeSwitch(Message msg) throws ScriptException {
        return null;
    }

    @Override
    public JsonNode executeJson(Message msg) throws ScriptException {
        return null;
    }

    @Override
    public String executeToString(Message msg) throws ScriptException {
        return null;
    }

    @Override
    public void destroy() {

    }

    public Value runScript(String script){
        return this.jsContext.eval("js", script);
    }

    public Value runScript(String script, Map<String, Object> args){
        for(String key : args.keySet()){
            jsContext.getBindings("js").putMember(key, args.get(key));
        }
        return runScript(script);
    }

    public Value getBindings() {
        return this.jsContext.getBindings("js");
    }
}
