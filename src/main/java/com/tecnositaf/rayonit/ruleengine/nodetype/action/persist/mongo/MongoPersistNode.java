package com.tecnositaf.rayonit.ruleengine.nodetype.action.persist.mongo;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.tecnositaf.rayonit.ruleengine.core.node.ComponentType;
import com.tecnositaf.rayonit.ruleengine.core.node.Node;
import com.tecnositaf.rayonit.ruleengine.core.node.simple.SimpleNode;
import com.tecnositaf.rayonit.ruleengine.message.Message;
import com.tecnositaf.rayonit.ruleengine.nodetype.action.persist.mongo.core.DataPersist;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Node(
        type = ComponentType.ACTION,
        name = "Mongo Persist Node", relationTypes = {"Connected", "Disconnected"},
        configClazz = MongoPersistNodeConfiguration.class,
        nodeDescription = "persists data",
        nodeDetails =
                "Details - Action Node that sends an email based on inputed configuration")
public class MongoPersistNode extends SimpleNode<MongoPersistNodeConfiguration> {

    private ReactiveMongoTemplate mongoTemplate;
    private DataPersist dataPersist;

    @Override
    public void init(MongoPersistNodeConfiguration configuration) {
        MongoClient client = MongoClients.create(configuration.getDatabaseConnectionString());
        this.mongoTemplate = new ReactiveMongoTemplate(client, configuration.getDatabase());
        this.dataPersist = new DataPersist(configuration, this.mongoTemplate);
    }

    @Override
    public void onMessage(Message message) {
      if(this.dataPersist != null){
          this.dataPersist.addMessageToPersist(message);
      }
    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean isSourceNode() {
        return false;
    }

}
