package com.skerdy.ruleengine.nodetype.action.sendsms;

import com.skerdy.ruleengine.core.node.ComponentType;
import com.skerdy.ruleengine.core.node.Node;
import com.skerdy.ruleengine.message.Message;
import com.skerdy.ruleengine.nodetype.action.ActionNode;
import com.skerdy.ruleengine.core.node.simple.SimpleNode;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;

@Node(
        type = ComponentType.ACTION,
        name = "Sms Sender Node", relationTypes = {"Connected", "Disconnected"},
        configClazz = SmsSenderNodeConfiguration.class,
        nodeDescription = "Send sms",
        nodeDetails =
                "Details - Action Node that sends an sms based on inputed configuration")
public class SmsSenderNode extends SimpleNode<SmsSenderNodeConfiguration> implements ActionNode<Message> {

    private String toPhone;
    private String fromPhone;
    private String body;

    @Override
    public void init(SmsSenderNodeConfiguration configuration) {
        Twilio.init(configuration.getSid(), configuration.getAuthToken());
        this.toPhone = configuration.getToPhone();
        this.fromPhone = configuration.getFromPhone();
        this.body = configuration.getBody();
    }

    @Override
    public void onMessage(Message message) {
        trigerAction(message);
    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean isSourceNode() {
        return false;
    }

    @Override
    public void trigerAction(Message message) {
        com.twilio.rest.api.v2010.account.Message twilioMessage = com.twilio.rest.api.v2010.account.Message
                .creator(new PhoneNumber(this.toPhone), // to
                        new PhoneNumber(this.fromPhone), // from
                        this.body)
                .create();
        System.out.println(" Error => " + twilioMessage.getErrorMessage());
    }
}
