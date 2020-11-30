/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.zblumenf.spring.data.gremlin.query.parameter;

import com.zblumenf.spring.data.gremlin.query.query.GremlinQueryMethod;
import org.springframework.data.repository.query.ParametersParameterAccessor;

public class GremlinParametersParameterAccessor extends ParametersParameterAccessor
        implements GremlinParameterAccessor {

    public GremlinParametersParameterAccessor(GremlinQueryMethod method, Object[] values) {
        super(method.getParameters(), values);
    }
}
