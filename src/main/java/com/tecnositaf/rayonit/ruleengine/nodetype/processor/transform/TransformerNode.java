package com.tecnositaf.rayonit.ruleengine.nodetype.processor.transform;

import com.tecnositaf.rayonit.ruleengine.core.node.ComponentType;
import com.tecnositaf.rayonit.ruleengine.core.node.Node;
import com.tecnositaf.rayonit.ruleengine.core.node.duplex.DuplexNode;
import com.tecnositaf.rayonit.ruleengine.message.Message;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.XML;
import org.json.simple.parser.ParseException;

@Node( type = ComponentType.FILTER,
        name = "Transformer Node", relationTypes = {"Connected", "Disconnected"},
        configClazz = TransformerNodeConfiguration.class,
        nodeDescription = "Transformer Node",
        nodeDetails =
                "Details - Transforms Node")
public class TransformerNode  extends DuplexNode<TransformerNodeConfiguration> {

    @Override
    public Message processMessage(Message message) {

        JSONObject jsonObject = null;
        JSONParser jsonParser = new JSONParser();
        String data = message.getPayload();
        String xmlJSONObj = XML.toJSONObject(data).toString();
        try {
             jsonObject = (JSONObject) jsonParser.parse(xmlJSONObj);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (jsonObject != null){
             message.setPayload(jsonObject.toJSONString());
        }
        System.out.println(message);
        return message;
    }

    @Override
    public void init(TransformerNodeConfiguration configuration) {

    }

    @Override
    public void onMessage(Message message) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean isSourceNode() {
        return false;
    }
}
