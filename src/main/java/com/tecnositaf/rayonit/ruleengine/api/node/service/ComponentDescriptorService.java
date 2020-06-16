package com.tecnositaf.rayonit.ruleengine.api.node.service;

import com.tecnositaf.rayonit.ruleengine.api.node.model.ComponentDescriptor;

import java.util.List;

public interface ComponentDescriptorService {

    ComponentDescriptor save(ComponentDescriptor componentDescriptor);

    ComponentDescriptor edit(ComponentDescriptor componentDescriptor);

    List<ComponentDescriptor> getAll();

    ComponentDescriptor findById(String id);

    ComponentDescriptor findByClass(String clazz);

    void deleteByClass(String clazz);

    List<ComponentDescriptor> findAllByClass(String clazz);

}
