package com.skerdy.ruleengine.api.chaincontrol.service;

import com.skerdy.ruleengine.api.commons.Response;
import com.skerdy.ruleengine.core.manager.ChainManager;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImpl implements ManagerService {

    private final ChainManager chainManager;

    public ManagerServiceImpl(ChainManager chainManager) {
        this.chainManager = chainManager;
    }

    @Override
    public Response<Boolean> initChainManager() {
        return null;
    }

    @Override
    public Response<Boolean> stopChainManager() {
        return null;
    }

    @Override
    public Response<Boolean> startChainById(String chainId) {
        if(chainManager.startChain(chainId)){
            return new Response<>("Ok", "Chain started successfully", true);
        } else {
            return new Response<>("Failure", "Chain does not exist. Please make sure to provide a valid chain!", true);
        }
    }

    @Override
    public Response<Boolean> stopChainById(String chainId) {
        return null;
    }

    @Override
    public Response<Boolean> restartChainById(String chainId) {
        return null;
    }
}
