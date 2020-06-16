package com.tecnositaf.rayonit.ruleengine.api.node.model;

import com.tecnositaf.rayonit.ruleengine.api.commons.BaseNodeDocument;
import com.tecnositaf.rayonit.ruleengine.core.node.ComponentType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document
@Data
@Getter
@Setter
public class ComponentDescriptor extends BaseNodeDocument {

    @Id
    private String id;
    private ComponentType type;
    private JSONObject configurationDescriptor;
    private String actions;


    public ComponentDescriptor() {
    }
}
