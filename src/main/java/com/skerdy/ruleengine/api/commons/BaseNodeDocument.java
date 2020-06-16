package com.skerdy.ruleengine.api.commons;

import com.skerdy.ruleengine.api.node.model.FieldDescriptor;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseNodeDocument {

    @Getter @Setter private String name;
    @Getter @Setter private String clazz;
    @Getter @Setter private String configurationClazz;
    @Getter @Setter private List<FieldDescriptor> configurationFields;

}
