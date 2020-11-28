package com.zblumenf.spring.data.gremlin.common;

import com.zblumenf.spring.data.gremlin.annotation.GeneratedValue;
import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSource;
import com.zblumenf.spring.data.gremlin.exception.GremlinIllegalConfigurationException;
import com.zblumenf.spring.data.gremlin.exception.GremlinInvalidEntityIdFieldException;
import com.zblumenf.spring.data.gremlin.exception.GremlinUnexpectedSourceTypeException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.tinkerpop.shaded.jackson.databind.MapperFeature;
import org.apache.tinkerpop.shaded.jackson.databind.ObjectMapper;
import org.springframework.data.annotation.Id;
import org.springframework.lang.NonNull;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GremlinUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(MapperFeature.AUTO_DETECT_FIELDS, false);
    }

    public static ObjectMapper getObjectMapper() {
        return mapper;
    }

    public static <T> T createInstance(@NonNull Class<T> type) {
        final T instance;

        try {
            instance = type.newInstance();
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("can not access type constructor", e);
        } catch (InstantiationException e) {
            throw new IllegalArgumentException("failed to create instance of given type", e);
        }

        return instance;
    }

    public static <T> Field getIdField(@NonNull Class<T> domainClass) {
        final Field idField;
        final List<Field> idFields = FieldUtils.getFieldsListWithAnnotation(domainClass, Id.class);
        final List<Field> generatedValueFields =
                FieldUtils.getFieldsListWithAnnotation(domainClass, GeneratedValue.class);

        if (generatedValueFields.size() > 1) {
            throw new GremlinIllegalConfigurationException("Only one field, the id field, can have the optional "
                    + "@GeneratedValue annotation!");
        }

        if (idFields.isEmpty()) {
            idField = ReflectionUtils.findField(domainClass, Constants.PROPERTY_ID_STRING);
        } else if (idFields.size() == 1) {
            idField = idFields.get(0);
        } else {
            throw new GremlinInvalidEntityIdFieldException("only one @Id field is allowed");
        }

        if (idField == null) {
            throw new GremlinInvalidEntityIdFieldException("no field named id in class");
        } else if (idField.getType() != String.class
                && idField.getType() != Long.class && idField.getType() != Integer.class) {
            throw new GremlinInvalidEntityIdFieldException("the type of @Id/id field should be String/Integer/Long");
        }

        if (generatedValueFields.size() == 1 && !generatedValueFields.get(0).equals(idField)) {
            throw new GremlinIllegalConfigurationException("Only the id field can have the optional "
                    + "@GeneratedValue annotation!");
        }

        return idField;
    }

    public static long timeToMilliSeconds(@NonNull Object time) {
        if (time instanceof Date) {
            return ((Date) time).getTime();
        } else {
            throw new UnsupportedOperationException("Unsupported time type");
        }
    }

    public static long toPrimitiveLong(@NonNull Object object) {
        if (object instanceof Date) {
            return timeToMilliSeconds(object);
        } else if (object instanceof Integer) {
            return (long) (int) object;
        } else if (object instanceof Long) {
            return (long) object;
        } else {
            throw new UnsupportedOperationException("Unsupported object type to long");
        }
    }

    public static Class<?> toEntityClass(@NonNull String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new GremlinUnexpectedSourceTypeException("failed to retrieve class: " + className, e);
        }
    }
}
