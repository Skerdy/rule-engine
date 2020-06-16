package com.tecnositaf.rayonit.ruleengine.api.node.service;

import com.google.gson.Gson;
import com.tecnositaf.rayonit.ruleengine.api.node.model.ComponentDescriptor;
import com.tecnositaf.rayonit.ruleengine.api.node.model.FieldDescriptor;
import com.tecnositaf.rayonit.ruleengine.api.node.repository.ComponentDescriptorRepository;
import com.tecnositaf.rayonit.ruleengine.core.annotation.ConfigurationField;
import com.tecnositaf.rayonit.ruleengine.core.node.ComponentType;
import com.tecnositaf.rayonit.ruleengine.core.node.Node;
import com.tecnositaf.rayonit.ruleengine.core.node.NodeDefinition;
import com.tecnositaf.rayonit.ruleengine.core.node.configuration.NodeConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.TypeVariable;
import java.util.*;

@Service
@Slf4j
public class RuleNodeService implements ComponentDescriptorService {

    public static final int MAX_OPTIMISITC_RETRIES = 3;

    private final String nodesPackage = "com.tecnositaf.rayonit.ruleengine.nodetype";
    private final ComponentDescriptorRepository componentDescriptorRepository;
    private Map<String, ComponentDescriptor> components = new HashMap<>();

    // private ObjectMapper mapper = new ObjectMapper();
    private Map<ComponentType, List<ComponentDescriptor>> componentsMap = new HashMap<>();
    private boolean isInstall = false;

    private Gson gson = new Gson();

    public RuleNodeService(ComponentDescriptorRepository componentDescriptorRepository) {
        this.componentDescriptorRepository = componentDescriptorRepository;
    }

    @PostConstruct
    public void init() {
        if (!isInstall) {
            discoverComponents();
        }
    }

    public void discoverComponents() {
        registerRuleNodeComponents();
        log.info("Found following definitions: {}", components.values());
    }

    private void registerRuleNodeComponents() {
        Set<BeanDefinition> ruleNodeBeanDefinitions = getBeanDefinitions(Node.class);
        for (BeanDefinition def : ruleNodeBeanDefinitions) {
            int retryCount = 0;
            Exception cause = null;
            while (retryCount < MAX_OPTIMISITC_RETRIES) {
                try {
                    String clazzName = def.getBeanClassName();
                    Class<?> clazz = Class.forName(clazzName);
                    Node ruleNodeAnnotation = clazz.getAnnotation(Node.class);
                    ComponentType type = ruleNodeAnnotation.type();
                    ComponentDescriptor component = scanAndPersistComponent(def, type, ruleNodeAnnotation.configClazz());
                    components.put(component.getClazz(), component);
                    componentsMap.computeIfAbsent(type, k -> new ArrayList<>()).add(component);
                    break;
                } catch (Exception e) {
                    cause = e;
                    retryCount++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        throw new RuntimeException(e1);
                    }
                }
            }
            if (cause != null && retryCount == MAX_OPTIMISITC_RETRIES) {
                throw new RuntimeException(cause);
            }
        }
    }

    private Set<BeanDefinition> getBeanDefinitions(Class<? extends Annotation> componentType) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(componentType));
        return new HashSet<>(scanner.findCandidateComponents(nodesPackage));
    }

    private ComponentDescriptor scanAndPersistComponent(BeanDefinition def, ComponentType type, Class<?> configClazz) {
        ComponentDescriptor scannedComponent = new ComponentDescriptor();
        String clazzName = def.getBeanClassName();
        try {
            scannedComponent.setType(type);
            Class<?> clazz = Class.forName(clazzName);
            switch (type) {
                case ENRICHMENT:
                case FILTER:
                case TRANSFORMATION:
                case ACTION:
                case INPUT:
                    Node ruleNodeAnnotation = clazz.getAnnotation(Node.class);
                    scannedComponent.setName(ruleNodeAnnotation.name());
                    NodeDefinition nodeDefinition = prepareNodeDefinition(ruleNodeAnnotation);
                    JSONObject object = new JSONObject();
                    object.put("nodeDefinition", gson.toJson(nodeDefinition));
                    scannedComponent.setConfigurationDescriptor(object);
                    scannedComponent.setConfigurationFields(collectConfigurattionFieldsForFields(ruleNodeAnnotation.configClazz().getDeclaredFields()));
                   // scannedComponent.setConfigurationFields(collectConfigurationFieldInformation(ruleNodeAnnotation));
                    break;
                default:
                    throw new RuntimeException(type + " is not supported yet!");
            }
            scannedComponent.setClazz(clazzName);
            scannedComponent.setConfigurationClazz(configClazz.getName());
            log.info("Processing scanned component: {}", scannedComponent);
        } catch (Exception e) {
            log.error("Can't initialize component {}, due to {}", def.getBeanClassName(), e.getMessage(), e);
            throw new RuntimeException(e);
        }
        ComponentDescriptor persistedComponent = this.findByClass(clazzName);
        if (persistedComponent == null) {
            log.info("Persisting new component: {}", scannedComponent);
            scannedComponent = this.save(scannedComponent);
        } else if (scannedComponent.equals(persistedComponent)) {
            log.info("Component is already persisted: {}", persistedComponent);
            scannedComponent = persistedComponent;
        } else {
            log.info("Component {} will be updated to {}", persistedComponent, scannedComponent);
            this.deleteByClass(persistedComponent.getClazz());
            scannedComponent.setId(persistedComponent.getId());
            scannedComponent = this.save(scannedComponent);
        }
        return scannedComponent;
    }

    private List<FieldDescriptor> collectConfigurattionFieldsForFields(Field[] fields){
        List<FieldDescriptor> result = new ArrayList<>();
        for(Field field: fields ){
            ConfigurationField configAnottation = field.getAnnotation(ConfigurationField.class);
            if(configAnottation!=null){
                FieldDescriptor fieldDescriptor = new FieldDescriptor();
                fieldDescriptor.setName(field.getName());
                fieldDescriptor.setType(field.getType().getName());
                fieldDescriptor.setRequired(configAnottation.required());
                Class<?>[] typeParameters = configAnottation.typeParameters();
                if(typeParameters.length>0){
                    for(Class<?> typeParam: typeParameters){
                        fieldDescriptor.addTypeParameter(typeParam.getName());
                    }
                }
                if(field.getType().getName().contains("com.tecnositaf.rayonit.ruleengine.nodetype")){
                    fieldDescriptor.setChildren(collectConfigurattionFieldsForFields(field.getType().getDeclaredFields()));
                }
                result.add(fieldDescriptor);
            }
        }
        return result;
    }


    private NodeDefinition prepareNodeDefinition(Node nodeAnnotation) throws Exception {
        NodeDefinition nodeDefinition = new NodeDefinition();
        nodeDefinition.setDetails(nodeAnnotation.nodeDetails());
        nodeDefinition.setDescription(nodeAnnotation.nodeDescription());
        nodeDefinition.setInEnabled(nodeAnnotation.inEnabled());
        nodeDefinition.setOutEnabled(nodeAnnotation.outEnabled());
        Class<? extends NodeConfiguration> configClazz = nodeAnnotation.configClazz();
        NodeConfiguration config = configClazz.newInstance();
        NodeConfiguration defaultConfiguration = config.defaultConfiguration();
        nodeDefinition.setDefaultConfiguration(new JSONObject());

        return nodeDefinition;
    }

    @Override
    public ComponentDescriptor save(ComponentDescriptor componentDescriptor) {
        return this.componentDescriptorRepository.save(componentDescriptor);
    }

    @Override
    public ComponentDescriptor edit(ComponentDescriptor componentDescriptor) {
        return componentDescriptor;
    }

    @Override
    public List<ComponentDescriptor> getAll() {
        return this.componentDescriptorRepository.findAll();
    }

    @Override
    public ComponentDescriptor findById(String id) {
        return this.componentDescriptorRepository.findById(id).orElse(null);
    }

    @Override
    public ComponentDescriptor findByClass(String clazz) {
        return this.componentDescriptorRepository.findByClazz(clazz);
    }

    @Override
    public void deleteByClass(String clazz) {
        this.componentDescriptorRepository.deleteByClazz(clazz);
    }

    @Override
    public List<ComponentDescriptor> findAllByClass(String clazz) {
        return this.componentDescriptorRepository.findAllByClazz(clazz);
    }

    public List<ComponentDescriptor> getComponents(Set<ComponentType> types) {
        List<ComponentDescriptor> result = new ArrayList<>();
        types.stream().filter(type -> componentsMap.containsKey(type)).forEach(type -> {
            result.addAll(componentsMap.get(type));
        });
        return Collections.unmodifiableList(result);
    }

    public List<ComponentDescriptor> getComponents(ComponentType type) {
        if (componentsMap.containsKey(type)) {
            return Collections.unmodifiableList(componentsMap.get(type));
        } else {
            return Collections.emptyList();
        }
    }

    public Optional<ComponentDescriptor> getComponent(String clazz) {
        return Optional.ofNullable(components.get(clazz));
    }
}
