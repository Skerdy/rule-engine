package com.skerdy.ruleengine.nodetype.input.rest;

import com.skerdy.ruleengine.core.manager.ChainManager;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneralSourceController {

    private final ChainManager chainManager;

    public GeneralSourceController(ChainManager chainManager) {
        this.chainManager = chainManager;
    }

    @PostMapping(value = "{path}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void handleRequest(@PathVariable String path, @RequestBody Object body){
        this.chainManager.dispatchData(path, body);
    }

    @PostMapping(value = "{path}", consumes = MediaType.TEXT_PLAIN_VALUE)
    public void handleTextRequest(@PathVariable String path, @RequestBody String body){
        this.chainManager.dispatchData(path, body);
    }

}
