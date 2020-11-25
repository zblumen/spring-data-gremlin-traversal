package com.zblumenf.spring.data.gremlin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface GremlinRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {

}
