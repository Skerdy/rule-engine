package com.skerdy.ruleengine.api.persistance.model;

import com.skerdy.ruleengine.api.commons.BaseNodeDocument;
import lombok.*;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NodeDocument extends BaseNodeDocument {

    @Id
    private String id;

    private JSONObject configurationProperties;

    private JSONObject nodeLayout;

    public String getId() {
        return id;
    }

    public Object getConfigurationProperty(String key){
        return configurationProperties.get(key);
    }
}
