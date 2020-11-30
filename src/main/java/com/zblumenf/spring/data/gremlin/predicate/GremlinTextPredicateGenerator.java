package com.zblumenf.spring.data.gremlin.predicate;

import org.apache.tinkerpop.gremlin.process.traversal.TextP;

public class GremlinTextPredicateGenerator implements TextPredicateGenerator {
    @Override
    public TextP getTextP(){
        return TextP.containing("0");
    }
}
