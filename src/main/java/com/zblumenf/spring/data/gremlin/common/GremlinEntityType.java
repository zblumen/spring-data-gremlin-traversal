package com.zblumenf.spring.data.gremlin.common;

import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSource;
import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSourceVertex;
import org.springframework.lang.NonNull;

import java.util.function.Supplier;

public enum GremlinEntityType {

    VERTEX(GremlinSourceVertex::new);
    //EDGE(GremlinSourceEdge::new),
    //GRAPH(GremlinSourceGraph::new);

    private Supplier<GremlinSource> creator;

    GremlinEntityType(@NonNull Supplier<GremlinSource> creator) {
        this.creator = creator;
    }

    public GremlinSource createGremlinSource() {
        return this.creator.get();
    }
}
