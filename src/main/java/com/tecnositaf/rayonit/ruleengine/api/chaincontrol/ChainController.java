package com.tecnositaf.rayonit.ruleengine.api.chaincontrol;

import com.tecnositaf.rayonit.ruleengine.api.chaincontrol.service.ManagerService;
import com.tecnositaf.rayonit.ruleengine.api.commons.Response;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rule/control")
public class ChainController {

    private final ManagerService managerService;

    public ChainController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping("/start")
    public Response<Boolean> initChainManager() {
        return managerService.initChainManager();
    }

    @PostMapping("/stop")
    public Response<Boolean> stopChainManager() {
        return managerService.stopChainManager();
    }

    @PostMapping("/start/{chainId}")
    public Response<Boolean> startChainById(@PathVariable String chainId) {
        System.out.println(chainId);
        return managerService.startChainById(chainId);
    }

    @PostMapping("/stop/{chainId}")
    public Response<Boolean> stopChainById(@PathVariable String chainId) {
        System.out.println(chainId);
        return managerService.stopChainById(chainId);
    }

    @PostMapping("/restart/{chainId}")
    public Response<Boolean> restartChain(@PathVariable String chainId) {
        System.out.println(chainId);
        return managerService.restartChainById(chainId);
    }


}
