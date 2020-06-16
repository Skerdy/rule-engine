package com.skerdy.ruleengine.core.node;


import com.skerdy.ruleengine.message.Message;
import com.skerdy.ruleengine.core.node.configuration.NodeConfiguration;

public interface RuleNode<T extends NodeConfiguration> {

    void init(T configuration);

    void onMessage(Message message);

    void destroy();

    void start();

    void stop();

    boolean isSourceNode();

}
