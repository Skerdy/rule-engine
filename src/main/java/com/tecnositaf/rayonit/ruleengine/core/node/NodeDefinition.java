
package com.tecnositaf.rayonit.ruleengine.core.node;

import lombok.*;
import org.json.simple.JSONObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NodeDefinition {

    private String details;
    private String description;
    private boolean inEnabled;
    private boolean outEnabled;
    JSONObject defaultConfiguration;

}
