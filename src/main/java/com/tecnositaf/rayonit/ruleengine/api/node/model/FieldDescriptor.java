package com.tecnositaf.rayonit.ruleengine.api.node.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FieldDescriptor {

    private String name;
    private String type;
    private boolean required;
    private List<FieldDescriptor> children;
    private List<String> typeParamenters;

    public void addFielDescriptor(FieldDescriptor fieldDescriptor){
        if(this.children==null){
            this.children = new ArrayList<>();
        }
        this.children.add(fieldDescriptor);
    }

    public void addTypeParameter(String typeParam){
        if(this.typeParamenters ==null){
            this.typeParamenters = new ArrayList<>();
        }
        this.typeParamenters.add(typeParam);
    }

}
