package com.skerdy.ruleengine.api.chaincontrol.service;

import com.skerdy.ruleengine.api.commons.Response;

public interface ManagerService {

    public Response<Boolean> initChainManager();

    public Response<Boolean> stopChainManager();

    public Response<Boolean> startChainById(String chainId);

    public Response<Boolean> stopChainById(String chainId);

    public Response<Boolean> restartChainById(String chainId);

}
