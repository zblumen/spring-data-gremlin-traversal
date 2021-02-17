/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.zblumenf.spring.data.gremlin.common;

import com.zblumenf.spring.data.gremlin.common.domain.Service;
import com.zblumenf.spring.data.gremlin.conversion.source.AbstractGremlinSource;
import org.junit.Assert;
import org.junit.Test;

public class GremlinUtilsUnitTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCreateIntegerInstance() {
        GremlinUtils.createInstance(Integer.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateTestConstantsInstance() {
        GremlinUtils.createInstance(TestConstants.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateAbstractInstance() {
        GremlinUtils.createInstance(AbstractGremlinSource.class);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testTimeToMilliSecondsException() {
        GremlinUtils.timeToMilliSeconds(new Service());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testToPrimitiveLongException() {
        GremlinUtils.toPrimitiveLong((short) 2);
    }

    @Test
    public void testToPrimitiveLong() {
        Assert.assertEquals((long) 3, GremlinUtils.toPrimitiveLong(new Long(3)));
    }
}
