package com.zblumenf.spring.data.gremlin.conversion.result;

import com.zblumenf.spring.data.gremlin.common.Constants;
import com.zblumenf.spring.data.gremlin.common.GremlinUtils;
import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSource;
import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSourceVertex;
import com.zblumenf.spring.data.gremlin.exception.GremlinUnexpectedSourceTypeException;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

public class GremlinResultVertexReader extends AbstractGremlinResultReader implements  GremlinResultsReader {

    private void validate(List<Map<Object, Object>> results, GremlinSource source) {
        Assert.notNull(results, "Results should not be null.");
        Assert.notNull(source, "GremlinSource should not be null.");
        Assert.isTrue(results.size() == 1, "Vertex should contain only one result.");

        @SuppressWarnings("unchecked") final Map<Object, Object> result =results.get(0);

        Assert.isTrue(result.containsKey(Constants.PROPERTY_ID), "should contain id property");
        Assert.isTrue(result.containsKey(Constants.PROPERTY_LABEL), "should contain label property");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void read(@NonNull List<Map<Object, Object>> results, @NonNull GremlinSource source) {
        if (!(source instanceof GremlinSourceVertex)) {
            throw new GremlinUnexpectedSourceTypeException("Should be instance of GremlinSourceVertex");
        }

        validate(results, source);

        final Map<Object, Object> result =results.get(0);

        super.readResultProperties(result, source);

        final String className = ((List<Object>)source.getProperties().get(Constants.GREMLIN_PROPERTY_CLASSNAME)).get(0).toString();

        source.setIdField(GremlinUtils.getIdField(GremlinUtils.toEntityClass(className)));
        source.setId(result.get(Constants.PROPERTY_ID));
        source.setLabel((String) result.get(Constants.PROPERTY_LABEL));
    }
}
