package com.skerdy.ruleengine.configuration;

import com.skerdy.ruleengine.api.persistance.repository.ChainRepository;
import com.skerdy.ruleengine.jsengine.JavaScriptEngine;
import com.skerdy.ruleengine.core.manager.ChainManager;
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
