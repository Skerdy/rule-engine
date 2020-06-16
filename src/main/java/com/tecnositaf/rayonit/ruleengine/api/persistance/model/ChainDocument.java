package com.tecnositaf.rayonit.ruleengine.api.persistance.model;

import com.tecnositaf.rayonit.ruleengine.core.connection.NodeConnectionInfo;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChainDocument {

    @Id
    private String id;
    private String name;
    private String description;
    private List<NodeDocument> nodeDocuments;
    private List<NodeConnectionInfo> nodeConnectionInfos;
    private boolean started;

}
