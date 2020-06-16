package com.skerdy.ruleengine.nodetype.action.persist.mongo.core;

public abstract class SimpleDataPersist<T> implements PersistService {

    abstract void addMessageToPersist(T t);

    abstract void persist(T t, MongoOperation mongoOperation, String collectionName);

    protected MongoOperation getMongoOperation(String operation) {
        if (operation.equals(MongoOperation.INSERT.getOperation())) {
            return MongoOperation.INSERT;
        } else if (operation.equals(MongoOperation.UPDATE.getOperation())) {
            return MongoOperation.UPDATE;
        }
        return MongoOperation.INSERT;
    }


}
