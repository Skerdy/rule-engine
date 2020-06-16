package com.tecnositaf.rayonit.ruleengine.nodetype.processor.transform;

public enum TransformType {

    XML_TO_JSON("XmlToJson");

    private final String type;

    TransformType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
