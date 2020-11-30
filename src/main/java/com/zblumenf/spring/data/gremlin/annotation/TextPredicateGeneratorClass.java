package com.zblumenf.spring.data.gremlin.annotation;

import org.apache.tinkerpop.gremlin.process.traversal.TextP;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specify Custom TextPredicate classes to apply for a property
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TextPredicateGeneratorClass {
    Class<? extends TextP> value();
}

