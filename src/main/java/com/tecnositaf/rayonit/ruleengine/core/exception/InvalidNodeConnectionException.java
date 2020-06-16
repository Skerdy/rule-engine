package com.tecnositaf.rayonit.ruleengine.core.exception;

public class InvalidNodeConnectionException extends Exception {

    public InvalidNodeConnectionException() {
        super("Please check your connection info. The node connections form a cycle which is not allowed.");
    }
}
