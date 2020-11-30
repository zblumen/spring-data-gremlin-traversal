package com.zblumenf.spring.data.gremlin.query.query;

import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSource;
import com.zblumenf.spring.data.gremlin.conversion.traversal.AbstractGremlinTraversalBuilder;
import lombok.NonNull;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.T;

import static com.zblumenf.spring.data.gremlin.common.Constants.DEFAULT_LABEL_PROPERTY_KEY;

public  abstract class AbstractGremlinTraversalGenerator extends AbstractGremlinTraversalBuilder {
    public static void addHasLabel(@NonNull GraphTraversal traversal, @NonNull GremlinSource source){

        if(source.getLabelProperty().equals(DEFAULT_LABEL_PROPERTY_KEY)){
            traversal.hasLabel(source.getLabel());
        }else{
            traversal.has(source.getLabelProperty(), source.getLabel());
        }
    }
}
