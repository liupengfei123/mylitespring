package org.mylitespring.context.annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mylitespring.beans.BeanDefinition;
import org.mylitespring.beans.BeanDefinitionRegistry;
import org.mylitespring.beans.factory.BeanDefinitionStoreException;
import org.mylitespring.beans.factory.support.BeanNameGenerator;
import org.mylitespring.core.io.Resource;
import org.mylitespring.core.io.support.PackageResourceLoader;
import org.mylitespring.core.type.AnnotationMetadata;
import org.mylitespring.core.type.classreading.MetadataReader;
import org.mylitespring.core.type.classreading.SimpleMetadataReader;
import org.mylitespring.stereotype.Component;
import org.mylitespring.util.StringUtils;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

public class ClassPathBeanDefinitionScanner {

    protected final Log logger = LogFactory.getLog(getClass());

    private final BeanDefinitionRegistry registry;

    private final PackageResourceLoader resourceLoader = new PackageResourceLoader();

    private final BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public Set<BeanDefinition> doScan(String packagesToScan) {
        String[] basePackages = StringUtils.tokenizeToStringArray(packagesToScan, ",");

        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<>();
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);

            for (BeanDefinition candidate : candidates) {
                registry.registerBeanDefinition(candidate.getID(),candidate);
            }
            beanDefinitions.addAll(candidates);
        }
        return beanDefinitions;
    }

    private Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        try {
            Resource[] resources = resourceLoader.getResources(basePackage);

            for (Resource resource : resources) {

                try {
                    MetadataReader reader = new SimpleMetadataReader(resource);
                    AnnotationMetadata metadata = reader.getAnnotationMetadata();

                    if(!metadata.hasAnnotation(Component.class.getName())){
                        continue;
                    }

                    ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadata);
                    String beanName = this.beanNameGenerator.generateBeanName(sbd, this.registry);
                    sbd.setId(beanName);
                    candidates.add(sbd);
                }
                catch (Throwable ex) {
                    throw new BeanDefinitionStoreException("Failed to read candidate component class: " + resource, ex);
                }
            }
        } catch (IOException ex) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
        }
        return candidates;
    }
}
