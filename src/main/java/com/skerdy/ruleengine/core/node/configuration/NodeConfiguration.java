package com.skerdy.ruleengine.core.node.configuration;

public interface NodeConfiguration<T extends NodeConfiguration> {

    T defaultConfiguration();

}
