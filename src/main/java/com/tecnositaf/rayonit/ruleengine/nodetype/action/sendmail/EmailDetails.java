package com.tecnositaf.rayonit.ruleengine.nodetype.action.sendmail;

import com.tecnositaf.rayonit.ruleengine.core.annotation.ConfigurationField;
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
