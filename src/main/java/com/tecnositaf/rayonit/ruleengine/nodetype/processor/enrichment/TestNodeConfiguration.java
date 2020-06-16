package com.tecnositaf.rayonit.ruleengine.nodetype.processor.enrichment;

import com.tecnositaf.rayonit.ruleengine.core.node.configuration.NodeConfiguration;
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
