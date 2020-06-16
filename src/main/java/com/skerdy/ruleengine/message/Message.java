package com.skerdy.ruleengine.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements MessageDefinition {

    private String id;

    private String payload;

    private MessageType type;

    private MessageMetaData metaData;

    private void ensureMetaDataNotNull() {
        if (this.metaData == null) {
            this.metaData = new MessageMetaData();
        }
    }

    @Override
    public String getOriginator() {
        String result = this.metaData.getValue(MetaDataKeyVal.ORIGINATOR);
        if (result == null || result.length() == 0) {
            return MetaDataKeyVal.VALUE_NOT_PRESENT;
        }
        return result;
    }

    @Override
    public void setOriginator(String originator) {
        ensureMetaDataNotNull();
        if (originator != null && !originator.isEmpty())
        this.metaData.addValue(MetaDataKeyVal.ORIGINATOR, originator);
    }

    @Override
    public String getRuleChainId() {
        String result = this.metaData.getValue(MetaDataKeyVal.RULE_CHAIN_ID);
        if (result == null || result.length() == 0) {
            return MetaDataKeyVal.VALUE_NOT_PRESENT;
        }
        return result;
    }

    @Override
    public void setRuleChainId(String ruleChainId) {
        ensureMetaDataNotNull();
        if (ruleChainId != null && !ruleChainId.isEmpty())
        this.metaData.addValue(MetaDataKeyVal.RULE_CHAIN_ID, ruleChainId);
    }

    @Override
    public String getNodeId() {
        String result = this.metaData.getValue(MetaDataKeyVal.NODE_ID);
        if (result == null || result.length() == 0) {
            return MetaDataKeyVal.VALUE_NOT_PRESENT;
        }
        return result;
    }

    @Override
    public void setNodeId(String nodeId) {
        ensureMetaDataNotNull();
        if (nodeId != null && !nodeId.isEmpty())
        this.metaData.addValue(MetaDataKeyVal.NODE_ID, nodeId);
    }

    public void addMetaData(String key, String value){
        ensureMetaDataNotNull();
        if(key!=null && value !=null && !key.isEmpty() && !value.isEmpty()){
            this.metaData.addValue(key,value);
        }
    }

    public void setOutputResult(String outputResult){
        this.metaData.setOutputResult(outputResult);
    }

    public String getOutputResult(){
        String result = this.metaData.getValue(MetaDataKeyVal.OUTPUT_RESULT);
        if (result == null || result.length() == 0) {
            return MetaDataKeyVal.VALUE_NOT_PRESENT;
        }
        return result;
    }
}
