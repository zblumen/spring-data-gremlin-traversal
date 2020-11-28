package com.zblumenf.spring.data.gremlin.query.query;

import lombok.Getter;
import lombok.NonNull;

public class GremlinQuery {

    @Getter
    private final Criteria criteria;

    public GremlinQuery(@NonNull Criteria criteria) {
        this.criteria = criteria;
    }
}
