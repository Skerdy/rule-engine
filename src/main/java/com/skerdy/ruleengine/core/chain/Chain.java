package com.skerdy.ruleengine.core.chain;

public interface Chain {

    void start();

    void stop();

    void restart();

    boolean processConnectionInfo();

}
