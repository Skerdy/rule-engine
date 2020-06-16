package com.skerdy.ruleengine.nodetype.input.mongo;

import com.google.gson.Gson;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.skerdy.ruleengine.core.node.ComponentType;
import com.skerdy.ruleengine.core.node.Node;
import com.skerdy.ruleengine.core.node.simple.SimpleNode;
import com.skerdy.ruleengine.message.Message;
import com.skerdy.ruleengine.message.MessageMetaData;
import com.skerdy.ruleengine.message.MessageType;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Node(
        type = ComponentType.INPUT,
        name = "Mongo Tailer Node", relationTypes = {"Connected", "Disconnected"},
        configClazz = MongoTailerNodeConfiguration.class,
        nodeDescription = "Clear Alarm",
        nodeDetails =
                "Details - Input Node that tails a capped collection in a mongo database")
public class MongoTailerNode extends SimpleNode<MongoTailerNodeConfiguration> {

    private ReactiveMongoTemplate mongoTemplate;
    private final Gson gson = new Gson();

    public MongoTailerNode() {
        this.setId("SOURCE_ID");
    }

    @Override
    public void init(MongoTailerNodeConfiguration configuration) {
        MongoClient client = MongoClients.create(configuration.getDatabaseConnectionString());
        this.mongoTemplate = new ReactiveMongoTemplate(client,configuration.getDatabase());
        this.mongoTemplate.tail(configuration.getFilterQuery(), configuration.getDocumentClass()).doOnNext(document->{
            Message message = new Message("1",gson.toJson(document), MessageType.JSON, new MessageMetaData());
            this.next(message);
        }).doOnError(Throwable::printStackTrace).subscribe();

    }

    @Override
    public void onMessage(Message message) {
        System.out.println("MONGO TAILER NODE => " + " ON MESSAGE => " + message.toString());
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


}
