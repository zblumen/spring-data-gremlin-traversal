package com.zblumenf.spring.data.gremlin.repository.config;

import com.zblumenf.spring.data.gremlin.repository.support.GremlinRepositoryFactoryBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.repository.config.DefaultRepositoryBaseClass;

import java.lang.annotation.*;

import static com.zblumenf.spring.data.gremlin.common.Constants.DEFAULT_REPOSITORY_IMPLEMENT_POSTFIX;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(GremlinRepositoryRegistrar.class)
public @interface EnableGremlinRepositories {

    /**
     * Alias for basePackages.
     */
    String[] value() default {};

    /**
     * Base packages to scan for components with annotations.
     */
    String[] basePackages() default {};

    /**
     * Type-safe version of basePackages.
     */
    Class<?>[] basePackageClasses() default {};

    /**
     * Specifies types for component scan.
     */
    ComponentScan.Filter[] includeFilters() default {};

    /**
     * Specifies types for skipping component scan.
     */
    ComponentScan.Filter[] excludeFilters() default {};

    /**
     * Specifics the postfix to be used for custom repository implementation class name.
     */
    String repositoryImplementationPostfix() default DEFAULT_REPOSITORY_IMPLEMENT_POSTFIX;

    /**
     * Configures the repository base class to be used to create repository.
     */
    Class<?> repositoryBaseClass() default DefaultRepositoryBaseClass.class;

    /**
     * Configures whether nested repository interface.
     */
    boolean considerNestedRepositories() default false;

    /**
     * Configure the class of repository factory bean.
     */
    Class<?> repositoryFactoryBeanClass() default GremlinRepositoryFactoryBean.class;

    /**
     * Specific the namedQuery location.
     */
    String namedQueriesLocation() default "";
}
