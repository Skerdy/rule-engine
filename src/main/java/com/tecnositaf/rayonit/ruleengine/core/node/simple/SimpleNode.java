package com.tecnositaf.rayonit.ruleengine.core.node.simple;

import com.tecnositaf.rayonit.ruleengine.core.node.RuleNode;
import com.tecnositaf.rayonit.ruleengine.core.node.configuration.NodeConfiguration;
import com.tecnositaf.rayonit.ruleengine.core.node.duplex.DuplexNode;
import com.tecnositaf.rayonit.ruleengine.message.Message;
import reactor.core.Disposable;
import reactor.core.publisher.EmitterProcessor;

public abstract class SimpleNode<T extends NodeConfiguration> implements RuleNode<T> {

    protected Disposable subscription;
    protected EmitterProcessor<Message> channel;
    private String id;

    public SimpleNode() {
        this.channel = EmitterProcessor.create();
    }

    protected void next(Message message) {
        if (this.channel != null) {
            this.channel.onNext(message);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EmitterProcessor<Message> getChannel() {
        return channel;
    }

    public void subscribeTo(SimpleNode<? extends NodeConfiguration> predecessorNode) {
        if (predecessorNode instanceof DuplexNode) {
            ((DuplexNode<? extends NodeConfiguration>) predecessorNode).getOutputChannel().doOnNext(this::next).subscribe();
        } else {
            predecessorNode.getChannel().doOnNext(this::next).subscribe();
        }
    }

    @Override
    public void start() {
        this.subscription = this.channel.doOnNext(this::onMessage).subscribe();
    }

    @Override
    public void stop() {
        this.subscription.dispose();
    }

    public void subscribeTo(SimpleNode<? extends NodeConfiguration> predecessorNode, String predicate) {
        if (predecessorNode instanceof DuplexNode) {
            ((DuplexNode<? extends NodeConfiguration>) predecessorNode).getOutputChannel().filter(message -> message.getOutputResult().equals(predicate)).doOnNext(this::next).subscribe();
        } else {
            predecessorNode.getChannel().filter(message -> message.getOutputResult().equals(predicate)).doOnNext(this::next).subscribe();
        }
    }


}
