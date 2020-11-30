package com.zblumenf.spring.data.gremlin.conversion.source.reader;

import com.zblumenf.spring.data.gremlin.conversion.MappingGremlinConverter;
import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSource;

/**
 * Provider Entity type dependent read method.
 */
public interface GremlinSourceReader {
    /**
     * Read data from GremlinSource to domain
     */
    <T extends Object> T read(Class<T> domainClass, MappingGremlinConverter converter, GremlinSource<T> source);
}
