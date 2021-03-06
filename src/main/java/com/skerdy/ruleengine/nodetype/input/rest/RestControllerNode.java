package com.skerdy.ruleengine.nodetype.input.rest;

import com.google.gson.Gson;
import com.skerdy.ruleengine.core.node.ComponentType;
import com.skerdy.ruleengine.core.node.Node;
import com.skerdy.ruleengine.core.node.simple.SimpleNode;
import com.skerdy.ruleengine.message.Message;
import com.skerdy.ruleengine.message.MessageMetaData;
import com.skerdy.ruleengine.message.MessageType;


@Node(
        type = ComponentType.INPUT,
        name = "REST Controller Node", relationTypes = {"Connected", "Disconnected"},
        configClazz = RestControllerNodeConfiguration.class,
        nodeDescription = "",
        nodeDetails =
                "Details - Input Node that exposes an endpoint to insert data to")
public class RestControllerNode extends SimpleNode<RestControllerNodeConfiguration> {

    private Gson gson;

    public RestControllerNode() {
        this.gson = new Gson();
    }


    @Override
    public void init(RestControllerNodeConfiguration configuration) {

    }

    @Override
    public void onMessage(Message message) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isSourceNode() {
        return true;
    }

    public void insertData(Object data){
        if(data instanceof String){
            this.next(new Message("staticId",(String) data, MessageType.JSON, new MessageMetaData()));
        } else {
            this.next(new Message("staticId",gson.toJson(data), MessageType.JSON, new MessageMetaData()));
        }

    }
}
