package com.tecnositaf.rayonit.ruleengine.core.manager;

import com.tecnositaf.rayonit.ruleengine.api.persistance.repository.ChainRepository;
import com.tecnositaf.rayonit.ruleengine.api.persistance.service.ChainServiceImpl;
import com.tecnositaf.rayonit.ruleengine.api.persistance.service.OnChainAdded;
import com.tecnositaf.rayonit.ruleengine.core.chain.RuleChain;
import com.tecnositaf.rayonit.ruleengine.core.exception.InvalidNodeConnectionException;
import com.tecnositaf.rayonit.ruleengine.core.node.configuration.NodeConfiguration;
import com.tecnositaf.rayonit.ruleengine.core.node.simple.SimpleNode;
import com.tecnositaf.rayonit.ruleengine.jsengine.JavaScriptEngine;
import com.tecnositaf.rayonit.ruleengine.nodetype.input.rest.RestControllerNode;
import com.tecnositaf.rayonit.ruleengine.api.persistance.model.ChainDocument;
import com.tecnositaf.rayonit.ruleengine.api.persistance.model.NodeDocument;
import com.tecnositaf.rayonit.ruleengine.api.persistance.service.ChainService;

import java.util.ArrayList;
import java.util.List;

public class ChainManager implements OnChainAdded {

    private ReflectNodeCreator nodeCreator;

    private List<RuleChain> chains;

    private ChainServiceImpl chainService;

    private List<ChainDocument> chainsMetadata;

    private ChainRepository chainRepository;

    public ChainManager(JavaScriptEngine engine, ChainRepository chainRepository) {
        this.nodeCreator = new ReflectNodeCreator(engine);
        this.chainService = new ChainServiceImpl(chainRepository, this);
        this.chains = new ArrayList<>();
    }

    public List<RuleChain> getChains() {
        return chains;
    }

    public void setChains(List<RuleChain> chains) {
        this.chains = chains;
    }

    public void init() throws InvalidNodeConnectionException {
      List<ChainDocument> chainDocuments = chainService.getAllChains();
      this.chainsMetadata = chainDocuments;
      for(ChainDocument chainDocument : chainDocuments){
        this.chains.add(createChainFromDocument(chainDocument));
      }
    }

    public boolean startChain(String chainId){
        RuleChain ruleChain = findRuleChainById(chainId);
        if(ruleChain!=null){
            ruleChain.start();
            ruleChain.setStarted(true);
            return true;
        }
        return false;
    }

    public void stopChain(String chainId){
        RuleChain ruleChain = findRuleChainById(chainId);
        if(ruleChain!=null){
            ruleChain.stop();
            ruleChain.setStarted(false);
        }
    }

    public boolean dispatchData(String path, Object data){
        boolean flag = true;
        List<SimpleNode<? extends NodeConfiguration>> eligibleNodes = getAllMatchingRestSources(path);
        if(eligibleNodes.size() !=0){
            for(SimpleNode<? extends NodeConfiguration> node : eligibleNodes){
                ((RestControllerNode)node).insertData(data);
            }
        } else {
            flag = false;
        }
        return flag;
    }

    private List<SimpleNode<? extends NodeConfiguration>> getAllMatchingRestSources(String path){
        List<SimpleNode<? extends NodeConfiguration>> result = new ArrayList<>();
        for(RuleChain ruleChain : this.chains){
            if(ruleChain.isStarted()){
                ChainDocument document = findChainDocumentById(ruleChain.getId());
                if(document!=null && document.getNodeDocuments()!=null && document.getNodeDocuments().size()!=0){
                    NodeDocument nodeDocument = document.getNodeDocuments().get(0);
                    Object endpoint = nodeDocument.getConfigurationProperty("endpoint");
                    if( endpoint != null && nodeDocument.getConfigurationProperty("endpoint").toString().equals(path)){
                        result.add(ruleChain.getSourceNode());
                    }
                }
            }
        }
        return result;
    }

    private ChainDocument findChainDocumentById(String id){
        for(ChainDocument chainDocument : this.chainsMetadata){
            if(chainDocument.getId().equals(id)){
                return chainDocument;
            }
        }
        return null;
    }

    private RuleChain createChainFromDocument(ChainDocument chainDocument) throws InvalidNodeConnectionException {
        List<SimpleNode<? extends NodeConfiguration>> chainNodes = new ArrayList<>();
        for(NodeDocument nodeDocument : chainDocument.getNodeDocuments()){
            chainNodes.add(createNodeFromDocument(nodeDocument));
        }
        RuleChain ruleChain = new RuleChain(chainNodes);
        ruleChain.setNodeConnectionInfoList(chainDocument.getNodeConnectionInfos());
        ruleChain.setId(chainDocument.getId());
        if (ruleChain.isConnectionValid()) {
            if (chainDocument.isStarted()) {
                ruleChain.start();
            }
        } else {
            throw new InvalidNodeConnectionException();
        }
        return ruleChain;
    }

    private SimpleNode<? extends NodeConfiguration> createNodeFromDocument(NodeDocument nodeDocument){
        return nodeCreator.create(nodeDocument);
    }

    private RuleChain findRuleChainById(String id){
        for(RuleChain ruleChain: this.chains){
            if(ruleChain.getId().equals(id)){
                return ruleChain;
            }
        }
        return null;
    }

    @Override
    public void onAdd(ChainDocument document) {
        System.out.println(" Add logic for creating of chain " + document.getId());
        try {
            this.chains.add(createChainFromDocument(document));
        } catch (InvalidNodeConnectionException e) {
            e.printStackTrace();
        }
    }
}
