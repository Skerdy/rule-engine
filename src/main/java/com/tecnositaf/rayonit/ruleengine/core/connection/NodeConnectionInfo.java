
package com.tecnositaf.rayonit.ruleengine.core.connection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeConnectionInfo {
    private int fromIndex;
    private int toIndex;
    private String predicate;
}
