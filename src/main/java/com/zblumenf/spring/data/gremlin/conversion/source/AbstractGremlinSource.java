package com.zblumenf.spring.data.gremlin.conversion.source;

import com.zblumenf.spring.data.gremlin.annotation.GeneratedValue;
import com.zblumenf.spring.data.gremlin.common.Constants;
import com.zblumenf.spring.data.gremlin.conversion.MappingGremlinConverter;
import com.zblumenf.spring.data.gremlin.conversion.result.GremlinResultsReader;
import com.zblumenf.spring.data.gremlin.conversion.source.reader.GremlinSourceReader;
import com.zblumenf.spring.data.gremlin.conversion.source.writer.GremlinSourceWriter;
import com.zblumenf.spring.data.gremlin.conversion.traversal.GremlinTraversalBuilder;
import com.zblumenf.spring.data.gremlin.exception.GremlinAnnotationTypeException;
import com.zblumenf.spring.data.gremlin.exception.GremlinInvalidEntityIdFieldException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.TextP;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractGremlinSource<T> implements GremlinSource<T> {

    @Setter
    private Object id;

    @Getter
    @Setter
    private String label;

    @Getter
    @Setter
    private String labelProperty;

    @Getter
    @Setter
    private Field idField;

    @Getter
    private Class<T> domainClass;

    @Getter
    @Setter
    private Map<String, Object> properties;

    @Getter
    @Setter
    private Map<String, P> propertyPs;

    @Getter
    @Setter
    private Map<String, TextP> propertyTextPs;


    @Setter(AccessLevel.PRIVATE)
    private GremlinTraversalBuilder traversalBuilder;

    @Setter(AccessLevel.PRIVATE)
    private GremlinSourceWriter sourceWriter;

    @Setter(AccessLevel.PRIVATE)
    private GremlinSourceReader sourceReader;

    @Setter(AccessLevel.PRIVATE)
    private GremlinResultsReader resultReader;

    protected AbstractGremlinSource() {
        this.properties = new HashMap<>();
        this.propertyPs = new HashMap<>();
        this.propertyTextPs = new HashMap<>();
    }

    protected AbstractGremlinSource(Class<T> domainClass) {
        this.domainClass = domainClass;
        this.properties = new HashMap<>();
        this.propertyPs = new HashMap<>();
        this.propertyTextPs = new HashMap<>();

        setProperty(Constants.GREMLIN_PROPERTY_CLASSNAME, domainClass.getName());
    }

    @Override
    public Optional<Object> getId() {
        return Optional.ofNullable(this.id);
    }

    /**
     * The type of Id keep the consistency with the result from gremlin server, for generate query correctly. So if the
     * id is ${@link GeneratedValue}, which may have different type against entity id field.
     *
     * @param id the given id from query.
     */
    @Override
    public void setId(Object id) {
        final Field idField = getIdField();

        if (idField == null) {
            throw new GremlinInvalidEntityIdFieldException("Id Field of GremlinSource cannot be null");
        }

        if (idField.isAnnotationPresent(GeneratedValue.class) && id instanceof String) {
            try {
                this.id = Long.valueOf((String) id); // Gremlin server default id type is Long.
            } catch (NumberFormatException ignore) {
                this.id = id;
            }
        } else {
            this.id = id;
        }
    }

    @Override
    public void setGremlinTraversalStrategy(@NonNull GremlinTraversalBuilder traversalBuilder) {
        this.setTraversalBuilder(traversalBuilder);
    }

    @Override
    public void setGremlinSourceWriter(@NonNull GremlinSourceWriter writer) {
        this.setSourceWriter(writer);
    }

    @Override
    public void setGremlinSourceReader(@NonNull GremlinSourceReader reader) {
        this.setSourceReader(reader);
    }

    @Override
    public void setGremlinResultReader(@NonNull GremlinResultsReader reader) {
        this.setResultReader(reader);
    }

    @Override
    public GremlinTraversalBuilder getGremlinTraversalBuilder() {
        return this.traversalBuilder;
    }

    @Override
    public void doGremlinSourceWrite(@NonNull Object domain, @NonNull MappingGremlinConverter converter) {
        Assert.notNull(this.sourceWriter, "the sourceWriter must be set before do writing");

        this.sourceWriter.write(domain, converter, this);
    }

    @Override
    public T doGremlinSourceRead(@NonNull Class<T> domainClass, @NonNull MappingGremlinConverter converter) {
        Assert.notNull(this.sourceReader, "the sourceReader must be set before do reading");

        return this.sourceReader.read(domainClass, converter, this);
    }

    @Override
    public void doGremlinResultRead(@NonNull List<Map<Object, Object>> results) {
        Assert.notNull(this.resultReader, "the resultReader must be set before do reading");

        this.resultReader.read(results, this);
    }

    private boolean hasProperty(String key) {
        return this.properties.get(key) != null;
    }

    @Override
    public void setProperty(String key, Object value) {
        if (this.hasProperty(key) && value == null) {
            this.properties.remove(key);
        } else {
            this.properties.put(key, value);
        }
    }

    @Override
    public void setPropertyP(String key, Class<? extends P> pClass) {
        try {
            this.propertyPs.put(key, (P<?>) pClass.getMethod("getP", null).invoke(null, null));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new GremlinAnnotationTypeException("Failed To Instantiate P", e);
        }
    }

    @Override
    public void setPropertyTextP(String key, Class<? extends TextP> textPClass) {
        try {
            this.propertyPs.put(key, (TextP) textPClass.getMethod("getTextP", null).invoke(null, null));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new GremlinAnnotationTypeException("Failed To Instantiate textP", e);
        }
    }
}
