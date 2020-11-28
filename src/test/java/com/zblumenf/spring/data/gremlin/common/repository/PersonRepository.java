package com.zblumenf.spring.data.gremlin.common.repository;

import com.zblumenf.spring.data.gremlin.common.domain.Person;
import com.zblumenf.spring.data.gremlin.repository.GremlinRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends GremlinRepository<Person, String> {
}

