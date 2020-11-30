package com.zblumenf.spring.data.gremlin.conversion.result;

import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSource;
import org.apache.tinkerpop.gremlin.driver.Result;

import java.util.List;
import java.util.Map;

public interface GremlinResultsReader {
    /**
     * Read the Gremlin returned Results to GremlinSource.
     */
    void read(List<Map<Object, Object>> results, GremlinSource source);
}
