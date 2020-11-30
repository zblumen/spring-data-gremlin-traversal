package com.zblumenf.spring.data.gremlin.conversion.source.reader;

import com.zblumenf.spring.data.gremlin.common.GremlinUtils;
import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSource;
import com.zblumenf.spring.data.gremlin.exception.GremlinEntityInformationException;
import com.zblumenf.spring.data.gremlin.exception.GremlinUnexpectedEntityTypeException;
import lombok.NonNull;
import org.apache.tinkerpop.shaded.jackson.databind.JavaType;
import org.apache.tinkerpop.shaded.jackson.databind.type.TypeFactory;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;

public abstract class AbstractGremlinSourceReader {

    protected Object readProperty(@NonNull PersistentProperty property, @Nullable Object value) {
        final Class<?> type = property.getTypeInformation().getType();
        final JavaType javaType = TypeFactory.defaultInstance().constructType(property.getType());

        if (value == null) {
            return null;
        } else if (type == int.class || type == Integer.class
                || type == Boolean.class || type == boolean.class
                || type == String.class) {
            return value;
        } else if (type == Date.class) {
            Assert.isTrue(value instanceof Long, "Date store value must be instance of long");
            return new Date((Long) value);
        } else {
            final Object object;

            try {
                object = GremlinUtils.getObjectMapper().readValue(value.toString(), javaType);
            } catch (IOException e) {
                throw new GremlinUnexpectedEntityTypeException("Failed to read String to Object", e);
            }

            return object;
        }
    }

    protected Object getGremlinSourceId(@NonNull GremlinSource source) {
        if (!source.getId().isPresent()) {
            return null;
        }

        final Object id = source.getId().get();
        final Field idField = source.getIdField();

        if (idField.getType() == String.class) {
            return id.toString();
        } else if (idField.getType() == Integer.class) {
            Assert.isTrue(id instanceof Integer, "source Id should be Integer.");
            return id;
        } else if (idField.getType() == Long.class && id instanceof Integer) {
            return Long.valueOf((Integer) id);
        } else if (idField.getType() == Long.class && id instanceof Long) {
            return id;
        }

        throw new GremlinEntityInformationException("unsupported id field type: " + id.getClass().getSimpleName());
    }
}
