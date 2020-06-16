package com.skerdy.ruleengine.nodetype.action.sendmail;

import com.skerdy.ruleengine.core.annotation.ConfigurationField;
import com.skerdy.ruleengine.core.node.configuration.NodeConfiguration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.json.simple.JSONObject;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@Builder
@Data
@AllArgsConstructor
public class MailSenderNodeConfiguration implements NodeConfiguration<MailSenderNodeConfiguration> {

    private final String DEFAULT_HOST = "smtp.gmail.com";
    private final Integer DEFAULT_PORT = 587;
    private final String DEFAULT_PROTOCOL = "smtp";

    @ConfigurationField(required = false)
    private String host;
    @ConfigurationField(required = false)
    private Integer port;
    @ConfigurationField(required = true)
    private String username;
    @ConfigurationField(required = true)
    private String password;
    @ConfigurationField(required = false)
    private String protocol;

    @ConfigurationField(required = true)
    private EmailDetails emailDetails;

    @ConfigurationField(required = true)
    private HashMap<String, Object> properties;

    public MailSenderNodeConfiguration() {
    }

    public MailSenderNodeConfiguration(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public MailSenderNodeConfiguration(JSONObject config) {
        Object host = config.get("host");
        if (host != null)
            this.host = host.toString();
        else
            this.host = DEFAULT_HOST;
        Object port = config.get("port");
        if (port != null)
            this.port = Integer.valueOf(port.toString());
        else
            this.port = DEFAULT_PORT;
        Object username = config.get("username");
        if (username != null)
            this.username = username.toString();
        else
            throw new InsufficientMailArgumentsException("Please provide username and password of your email");
        Object password = config.get("password");
        if (password != null)
            this.password = password.toString();
        else
            throw new InsufficientMailArgumentsException("Please provide username and password of your email");
        Object protocol = config.get("protocol");
        if (protocol != null)
            this.protocol = protocol.toString();
        else
            this.protocol = DEFAULT_PROTOCOL;
        Object properties = config.get("properties");
        if (properties != null)
            this.properties = (HashMap<String, Object>) properties;
        else
            this.properties = defaultProperties();

        Object emailDetails = config.get("emailDetails");
        if (emailDetails != null) {
            this.emailDetails = new EmailDetails();
            Object to = ((LinkedHashMap) emailDetails).get("to");
            if (to != null)
                this.emailDetails.setTo(to.toString());
            else
                throw new InsufficientMailArgumentsException("Please provide recipient attributes");
            Object subject = ((LinkedHashMap) emailDetails).get("subject");
            if (subject != null)
                this.emailDetails.setSubject(subject.toString());
            else
                this.emailDetails.setSubject("");
            Object text = ((LinkedHashMap) emailDetails).get("text");
            if (text != null)
                this.emailDetails.setText(text.toString());
            else
                throw new InsufficientMailArgumentsException("Please provide email content");
        }


    }

    @Override
    public MailSenderNodeConfiguration defaultConfiguration() {
        return new MailSenderNodeConfigurationBuilder().username(username).password(password).host(DEFAULT_HOST).port(DEFAULT_PORT)
                .protocol(DEFAULT_PROTOCOL).properties(defaultProperties()).build();
    }

    private Map<String, Object> getJSONObject(HashMap<Object, Object> jsonObject) {
        Map<String, Object> result = new HashMap<>();
        for (Object key : jsonObject.keySet()) {
            if (key instanceof String) {
                result.put((String) key, jsonObject.get(key));
            }
        }
        return result;
    }

    private HashMap<String, Object> defaultProperties() {
        HashMap<String, Object> props = new HashMap<>();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return props;
    }

    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        for (String key : properties.keySet()) {
            props.put(key, properties.get(key));
        }
        return mailSender;
    }

    public JavaMailSender getJavaMailSender(MailSenderNodeConfiguration configuration) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(configuration.getHost());
        mailSender.setPort(configuration.getPort());

        mailSender.setUsername(configuration.getUsername());
        mailSender.setPassword(configuration.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        for (String key : properties.keySet()) {
            props.put(key, properties.get(key));
        }

        return mailSender;
    }
}
