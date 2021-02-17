/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.zblumenf.spring.data.gremlin.common.domain;

import com.zblumenf.spring.data.gremlin.annotation.GeneratedValue;
import com.zblumenf.spring.data.gremlin.annotation.Vertex;
import com.zblumenf.spring.data.gremlin.common.TestConstants;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Vertex(label = TestConstants.VERTEX_LABEL)
@Data
@NoArgsConstructor
public class Orange {

    @Id
    @GeneratedValue
    private String id;

    private String location;

    private Double price;

    public Orange(String location, Double price) {
        this.location = location;
        this.price = price;
    }
}
