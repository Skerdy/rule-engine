package com.skerdy.ruleengine.nodetype.action.persist.mongo.core;

import com.mongodb.BasicDBObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataHolder {
    private String collectionName;
    private BasicDBObject data;
}
