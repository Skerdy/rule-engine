package com.skerdy.ruleengine.api.persistance.controller;

import com.skerdy.ruleengine.api.persistance.model.ChainDocument;
import com.skerdy.ruleengine.api.persistance.service.ChainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rules")
public class RuleController {

    private final ChainService chainService;

    public RuleController(ChainService chainService) {
        this.chainService = chainService;
    }

    @GetMapping
    public List<ChainDocument> getAll() {
        return chainService.getAllChains();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChainDocument> getById(@PathVariable("id") String id) {
        return chainService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ChainDocument insert(@RequestBody ChainDocument document) {
        return chainService.insert(document);
    }

    @PutMapping
    public ResponseEntity<ChainDocument> update(@RequestBody ChainDocument document) {
        return chainService.update(document)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        chainService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
