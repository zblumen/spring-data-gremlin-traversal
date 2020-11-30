package com.zblumenf.spring.data.gremlin.query.query;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;

public interface QueryTraversalGenerator {
    GraphTraversal generate(GremlinQuery query, GraphTraversalSource g);
}
