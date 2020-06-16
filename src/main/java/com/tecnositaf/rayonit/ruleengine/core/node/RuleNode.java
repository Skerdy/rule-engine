package com.tecnositaf.rayonit.ruleengine.core.node;


import com.tecnositaf.rayonit.ruleengine.core.node.configuration.NodeConfiguration;
import com.tecnositaf.rayonit.ruleengine.message.Message;

public interface RuleNode<T extends NodeConfiguration> {

    void init(T configuration);

    void onMessage(Message message);

    void destroy();

    void start();

    void stop();

    boolean isSourceNode();

}
