package com.tecnositaf.rayonit.ruleengine.core.node.duplex;


import com.tecnositaf.rayonit.ruleengine.core.node.configuration.NodeConfiguration;
import com.tecnositaf.rayonit.ruleengine.core.node.simple.SimpleNode;
import com.tecnositaf.rayonit.ruleengine.message.Message;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

public abstract class DuplexNode<T extends NodeConfiguration> extends SimpleNode<T> {

    private EmitterProcessor<Message> outputChannel;

    public DuplexNode() {
        this.outputChannel = EmitterProcessor.create();
    }

    public abstract Message processMessage(Message message);

    protected void output(Message message) {
        if (this.outputChannel != null) {
            if (this.isSourceNode()) {
                message.setOriginator(getId());
            }
            message.setNodeId(getId());
            this.outputChannel.onNext(message);
        }
    }

    @Override
    public void start() {
        if(this.subscription !=null){
            this.subscription.dispose();
        }
        this.channel.doOnNext(incomingMessage -> {
            output(processMessage(incomingMessage));
        }).doOnError(Throwable::printStackTrace).subscribe();
    }

    public Flux<Message> getOutputChannel() {
        return outputChannel;
    }


}
