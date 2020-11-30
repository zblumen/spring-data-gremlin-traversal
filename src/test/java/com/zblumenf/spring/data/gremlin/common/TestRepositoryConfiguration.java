package com.zblumenf.spring.data.gremlin.common;

import com.zblumenf.spring.data.gremlin.common.GremlinConfig;
import com.zblumenf.spring.data.gremlin.config.AbstractGremlinConfiguration;
import com.zblumenf.spring.data.gremlin.repository.config.EnableGremlinRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@EnableGremlinRepositories
@PropertySource(value = {"classpath:application.properties"})
@EnableConfigurationProperties(TestGremlinProperties.class)
public class TestRepositoryConfiguration extends AbstractGremlinConfiguration {

    @Autowired
    private TestGremlinProperties testProps;

    @Override
    public GremlinConfig getGremlinConfig() {
        return GremlinConfig.builder(testProps.getEndpoint(), testProps.getUsername(), testProps.getPassword())
                .port(testProps.getPort())
                .telemetryAllowed(testProps.isTelemetryAllowed())
                .sslEnabled(testProps.isSslEnabled())
                .serializer(testProps.getSerializer())
                .build();
    }
}
