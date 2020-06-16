package com.tecnositaf.rayonit.ruleengine.nodetype.action.persist.mongo.core;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.tecnositaf.rayonit.ruleengine.message.Message;
import com.tecnositaf.rayonit.ruleengine.nodetype.action.persist.mongo.MongoPersistNodeConfiguration;
import org.json.simple.JSONObject;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataPersist extends SimpleDataPersist<Message> {

    private final MongoPersistNodeConfiguration configuration;
    private Gson gson;
    private EmitterProcessor<DataHolder> insertFlux;
    private EmitterProcessor<DataHolder> updateFlux;
    private final ReactiveMongoTemplate template;

    public DataPersist(MongoPersistNodeConfiguration configuration, ReactiveMongoTemplate template) {
        this.configuration = configuration;
        this.template = template;
        this.gson = new Gson();
        this.insertFlux = EmitterProcessor.create();
        this.updateFlux = EmitterProcessor.create();
        this.insertFlux.buffer(Duration.ofMillis(configuration.getInsertBufferDuration())).doOnNext(this::insertAll).subscribe();
    }


    @Override
    public void addMessageToPersist(Message message) {
        // TODO add logic for multiple collections
        int i =0;
        for(String collection : configuration.getCollections()){
            persist(message, configuration.getMongoOperation(), collection);
        }
    }

    @Override
    void persist(Message message, MongoOperation mongoOperation, String collectionName) {
        JSONObject object = gson.fromJson(message.getPayload(), JSONObject.class);
        // insert operation
        if (mongoOperation.getOperation().equals(MongoOperation.INSERT.getOperation())) {
            // chekcs if the insertDate String in the properties is not empty. If so adds a field with the given key and the instant date
            if (configuration.isAppendInsertDate() && object != null) {
                object.put(configuration.getInsertDateKey(), new Date());
            }
             insertNext(new BasicDBObject(object), collectionName);
        } else {
             updateNext(new BasicDBObject(object), collectionName);
        }
    }

    @Override
    public void updateNext(BasicDBObject data, String collectionName) {
        this.updateFlux.onNext(new DataHolder(collectionName, data));
    }

    @Override
    public void insertNext(BasicDBObject data, String collectionName) {
        this.insertFlux.onNext(new DataHolder(collectionName, data));
    }

    private void insertAll(List<DataHolder> documents){
        for(String collection : getAllBufferedCollections(documents)){
            this.template.insertAll(getDocumentsForCollection(collection, documents), collection).subscribe();
        }
    }

    private Mono<List<BasicDBObject>> getDocumentsForCollection(String collection, List<DataHolder> documents){
        List<BasicDBObject> filteredDocuments = new ArrayList<>();
        documents.stream().forEach(dataHolder -> {
            if(dataHolder.getCollectionName().equals(collection)){
                filteredDocuments.add(dataHolder.getData());
            }
        });
        return Mono.just(filteredDocuments);
    }

    private List<String> getAllBufferedCollections(List<DataHolder> documents){
        List<String> result = new ArrayList<>();
        documents.stream().forEach(dataHolder -> {
            if(!checkIfCollectionIsCollected(dataHolder.getCollectionName(), result)){
                result.add(dataHolder.getCollectionName());
            }
        });
        return result;
    }

    private boolean checkIfCollectionIsCollected(String collectionName, List<String> bufferedCollections){
        for(String bufferedCollection : bufferedCollections){
            if(bufferedCollection.equals(collectionName)){
                return true;
            }
        }
        return false;
    }

}
