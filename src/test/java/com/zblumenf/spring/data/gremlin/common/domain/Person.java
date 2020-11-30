package com.zblumenf.spring.data.gremlin.common.domain;

import com.zblumenf.spring.data.gremlin.annotation.Vertex;
import com.zblumenf.spring.data.gremlin.common.TestConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Vertex(label = TestConstants.VERTEX_PERSON_LABEL)
public class Person {

    private String id;

    private String name;
}

