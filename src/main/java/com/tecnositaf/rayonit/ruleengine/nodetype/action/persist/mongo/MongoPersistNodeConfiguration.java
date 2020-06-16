package com.tecnositaf.rayonit.ruleengine.nodetype.action.persist.mongo;

import com.mongodb.ConnectionString;
import com.tecnositaf.rayonit.ruleengine.core.annotation.ConfigurationField;
import com.tecnositaf.rayonit.ruleengine.core.node.configuration.NodeConfiguration;
import com.tecnositaf.rayonit.ruleengine.nodetype.action.persist.mongo.core.MongoOperation;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MongoPersistNodeConfiguration implements NodeConfiguration<MongoPersistNodeConfiguration> {

    private final String DEFAULT_INSERT_DATE_KEY = "insertDate";
    private final Long DEFAULT_INSERT_BUFFER_DURATION = 1000L;

    @ConfigurationField(required = true)
    private String host;
    @ConfigurationField(required = true)
    private String port;
    @ConfigurationField(required = true)
    private String database;

    @ConfigurationField(required = true)
    private String mongoOperation;

    @ConfigurationField(required = true, typeParameters = String.class)
    private List<String> collections;

    @ConfigurationField(required = false)
    private Long insertBufferDuration = DEFAULT_INSERT_BUFFER_DURATION;

    @ConfigurationField(required = true)
    private boolean appendInsertDate = true;
    @ConfigurationField(required = true)
    private String insertDateKey = DEFAULT_INSERT_DATE_KEY;

    public MongoPersistNodeConfiguration() {
    }

    public MongoPersistNodeConfiguration(JSONObject config) {
        this.collections = new ArrayList<>();
        Object host = config.get("host");
        if (host != null) {
            this.host = host.toString();
        }

        Object port = config.get("port");
        if (port != null) {
            this.port = port.toString();
        }

        Object database = config.get("database");
        if (database != null) {
            this.database = database.toString();
        }

        Object collections = config.get("collections");
        if (collections instanceof ArrayList) {
            this.collections = (ArrayList) collections;
        }

        Object mongoOperation = config.get("mongoOperation");
        if (mongoOperation != null) {
            this.mongoOperation = mongoOperation.toString();
        }

    }

    @Override
    public MongoPersistNodeConfiguration defaultConfiguration() {
        return new MongoPersistNodeConfiguration();
    }

    public ConnectionString getDatabaseConnectionString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mongodb://").append(host).append(":").append(port).append("/").append(database);
        return new ConnectionString(stringBuilder.toString());
    }

    public boolean isAppendInsertDate() {
        return appendInsertDate;
    }

    public void setAppendInsertDate(boolean appendInsertDate) {
        this.appendInsertDate = appendInsertDate;
    }

    public String getInsertDateKey() {
        return insertDateKey;
    }

    public void setInsertDateKey(String insertDateKey) {
        this.insertDateKey = insertDateKey;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public List<String> getCollections() {
        return collections;
    }


    public Long getInsertBufferDuration() {
        return insertBufferDuration;
    }

    public void setInsertBufferDuration(Long insertBufferDuration) {
        this.insertBufferDuration = insertBufferDuration;
    }

    public MongoOperation getMongoOperation() {
        if (mongoOperation.equals(MongoOperation.INSERT.getOperation())) {
            return MongoOperation.INSERT;
        }
        return MongoOperation.UPDATE;
    }
}
