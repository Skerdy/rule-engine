package com.tecnositaf.rayonit.ruleengine.api.node.controller;

import com.tecnositaf.rayonit.ruleengine.core.node.ComponentType;
import com.tecnositaf.rayonit.ruleengine.api.node.model.ComponentDescriptor;
import com.tecnositaf.rayonit.ruleengine.api.node.service.RuleNodeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/components")
public class ComponentsController {

    private final RuleNodeService service;

    public ComponentsController(RuleNodeService service) {
        this.service = service;
    }

    @GetMapping
    public List<ComponentDescriptor> getComponentsByTypes(@RequestParam("componentTypes") String[] strComponentTypes){
        Set<ComponentType> componentTypes = new HashSet<>();
        for (String strComponentType : strComponentTypes) {
            componentTypes.add(ComponentType.valueOf(strComponentType));
        }
        return service.getComponents(componentTypes);
    }

    @GetMapping("/all")
    public List<ComponentDescriptor> getAllComponents() {
        return service.getAll();
    }

}
