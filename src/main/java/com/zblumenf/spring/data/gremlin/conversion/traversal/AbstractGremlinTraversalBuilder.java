package com.zblumenf.spring.data.gremlin.conversion.traversal;


import com.zblumenf.spring.data.gremlin.common.GremlinUtils;
import com.zblumenf.spring.data.gremlin.exception.GremlinInvalidEntityIdFieldException;
import lombok.NonNull;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.T;

import java.util.Date;
import java.util.Map;

public abstract class AbstractGremlinTraversalBuilder {

    protected static void generatePropertyWithRequiredId(@NonNull GraphTraversal traversal, @NonNull Object id) {
        if (id instanceof Long || id instanceof String || id instanceof Integer) {
            traversal.property(T.id, id);
        } else{
            throw new GremlinInvalidEntityIdFieldException("Only String/Integer/Long of id is supported");
        }
    }

    private static void generateProperty(@NonNull GraphTraversal traversal, @NonNull String name, @NonNull Object value){
        Object insertValue;
        if(value instanceof Date){
            insertValue = GremlinUtils.timeToMilliSeconds(value);
        }else{
            insertValue = value;
        }
        traversal.property(name, insertValue);
    }

    protected static void generateProperties(@NonNull GraphTraversal traversal, @NonNull final Map<String, Object> properties) {
        properties.entrySet().stream().filter(e -> e.getValue() != null)
                .forEach(e -> generateProperty(traversal, e.getKey(), e.getValue()));

    }

    public static GraphTraversal generateHasId(@NonNull GraphTraversal traversal, @NonNull Object id) {
        if (id instanceof Long || id instanceof String || id instanceof Integer) {
            return traversal.hasId(id);
        }
        throw new GremlinInvalidEntityIdFieldException("the type of @Id/id field should be String/Integer/Long");
    }

}
