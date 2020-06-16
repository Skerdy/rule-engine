package com.skerdy.ruleengine.nodetype.action.sendmail;

import com.skerdy.ruleengine.core.annotation.ConfigurationField;
import lombok.Data;

@Data
public class EmailDetails {

    @ConfigurationField(required = true)
    private String to;
    @ConfigurationField(required = true)
    private String subject;
    @ConfigurationField(required = true)
    private String text;

}
