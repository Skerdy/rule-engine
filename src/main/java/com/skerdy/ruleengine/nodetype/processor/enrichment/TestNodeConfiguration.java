package com.skerdy.ruleengine.nodetype.processor.enrichment;

import com.skerdy.ruleengine.core.node.configuration.NodeConfiguration;
import org.json.simple.JSONObject;

public class TestNodeConfiguration implements NodeConfiguration<TestNodeConfiguration> {

    @Override
    public TestNodeConfiguration defaultConfiguration() {
        return new TestNodeConfiguration();
    }

    public TestNodeConfiguration(JSONObject jsonObject) {
    }

    public TestNodeConfiguration() {
    }
}
