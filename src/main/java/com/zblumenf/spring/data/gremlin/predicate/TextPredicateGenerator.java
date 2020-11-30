package com.zblumenf.spring.data.gremlin.predicate;

import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.TextP;

public interface TextPredicateGenerator {
    TextP getTextP();
}
