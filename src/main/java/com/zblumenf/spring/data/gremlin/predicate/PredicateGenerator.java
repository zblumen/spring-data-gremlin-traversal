package com.zblumenf.spring.data.gremlin.predicate;

import org.apache.tinkerpop.gremlin.process.traversal.P;

public interface PredicateGenerator {
    P<?> getP();
}
