package com.tecnositaf.rayonit.ruleengine.nodetype.input.mongo;

import com.mongodb.ConnectionString;
import com.tecnositaf.rayonit.ruleengine.core.annotation.ConfigurationField;
import com.tecnositaf.rayonit.ruleengine.core.node.configuration.NodeConfiguration;
import org.json.simple.JSONObject;
import org.springframework.data.mongodb.core.query.Query;

public class MongoTailerNodeConfiguration implements NodeConfiguration<MongoTailerNodeConfiguration> {

    @ConfigurationField(required = true)
    private String host;

    @ConfigurationField(required = true)
    private String port;

    @ConfigurationField(required = true)
    private String database;

    @ConfigurationField(required = true)
    private String collection;

    @ConfigurationField(required = false)
    private String filter;

    @ConfigurationField(required = true)
    private String clazzOfDocument;

    private Query filterQuery;
    private Class<?> documentClass;

    public MongoTailerNodeConfiguration() {
    }

    public MongoTailerNodeConfiguration(JSONObject data) {
        String host = data.get("host").toString();
        if(host!=null){
            this.host = host;
        }

        String port = data.get("port").toString();
        if(port!=null){
            this.port = port;
        }


        String database = data.get("database").toString();
        if(database!=null){
            this.database = database;
        }

        String clazz = data.get("clazzOfDocument").toString();
        if(clazz !=null){
            this.clazzOfDocument = clazz;
        }

    }

    @Override
    public MongoTailerNodeConfiguration defaultConfiguration() {
        return null;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setClazzOfDocument(String clazzOfDocument) {
        this.clazzOfDocument = clazzOfDocument;
    }

    public ConnectionString getDatabaseConnectionString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mongodb://").append(host).append(":").append(port).append("/").append(database);
        return new ConnectionString(stringBuilder.toString());
    }

    public Query getFilterQuery(){
        if(this.filterQuery == null){
           generateQuery();
        }
        return filterQuery;
    }

    public Class<?> getDocumentClass(){
        if(this.documentClass == null){
            generateDocumentClass();
        }
        return this.documentClass;
    }

    private void generateDocumentClass(){
        try {
            this.documentClass = Class.forName(this.clazzOfDocument);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void generateQuery(){
       this.filterQuery = new Query();
    }

    public String getDatabase() {
        return database;
    }
}
