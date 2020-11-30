package com.zblumenf.spring.data.gremlin.conversion.traversal;

import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSource;
import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSourceVertex;
import com.zblumenf.spring.data.gremlin.exception.GremlinUnexpectedSourceTypeException;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.springframework.util.Assert;

@NoArgsConstructor
public class GremlinTraversalBuilderVertex extends AbstractGremlinTraversalBuilder implements GremlinTraversalBuilder {

    @Override
    public GraphTraversal buildInsertTraversal(@NonNull GremlinSource source, @NonNull GraphTraversalSource g){
        if (!(source instanceof GremlinSourceVertex)) {
            throw new GremlinUnexpectedSourceTypeException("should be the instance of GremlinSourceVertex");
        }

        GraphTraversal traversal =  g.addV(source.getLabel());
        source.getId().ifPresent(id -> addPropertyWithRequiredId(traversal, id)); // property(id, xxx)
        addProperties(traversal, source.getProperties());

        return traversal;
    }

    @Override
    public GraphTraversal buildFindByIdTraversal(@NonNull GremlinSource source, @NonNull GraphTraversalSource g) {
        if (!(source instanceof GremlinSourceVertex)) {
            throw new GremlinUnexpectedSourceTypeException("should be the instance of GremlinSourceVertex");
        }

        Assert.isTrue(source.getId().isPresent(), "GremlinSource should contain id.");

        return generateHasId(g.V(), source.getId().get());
    }

    @Override
    public GraphTraversal buildDeleteByIdTraversal(@NonNull GremlinSource source, @NonNull GraphTraversalSource g) {
        if (!(source instanceof GremlinSourceVertex)) {
            throw new GremlinUnexpectedSourceTypeException("should be the instance of GremlinSourceVertex");
        }

        Assert.isTrue(source.getId().isPresent(), "GremlinSource should contain id.");

        return generateHasId(g.V(), source.getId().get()).drop();
    }
}
