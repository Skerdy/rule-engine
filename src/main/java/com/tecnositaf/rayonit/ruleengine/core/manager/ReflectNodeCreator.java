package com.tecnositaf.rayonit.ruleengine.core.manager;

import com.tecnositaf.rayonit.ruleengine.core.node.configuration.NodeConfiguration;
import com.tecnositaf.rayonit.ruleengine.core.node.simple.SimpleNode;
import com.tecnositaf.rayonit.ruleengine.jsengine.JavaScriptEngine;
import com.tecnositaf.rayonit.ruleengine.api.persistance.model.NodeDocument;
import org.json.simple.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectNodeCreator {

    private final JavaScriptEngine javaScriptEngine;

    public ReflectNodeCreator(JavaScriptEngine javaScriptEngine) {
        this.javaScriptEngine = javaScriptEngine;
    }

    public SimpleNode<? extends NodeConfiguration> create(NodeDocument document){
        Class<?> nodeClazz = null;
        Object node = null;

        Class<?> configurationClazz = null;
        Object configuration = null;

        try {
            nodeClazz = Class.forName(document.getClazz());
            configurationClazz = Class.forName(document.getConfigurationClazz());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(nodeClazz !=null && configurationClazz != null){
            try {
                node = nodeClazz.newInstance();
                Constructor<?> configurationConstructor = configurationClazz.getConstructor(JSONObject.class);
                configuration = configurationConstructor.newInstance(document.getConfigurationProperties());
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            if(node!=null){
                try {
                    injectRequiredMembersForNode(nodeClazz, node, configuration);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return (SimpleNode<? extends NodeConfiguration>) node;
    }

    private void injectRequiredMembersForNode(Class<?> clazz , Object node, Object configuration) throws InvocationTargetException, IllegalAccessException {
        Method[] allMethods = clazz.getDeclaredMethods();

        // injection iteration
        for(Method method : allMethods){
            if(method.getName().equals("setJavaScriptEngine")){
                method.invoke(node, javaScriptEngine);
            }
        }

        // fond init method and invoke it

        for(Method method : allMethods){
            if(method.getName().equals("init")){
                method.invoke(node, configuration);
            }
        }

    }
}
