package com.tecnositaf.rayonit.ruleengine.core.chain;

import com.tecnositaf.rayonit.ruleengine.core.connection.NodeConnectionInfo;
import com.tecnositaf.rayonit.ruleengine.core.node.configuration.NodeConfiguration;
import com.tecnositaf.rayonit.ruleengine.core.node.duplex.DuplexNode;
import com.tecnositaf.rayonit.ruleengine.core.node.simple.SimpleNode;
import com.tecnositaf.rayonit.ruleengine.util.GraphUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleChain implements Chain {

    private String id;

    private List<SimpleNode<? extends NodeConfiguration>> nodes;

    private List<NodeConnectionInfo> nodeConnectionInfoList;

    private boolean started;


    public RuleChain(List<SimpleNode<? extends NodeConfiguration>> nodes) {
        this.nodes = nodes;
        if (!nodes.isEmpty()) {
            startAllFollowingNodes();
        }
        this.nodeConnectionInfoList = new ArrayList<>();
    }

    public boolean isConnectionValid(){

        GraphUtil graph = new GraphUtil(this.nodes.size());
        for (NodeConnectionInfo nodeConnectionInfo: this.nodeConnectionInfoList){
            graph.addEdge(nodeConnectionInfo.getFromIndex(), nodeConnectionInfo.getToIndex());
        }
        return !graph.isCyclic();
    }



    public void setNodeConnectionInfoList(List<NodeConnectionInfo> nodeConnectionInfoList) {
        this.nodeConnectionInfoList = nodeConnectionInfoList;
    }

    @Override
    public void start() {
        if (this.processConnectionInfo()) {
            if (this.getSourceNode() != null) {
                this.getSourceNode().start();
                this.started=true;

                System.out.println(" Rule Chain Started Successfully !");
            }
        } else {
            System.out.println(" Rule Chain Configuration is not valid !");
        }
    }

    @Override
    public void stop() {
       if(this.getSourceNode() != null){
           this.getSourceNode().stop();
       }
    }

    @Override
    public void restart() {
        stop();
        start();
    }

    @Override
    public boolean processConnectionInfo() {
        boolean flag = true;
        if (this.nodeConnectionInfoList != null && !nodeConnectionInfoList.isEmpty()) {
            for (NodeConnectionInfo nodeConnectionInfo : this.nodeConnectionInfoList) {
                //TODO ADD Output result predicate for receiver
                if (nodeConnectionInfo.getPredicate() != null) {
                    this.nodes.get(nodeConnectionInfo.getToIndex()).subscribeTo(this.nodes.get(nodeConnectionInfo.getFromIndex()), nodeConnectionInfo.getPredicate());

                } else {
                    this.nodes.get(nodeConnectionInfo.getToIndex()).subscribeTo(this.nodes.get(nodeConnectionInfo.getFromIndex()));
                }
            }
        } else {
            flag = false;
        }

        return flag;
    }

    public void addNodeConnectionInfo(NodeConnectionInfo nodeConnectionInfo) {
        if (this.nodeConnectionInfoList == null) {
            this.nodeConnectionInfoList = new ArrayList<>();
        }
        this.nodeConnectionInfoList.add(nodeConnectionInfo);
    }

    private void startAllFollowingNodes() {
        if (this.nodes.size() > 1) {
            for (int i = 1; i < this.nodes.size(); i++) {
                if(this.nodes.get(i) instanceof DuplexNode){
                    ((DuplexNode<? extends NodeConfiguration>)this.nodes.get(i)).start();
                } else {
                    this.nodes.get(i).start();
                }

            }
        }
    }

    public SimpleNode<? extends NodeConfiguration> getLastNode() {
        return this.nodes.get(this.nodes.size() - 1);
    }

    public SimpleNode<? extends NodeConfiguration> getSourceNode() {
        if (!nodes.isEmpty()) {
            return this.nodes.get(0);
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public SimpleNode<? extends NodeConfiguration> getNode(int index){
        if(this.nodes != null && this.nodes.size()> index){
            return this.nodes.get(index);
        }
        return null;
    }
}
