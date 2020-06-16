package com.skerdy.ruleengine.nodetype.processor.filter;

import com.skerdy.ruleengine.jsengine.utils.ScriptUtils;
import com.skerdy.ruleengine.message.MetaDataKeyVal;
import com.skerdy.ruleengine.core.annotation.ConfigurationField;
import com.skerdy.ruleengine.core.node.configuration.NodeConfiguration;
import org.json.simple.JSONObject;

public class SwitchNodeConfiguration implements NodeConfiguration<SwitchNodeConfiguration> {

    @ConfigurationField(required = true)
    private String script;

    public SwitchNodeConfiguration() {
        this.script = "if(payload.amount > 500) {" +
                "message.getMetaData().putValue('"+ MetaDataKeyVal.OUTPUT_RESULT + "','"+ MetaDataKeyVal.OUTPUT_TRUE +"')}" +
                "else {" +
                "message.getMetaData().putValue('"+ MetaDataKeyVal.OUTPUT_RESULT + "','"+ MetaDataKeyVal.OUTPUT_FALSE +"')} ";
    }

    public SwitchNodeConfiguration(JSONObject data) {
        String dataScript = data.get("script").toString();
        if(dataScript != null ){
            this.script = dataScript;
            this.script = ScriptUtils.OUTPUT_SCRIPT_FIRST + this.script + ScriptUtils.OUTPUT_SCRIPT_SECOND;
            System.out.println("Script is => " + this.script);
        }
    }

    @Override
    public SwitchNodeConfiguration defaultConfiguration() {
        return new SwitchNodeConfiguration();
    }

    public String getScript() {

//        this.script = "if(payload.amount > 500) { message.getMetaData().putValue('OutputResult','true')} else { message.getMetaData().putValue('OutputResult','false')} ";

        return script;
    }
}
