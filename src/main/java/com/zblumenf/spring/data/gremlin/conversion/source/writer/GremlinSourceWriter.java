package com.zblumenf.spring.data.gremlin.conversion.source.writer;

import com.zblumenf.spring.data.gremlin.conversion.MappingGremlinConverter;
import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSource;

/**
 * Provider Entity type dependent write method.
 */
public interface GremlinSourceWriter {
    /**
     * Write the domain class information to GremlinSource
     */
    void write(Object domain, MappingGremlinConverter converter, GremlinSource source);
}

