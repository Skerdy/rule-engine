package com.tecnositaf.rayonit.ruleengine.api.node.repository;

import com.tecnositaf.rayonit.ruleengine.api.node.model.ComponentDescriptor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentDescriptorRepository extends MongoRepository<ComponentDescriptor, String> {

    List<ComponentDescriptor> findAllByClazz(String clazz);

    void deleteByClazz(String clazz);

    ComponentDescriptor findByClazz(String clazz);
}
