package com.skerdy.ruleengine.message;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@NoArgsConstructor
public final class MessageMetaData implements Serializable {

    private final Map<String, String> data = new ConcurrentHashMap<>();

    public MessageMetaData(Map<String, String> data) {
        data.forEach(this::putValue);
    }

    public String getValue(String key) {
        return data.get(key);
    }

    public void putValue(String key, String value) {
        if (key != null && value != null) {
            data.put(key, value);
        }
    }

    public Map<String, String> values() {
        return new HashMap<>(data);
    }

    public void addValue(String key, String value){
        this.data.put(key,value);
    }

    public MessageMetaData copy() {
        return new MessageMetaData(new ConcurrentHashMap<>(data));
    }

    public void setOutputResult(String value){
        data.put(MetaDataKeyVal.OUTPUT_RESULT, value);
    }
}
