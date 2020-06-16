package com.skerdy.ruleengine.nodetype.action.sendsms;

import com.skerdy.ruleengine.core.annotation.ConfigurationField;
import com.skerdy.ruleengine.core.node.configuration.NodeConfiguration;
import com.skerdy.ruleengine.nodetype.action.sendmail.InsufficientMailArgumentsException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.json.simple.JSONObject;

@Builder
@Data
@AllArgsConstructor
public class SmsSenderNodeConfiguration implements NodeConfiguration<SmsSenderNodeConfiguration> {

    private static final String ACCOUNT_SID = "AC1aa4db666585530fc5b61ae525bfc1b2";
    private static final String AUTH_TOKEN= "eb699e9168dfe046a7e753b3888ff196";
    private static final String FROM_PHONE = "+18084005863";

    @ConfigurationField(required = true)
    private String sid;
    @ConfigurationField(required = true)
    private String authToken;
    @ConfigurationField(required = true)
    private String toPhone;
    @ConfigurationField(required = true)
    private String fromPhone;
    @ConfigurationField(required = true)
    private String body;


    public SmsSenderNodeConfiguration(JSONObject config) {

        Object sid = config.get("sid");
        if (sid != null)
            this.sid = sid.toString();
        else
            throw new InsufficientMailArgumentsException("Please provide your twilio SID");
        Object authToken = config.get("authToken");
        if (authToken != null)
            this.authToken = authToken.toString();
        else
            throw new InsufficientMailArgumentsException("Please provide username and password of your email");
        Object toPhone = config.get("toPhone");
        if (toPhone != null)
            this.toPhone = toPhone.toString();
        else
            throw new InsufficientMailArgumentsException("Please provide the receiver phone number");
        Object fromPhone = config.get("fromPhone");
        if (fromPhone != null)
            this.fromPhone = fromPhone.toString();
        else
            throw new InsufficientMailArgumentsException("Please provide your phone number");
        Object body = config.get("body");
        if (body != null)
            this.body = body.toString();
        else
            throw new InsufficientMailArgumentsException("Please provide a body as a text message");

    }

    public SmsSenderNodeConfiguration() {
    }

    @Override
    public SmsSenderNodeConfiguration defaultConfiguration() {
        return new SmsSenderNodeConfigurationBuilder().toPhone(getToPhone()).sid(ACCOUNT_SID).authToken(AUTH_TOKEN).body(getBody()).fromPhone(FROM_PHONE).build();
    }


}
