package com.tecnositaf.rayonit.ruleengine.nodetype.processor.enrichment;

import com.tecnositaf.rayonit.ruleengine.core.node.ComponentType;
import com.tecnositaf.rayonit.ruleengine.core.node.Node;
import com.tecnositaf.rayonit.ruleengine.core.node.duplex.DuplexNode;
import com.tecnositaf.rayonit.ruleengine.message.Message;
import com.tecnositaf.rayonit.ruleengine.nodetype.processor.filter.SwitchNodeConfiguration;

@Node(
        type = ComponentType.FILTER,
        name = "Switch Node", relationTypes = {"Connected", "Disconnected"},
        configClazz = SwitchNodeConfiguration.class,
        nodeDescription = "Switch Node",
        nodeDetails =
                "Details - Filter Node that returns message with output metadata based on javascript filtering on the payload")
public class TestNode extends DuplexNode<TestNodeConfiguration> {


    public TestNode() {
        setId("TESTNODEID");
    }

    @Override
    public Message processMessage(Message message) {
        return message;
    }

    @Override
    public void init(TestNodeConfiguration configuration) {

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


}
