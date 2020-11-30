package com.zblumenf.spring.data.gremlin.query.query;

import com.zblumenf.spring.data.gremlin.common.GremlinUtils;
import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSource;
import com.zblumenf.spring.data.gremlin.query.GremlinOperations;
import com.zblumenf.spring.data.gremlin.repository.support.GremlinEntityInformation;
import org.springframework.lang.NonNull;

public interface GremlinQueryExecution {
    Object execute(GremlinQuery query, Class<?> type);

    final class FindExecution implements GremlinQueryExecution {

        private final GremlinOperations operations;

        public FindExecution(@NonNull GremlinOperations operations) {
            this.operations = operations;
        }

        @Override
        public Object execute(@NonNull GremlinQuery query, @NonNull Class<?> domainClass) {
            final GremlinSource<?> source = new GremlinEntityInformation<>(domainClass).createGremlinSource();

            return this.operations.find(query, source);
        }
    }
}
