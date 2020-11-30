package com.zblumenf.spring.data.gremlin.annotation;

import com.zblumenf.spring.data.gremlin.predicate.PredicateGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specify Custom Predicate class to apply for a property
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PredicateGeneratorClass {
    Class<? extends PredicateGenerator> value();
}
