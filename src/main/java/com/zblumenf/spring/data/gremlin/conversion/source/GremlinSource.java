package com.zblumenf.spring.data.gremlin.conversion.source;

import lombok.NonNull;
import org.apache.tinkerpop.gremlin.driver.Result;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GremlinSource<T> {

    /**
     * Set the property map of domain
     */
    void setProperty(String key, Object value);

    /**
     * Get the id of domain
     *
     * @return the Optional of id
     */
    Optional<Object> getId();

    /**
     * Set the id of domain
     */
    void setId(Object id);

    /**
     * Get the id Field of domain
     *
     * @return will never be null
     */
    Field getIdField();

    /**
     * Set the id of domain
     */
    void setIdField(Field id);

    /**
     * Get the label of domain
     *
     * @return will never be null
     */
    @NonNull
    String getLabel();

    /**
     * Set the label of domain
     */
    void setLabel(String label);

    /**
     * Get the Class type of domain
     *
     * @return will never be null
     */
    @NonNull
    Class<T> getDomainClass();

    /**
     * Get the properties of domain
     *
     * @return will never be null
     */
    Map<String, Object> getProperties();

    /**
     * do the real write from domain to GremlinSource
     */
    //void doGremlinSourceWrite(Object domain, MappingGremlinConverter converter);

    /**
     * do the real reading from Result to GremlinSource
     */
    //void doGremlinResultRead(List<Result> results);

    /**
     * do the real reading from GremlinSource to domain
     */
    //T doGremlinSourceRead(Class<T> domainClass, MappingGremlinConverter converter);

    /**
     * return the GremlinScriptLiteral
     */
    //GremlinScriptLiteral getGremlinScriptLiteral();

    /**
     * Set the script Strategy of GremlinSource
     */
   //void setGremlinScriptStrategy(GremlinScriptLiteral script);

    /**
     * Set the SourceWriter of GremlinSource
     */
    //void setGremlinSourceWriter(GremlinSourceWriter writer);

    /**
     * Set the ResultReader for reading data from Gremlin Result to GremlinSource
     */
    //void setGremlinResultReader(GremlinResultsReader reader);

    /**
     * Set the SourceReader for reading data from GremlinSource to domain
     */
    //void setGremlinSourceReader(GremlinSourceReader reader);
}
