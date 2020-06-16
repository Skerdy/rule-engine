package com.skerdy.ruleengine.nodetype.action.persist.mongo.core;

public enum MongoOperation {
    INSERT("insert"),
    UPDATE("update");

    private final String operation;

    MongoOperation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
