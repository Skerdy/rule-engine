package com.skerdy.ruleengine.api.persistance.repository;

import com.skerdy.ruleengine.api.persistance.model.ChainDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChainRepository extends MongoRepository<ChainDocument, String> {

    Optional<ChainDocument> getById(String id);

}
