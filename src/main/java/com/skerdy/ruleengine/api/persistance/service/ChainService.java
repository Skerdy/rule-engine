package com.skerdy.ruleengine.api.persistance.service;

import com.skerdy.ruleengine.api.persistance.model.ChainDocument;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ChainService {

    List<ChainDocument> getAllChains();

    Optional<ChainDocument> getById(String id);

    ChainDocument findChainById(String id);

    ChainDocument insert(ChainDocument document);

    Optional<ChainDocument> update(ChainDocument document);

    void deleteById(String id);

}
