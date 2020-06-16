package com.tecnositaf.rayonit.ruleengine.core.node;

import com.tecnositaf.rayonit.ruleengine.core.node.configuration.NodeConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Node {

    ComponentType type();

    String name();

    String nodeDescription();

    String nodeDetails();

    Class<? extends NodeConfiguration> configClazz();

    boolean inEnabled() default true;

    boolean outEnabled() default true;

    String[] relationTypes() default {RelationTypes.SUCCESS, RelationTypes.FAILURE};

}
