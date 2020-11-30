package com.zblumenf.spring.data.gremlin.conversion.source;

import com.zblumenf.spring.data.gremlin.conversion.MappingGremlinConverter;
import com.zblumenf.spring.data.gremlin.conversion.result.GremlinResultsReader;
import com.zblumenf.spring.data.gremlin.conversion.source.reader.GremlinSourceReader;
import com.zblumenf.spring.data.gremlin.conversion.source.writer.GremlinSourceWriter;
import com.zblumenf.spring.data.gremlin.conversion.traversal.GremlinTraversalBuilder;
import lombok.NonNull;
import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.TextP;

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
     * Set the property Predicate map of domain
     */
    void setPropertyP(String key, Class<? extends P> pClass);

    /**
     * Set the property Text Predicate map of domain
     */
    void setPropertyTextP(String key, Class<? extends TextP> textPClass);

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
     * Get the label property name
     * some graph Providers have poor to failed performance on hasLabel() for multi-million/billion node graphs
     * For this reason we need the ability to use another property asd a label and this could be a string or T.label
     *
     * @return will never be null
     */
    @NonNull
    String getLabelProperty();

    /**
     * Set the label  property name
     */
    void setLabelProperty(String labelProperty);

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
     * Get The Custom Predicates for the properties
     *
     * @return will never be null
     */
    Map<String, P> getPropertyPs();

    /**
     * Get The Custom Text Predicates for the properties
     *
     * @return will never be null
     */
    Map<String, TextP> getPropertyTextPs();

    /**
     * do the real write from domain to GremlinSource
     */
    void doGremlinSourceWrite(Object domain, MappingGremlinConverter converter);

    /**
     * do the real reading from Result to GremlinSource
     */
    void doGremlinResultRead(List<Map<Object, Object>> results);

    /**
     * do the real reading from GremlinSource to domain
     */
    T doGremlinSourceRead(Class<T> domainClass, MappingGremlinConverter converter);

    /**
     * return the GremlinTraversalBuilder
     */
    GremlinTraversalBuilder getGremlinTraversalBuilder();

    /**
     * Set the script Strategy of GremlinSource
     */
   void setGremlinTraversalStrategy(GremlinTraversalBuilder traversalBuilder);

    /**
     * Set the SourceWriter of GremlinSource
     */
    void setGremlinSourceWriter(GremlinSourceWriter writer);

    /**
     * Set the ResultReader for reading data from Gremlin Result to GremlinSource
     */
    void setGremlinResultReader(GremlinResultsReader reader);

    /**
     * Set the SourceReader for reading data from GremlinSource to domain
     */
    void setGremlinSourceReader(GremlinSourceReader reader);
}
