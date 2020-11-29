/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.zblumenf.spring.data.gremlin.annotation;

import org.springframework.data.annotation.Persistent;

import java.lang.annotation.*;

import static com.zblumenf.spring.data.gremlin.common.Constants.DEFAULT_LABEL_PROPERTY_KEY;

/**
 * Specifies the class as vertex in graph, with one optional label(String).
 */
@Persistent
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Vertex {
    /**
     * The label(gremlin reserved) of given Vertex, can add Vertex by label.
     * @return class name if not specify.
     */
    String label();
    String labelProperty() default DEFAULT_LABEL_PROPERTY_KEY;
}
