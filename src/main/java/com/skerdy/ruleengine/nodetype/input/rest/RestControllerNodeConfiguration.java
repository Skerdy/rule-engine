package com.skerdy.ruleengine.nodetype.input.rest;

import com.skerdy.ruleengine.core.annotation.ConfigurationField;
import com.skerdy.ruleengine.core.node.configuration.NodeConfiguration;
import org.json.simple.JSONObject;

public class RestControllerNodeConfiguration implements NodeConfiguration<RestControllerNodeConfiguration> {

    @ConfigurationField(required = true)
    private String endpoint;

    @Override
    public RestControllerNodeConfiguration defaultConfiguration() {
        return new RestControllerNodeConfiguration();
    }

    public RestControllerNodeConfiguration() {
    }

    public RestControllerNodeConfiguration(JSONObject config) {
        Object endpoint = config.get("endpoint");
        if(endpoint !=null){
            this.endpoint = endpoint.toString();
        }

    }
}
