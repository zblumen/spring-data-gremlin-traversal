/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.zblumenf.spring.data.gremlin.common.domain;

import com.zblumenf.spring.data.gremlin.annotation.Vertex;
import com.zblumenf.spring.data.gremlin.common.TestConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Vertex(label = TestConstants.VERTEX_BOOK_LABEL)
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    private Integer serialNumber;

    private String name;

    private Double price;
}
