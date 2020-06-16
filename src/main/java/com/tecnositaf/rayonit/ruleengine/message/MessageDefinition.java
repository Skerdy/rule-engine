package com.tecnositaf.rayonit.ruleengine.message;

public interface MessageDefinition {

    void setOriginator(String originator);

    void setRuleChainId(String ruleChainId);

    void setNodeId(String nodeId);

    String getOriginator();

    String getRuleChainId();

    String getNodeId();

}
