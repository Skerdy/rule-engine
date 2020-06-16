package com.tecnositaf.rayonit.ruleengine.configuration;

import com.tecnositaf.rayonit.ruleengine.api.persistance.repository.ChainRepository;
import com.tecnositaf.rayonit.ruleengine.core.manager.ChainManager;
import com.tecnositaf.rayonit.ruleengine.jsengine.JavaScriptEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChainManagerConfiguration {

    private final ChainRepository chainRepository;

    public ChainManagerConfiguration(ChainRepository chainRepository) {
        this.chainRepository = chainRepository;
    }


    @Bean
    public JavaScriptEngine getJavaScriptEngine(){
        return new JavaScriptEngine();
    }

    @Bean
    public ChainManager getSingletonManager(){
        return new ChainManager(getJavaScriptEngine(), chainRepository);
    }

}
