package com.zblumenf.spring.data.gremlin.repository.config;

import org.springframework.data.repository.config.RepositoryBeanDefinitionRegistrarSupport;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

import java.lang.annotation.Annotation;

public class GremlinRepositoryRegistrar extends RepositoryBeanDefinitionRegistrarSupport {

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableGremlinRepositories.class;
    }

    @Override
    protected RepositoryConfigurationExtension getExtension() {
        return new GremlinRepositoryConfigurationExtension();
    }
}

