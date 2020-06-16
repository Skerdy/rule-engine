package com.tecnositaf.rayonit.ruleengine.nodetype.processor.transform;

import com.tecnositaf.rayonit.ruleengine.core.node.configuration.NodeConfiguration;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

@NoArgsConstructor
public class TransformerNodeConfiguration implements NodeConfiguration<TransformerNodeConfiguration> {


    private String type;

    @Override
    public TransformerNodeConfiguration defaultConfiguration() {
        return new TransformerNodeConfiguration();
    }

    public TransformerNodeConfiguration(JSONObject config) {
    }
}
