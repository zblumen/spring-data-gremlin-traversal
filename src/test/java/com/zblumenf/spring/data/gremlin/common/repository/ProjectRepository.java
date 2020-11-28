package com.zblumenf.spring.data.gremlin.common.repository;

import com.zblumenf.spring.data.gremlin.common.domain.Project;
import com.zblumenf.spring.data.gremlin.repository.GremlinRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends GremlinRepository<Project, String> {
}

