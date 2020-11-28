package com.zblumenf.spring.data.gremlin.conversion.result;

import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSource;
import org.springframework.lang.NonNull;

import java.util.Map;

public abstract class AbstractGremlinResultReader {

    protected void readResultProperties(@NonNull Map<Object, Object> properties, @NonNull GremlinSource source) {
        source.getProperties().clear();
        properties.forEach((key, value) -> source.setProperty(key.toString(), value));
    }
}
