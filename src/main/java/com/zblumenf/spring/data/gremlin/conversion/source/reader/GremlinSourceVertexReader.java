package com.zblumenf.spring.data.gremlin.conversion.source.reader;

import com.zblumenf.spring.data.gremlin.common.Constants;
import com.zblumenf.spring.data.gremlin.common.GremlinUtils;
import com.zblumenf.spring.data.gremlin.conversion.MappingGremlinConverter;
import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSource;
import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSourceVertex;
import com.zblumenf.spring.data.gremlin.exception.GremlinUnexpectedSourceTypeException;
import com.zblumenf.spring.data.gremlin.mapping.GremlinPersistentEntity;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.model.ConvertingPropertyAccessor;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;

@NoArgsConstructor
public class GremlinSourceVertexReader extends AbstractGremlinSourceReader implements GremlinSourceReader {

    @Override
    public <T extends Object> T read(@NonNull Class<T> domainClass, @NonNull MappingGremlinConverter converter,
                                     @NonNull GremlinSource<T> source) {
        if (!(source instanceof GremlinSourceVertex)) {
            throw new GremlinUnexpectedSourceTypeException("should be instance of GremlinSourceVertex");
        }

        final T domain = GremlinUtils.createInstance(domainClass);
        final ConvertingPropertyAccessor accessor = converter.getPropertyAccessor(domain);
        final GremlinPersistentEntity persistentEntity = converter.getPersistentEntity(domainClass);

        for (final Field field : FieldUtils.getAllFields(domainClass)) {
            final PersistentProperty property = persistentEntity.getPersistentProperty(field.getName());
            if (property == null) {
                continue;
            }

            if (field.getName().equals(Constants.PROPERTY_ID_STRING) || field.getAnnotation(Id.class) != null) {
                accessor.setProperty(property, super.getGremlinSourceId(source));
            } else {
                final Object value = super.readProperty(property, source.getProperties().get(field.getName()));
                accessor.setProperty(property, value);
            }
        }

        return domain;
    }
}