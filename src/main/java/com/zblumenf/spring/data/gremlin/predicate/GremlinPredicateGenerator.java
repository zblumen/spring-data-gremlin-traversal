package com.zblumenf.spring.data.gremlin.predicate;

import org.apache.tinkerpop.gremlin.process.traversal.P;

public class GremlinPredicateGenerator implements PredicateGenerator {
    @Override
    public P<?> getP(){
        return P.eq(0);
    }
}
