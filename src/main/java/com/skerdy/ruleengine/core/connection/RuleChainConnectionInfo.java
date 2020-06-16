
package com.skerdy.ruleengine.core.connection;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class RuleChainConnectionInfo {
    private int fromIndex;
    private String targetRuleChainId;
    private JsonNode additionalInfo;
    private String type;
}
