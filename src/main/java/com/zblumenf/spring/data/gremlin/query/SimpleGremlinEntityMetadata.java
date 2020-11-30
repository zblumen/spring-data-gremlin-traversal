package com.zblumenf.spring.data.gremlin.query;

public class SimpleGremlinEntityMetadata<T> implements GremlinEntityMetadata<T> {

    private final Class<T> type;

    public SimpleGremlinEntityMetadata(Class<T> type) {
        this.type = type;
    }

    public Class<T> getJavaType() {
        return this.type;
    }
}
