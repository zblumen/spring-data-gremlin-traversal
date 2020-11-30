package com.zblumenf.spring.data.gremlin.conversion.traversal;

import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;

public interface GremlinTraversalBuilder {
    /**
     * build the insert query from source (Vertex, Edge or Graph).
     */
    GraphTraversal buildInsertTraversal(GremlinSource source, GraphTraversalSource g);

    /**
     * build the deleteAll query from source (Vertex, Edge or Graph).
     */
    //GraphTraversal buildDeleteAllTraversal();

    /**
     * build the deleteAll By Domain Class query from source (Vertex, Edge or Graph).
     */
    // GraphTraversal buildDeleteAllByClassTraversal(GremlinSource source, GraphTraversalSource g);

    /**
     * build the findById query from source (Vertex, Edge).
     */
    GraphTraversal buildFindByIdTraversal(GremlinSource source, GraphTraversalSource g);

    /**
     * build the update query from source (Vertex, Edge or Graph).
     */
    // GraphTraversal buildUpdateTraversal(GremlinSource source, GraphTraversalSource g);

    /**
     * build the findAll query from source (Vertex, Edge or Graph).
     */
    // GraphTraversal buildFindAllTraversal(GremlinSource source, GraphTraversalSource g);

    /**
     * build the DeleteById query from source (Vertex, Edge or Graph).
     */
    GraphTraversal buildDeleteByIdTraversal(GremlinSource source, GraphTraversalSource g);

    /**
     * build the Count query from Source (Vertex, Edge)
     */
    // GraphTraversal buildCountTraversal(GremlinSource source, GraphTraversalSource g);

    
}
