/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.zblumenf.spring.data.gremlin.config;

import com.zblumenf.spring.data.gremlin.common.domain.*;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GremlinConfigurationSupportUnitTest {

    private static final String TEST_CONFIG_PACKAGE_NAME = "com.zblumenf.spring.data.gremlin.config";
    private static final String TEST_DOMAIN_PACKAGE_NAME = "com.zblumenf.spring.data.gremlin.common.domain";
    private TestConfig config;

    @Before
    public void setup() {
        this.config = new TestConfig();
    }

    @Test
    public void testGetMappingBasePackages() {
        final Collection<String> basePackages = this.config.getMappingBasePackages();

        Assert.assertNotNull(basePackages);
        Assert.assertEquals(basePackages.size(), 1);
        Assert.assertEquals(basePackages.toArray()[0], TEST_CONFIG_PACKAGE_NAME);
    }

    @Test
    public void testGremlinMappingContext() throws ClassNotFoundException {
        Assert.assertNotNull(this.config.gremlinMappingContext());
    }

    @Test
    @SneakyThrows
    public void testScanEntity() {
        final Set<Class<?>> entities = this.config.scanEntities(TEST_DOMAIN_PACKAGE_NAME);
        final Set<Class<?>> references = new HashSet<>(Arrays.asList(
                Library.class, Person.class, Project.class, Service.class, UserDomain.class, AdvancedUser.class,
                Student.class, Book.class, Master.class, GroupOwner.class, Orange.class
                //Group.class, Dependency.class, , Network.class, Relationship.class, Roadmap.class, SimpleDependency.class,
               // InvalidDependency.class, BookReference.class, Neighbor.class
        ));

        Assert.assertNotNull(entities);
        Assert.assertEquals(entities.size(), references.size());

        references.forEach(entity -> Assert.assertTrue(entities.contains(entity)));
    }

    @Test
    @SneakyThrows
    public void testScanEntityEmpty() {
        final Set<Class<?>> entities = this.config.scanEntities("");

        Assert.assertTrue(entities.isEmpty());
    }

    @NoArgsConstructor
    private class TestConfig extends GremlinConfigurationSupport {

    }
}
