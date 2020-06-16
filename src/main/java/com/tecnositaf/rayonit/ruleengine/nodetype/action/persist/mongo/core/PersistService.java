package com.tecnositaf.rayonit.ruleengine.nodetype.action.persist.mongo.core;

import com.mongodb.BasicDBObject;

public interface PersistService {

    void updateNext(BasicDBObject data, String collectionName);

    void insertNext(BasicDBObject data, String collectionName);
}
