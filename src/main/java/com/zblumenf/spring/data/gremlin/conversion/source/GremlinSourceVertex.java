package com.zblumenf.spring.data.gremlin.conversion.source;

import com.zblumenf.spring.data.gremlin.conversion.result.GremlinResultVertexReader;
import com.zblumenf.spring.data.gremlin.conversion.source.reader.GremlinSourceVertexReader;
import com.zblumenf.spring.data.gremlin.conversion.source.writer.GremlinSourceVertexWriter;
import com.zblumenf.spring.data.gremlin.conversion.traversal.GremlinTraversalBuilderVertex;

public class GremlinSourceVertex<T> extends AbstractGremlinSource<T> {

    public GremlinSourceVertex() {
        super();
        initializeGremlinStrategy();
    }

    public GremlinSourceVertex(Class<T> domainClass) {
        super(domainClass);
        initializeGremlinStrategy();
    }

    private void initializeGremlinStrategy() {
        this.setGremlinTraversalStrategy(new GremlinTraversalBuilderVertex());
        this.setGremlinResultReader(new GremlinResultVertexReader());
        this.setGremlinSourceReader(new GremlinSourceVertexReader());
        this.setGremlinSourceWriter(new GremlinSourceVertexWriter());
    }
}
