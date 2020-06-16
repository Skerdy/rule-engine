package com.tecnositaf.rayonit.ruleengine.nodetype.action.sendmail;

import com.tecnositaf.rayonit.ruleengine.core.node.ComponentType;
import com.tecnositaf.rayonit.ruleengine.core.node.Node;
import com.tecnositaf.rayonit.ruleengine.core.node.simple.SimpleNode;
import com.tecnositaf.rayonit.ruleengine.message.Message;
import com.tecnositaf.rayonit.ruleengine.nodetype.action.ActionNode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Node(
        type = ComponentType.ACTION,
        name = "Mail Sender Node", relationTypes = {"Connected", "Disconnected"},
        configClazz = MailSenderNodeConfiguration.class,
        nodeDescription = "Send mail",
        nodeDetails =
                "Details - Action Node that sends an email based on inputed configuration")
public class MailSenderNode extends SimpleNode<MailSenderNodeConfiguration> implements ActionNode<Message> {

    private JavaMailSender emailSender;

    private EmailDetails emailDetails;

    private JSONParser parser;

    public MailSenderNode() {
        this.parser = new JSONParser();
    }

    private void sendSimpleMessage(EmailDetails emailDetails) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDetails.getTo());
        message.setSubject(emailDetails.getSubject());
        message.setText(emailDetails.getText());
        emailSender.send(message);
    }

    private void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file
                    = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment(file.getFilename(), file);

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void trigerAction(Message message) {
        // TODO :: Replace wildcard logic
        try {
            this.emailDetails.setText(EmailBuilder.getEmailText(this.emailDetails.getText(), (JSONObject) this.parser.parse(message.getPayload())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.sendSimpleMessage(this.emailDetails);
    }

    @Override
    public void init(MailSenderNodeConfiguration configuration) {
        this.emailSender = configuration.getJavaMailSender();
        this.emailDetails = configuration.getEmailDetails();
    }

    @Override
    public void onMessage(Message message) {
        trigerAction(message);
    }

    @Override
    public void destroy() {

    }


    @Override
    public boolean isSourceNode() {
        return false;
    }


}
