package com.zblumenf.spring.data.gremlin.mapping;

import org.springframework.data.mapping.PersistentEntity;

public interface GremlinPersistentEntity<T> extends PersistentEntity<T, GremlinPersistentProperty> {

}