package com.zblumenf.spring.data.gremlin.config;

import com.zblumenf.spring.data.gremlin.common.GremlinConfig;
import com.zblumenf.spring.data.gremlin.common.GremlinFactory;
import com.zblumenf.spring.data.gremlin.conversion.MappingGremlinConverter;
import com.zblumenf.spring.data.gremlin.query.GremlinTraversalTemplate;
import org.springframework.context.annotation.Bean;

public abstract class AbstractGremlinConfiguration extends GremlinConfigurationSupport {

    public abstract GremlinConfig getGremlinConfig();

    @Bean
    public GremlinFactory gremlinFactory() {
        return new GremlinFactory(getGremlinConfig());
    }

    @Bean
    public MappingGremlinConverter mappingGremlinConverter() throws ClassNotFoundException {
        return new MappingGremlinConverter(gremlinMappingContext());
    }

    @Bean
    public GremlinTraversalTemplate gremlinTemplate(GremlinFactory factory) throws ClassNotFoundException {
        return new GremlinTraversalTemplate(factory, mappingGremlinConverter());
    }
}
