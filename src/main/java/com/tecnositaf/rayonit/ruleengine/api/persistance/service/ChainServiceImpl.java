package com.tecnositaf.rayonit.ruleengine.api.persistance.service;

import com.tecnositaf.rayonit.ruleengine.api.persistance.model.ChainDocument;
import com.tecnositaf.rayonit.ruleengine.api.persistance.repository.ChainRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChainServiceImpl implements ChainService {

    private final ChainRepository repository;

    private final OnChainAdded onChainAdded;

    public ChainServiceImpl(ChainRepository repository, OnChainAdded onChainAdded) {
        this.repository = repository;
        this.onChainAdded = onChainAdded;
    }

    @Override
    public List<ChainDocument> getAllChains() {
        return repository.findAll();
    }

    @Override
    public Optional<ChainDocument> getById(String id) {
        return repository.getById(id);
    }

    @Override
    public ChainDocument findChainById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public ChainDocument insert(ChainDocument document) {
        ChainDocument result = repository.save(document);
        onChainAdded.onAdd(result);
        return result;
    }

    @Override
    public Optional<ChainDocument> update(ChainDocument document) {
        return repository
                .getById(document.getId())
                .map(e -> document)
                .map(repository::save);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
