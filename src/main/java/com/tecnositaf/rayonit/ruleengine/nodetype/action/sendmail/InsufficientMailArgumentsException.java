package com.tecnositaf.rayonit.ruleengine.nodetype.action.sendmail;

import org.springframework.http.HttpStatus;

public class InsufficientMailArgumentsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String message;

    public InsufficientMailArgumentsException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
