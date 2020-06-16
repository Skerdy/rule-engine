package com.tecnositaf.rayonit.ruleengine.nodetype.processor.filter;

import com.tecnositaf.rayonit.ruleengine.core.node.ComponentType;
import com.tecnositaf.rayonit.ruleengine.core.node.Node;
import com.tecnositaf.rayonit.ruleengine.core.node.duplex.DuplexNode;
import com.tecnositaf.rayonit.ruleengine.jsengine.JavaScriptEngine;
import com.tecnositaf.rayonit.ruleengine.jsengine.utils.ScriptUtils;
import com.tecnositaf.rayonit.ruleengine.message.Message;

import javax.script.ScriptException;

@Node(
        type = ComponentType.FILTER,
        name = "Switch Node", relationTypes = {"Connected", "Disconnected"},
        configClazz = SwitchNodeConfiguration.class,
        nodeDescription = "Switch Node",
        nodeDetails =
                "Details - Filter Node that returns message with output metadata based on javascript filtering on the payload")
public class SwitchNode extends DuplexNode<SwitchNodeConfiguration> {

    private JavaScriptEngine javaScriptEngine;

    private SwitchNodeConfiguration config;

    //TODO inject javascript engine we are going to build with GraalVM to take care of the filter

    public SwitchNode(JavaScriptEngine javaScriptEngine) {
        this.javaScriptEngine = javaScriptEngine;
        this.setId("SWITCH_NODE_ID");
    }

    public SwitchNode() {
    }

    @Override
    public Message processMessage(Message message) {
        //TODO for now just pretend we got a result from a message filter to apply it to the input stream

        try {
            return javaScriptEngine.executeFilter(message, config.getScript());
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public void init(SwitchNodeConfiguration configuration) {
        this.config = configuration;
    }

    @Override
    public void onMessage(Message message) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isSourceNode() {
        return false;
    }

    public void setJavaScriptEngine(JavaScriptEngine javaScriptEngine) {
        this.javaScriptEngine = javaScriptEngine;
    }
}
