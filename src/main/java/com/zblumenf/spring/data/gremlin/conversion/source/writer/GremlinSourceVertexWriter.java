package com.zblumenf.spring.data.gremlin.conversion.source.writer;

import com.zblumenf.spring.data.gremlin.conversion.MappingGremlinConverter;
import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSource;
import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSourceVertex;
import com.zblumenf.spring.data.gremlin.exception.GremlinEntityInformationException;
import com.zblumenf.spring.data.gremlin.exception.GremlinUnexpectedSourceTypeException;
import com.zblumenf.spring.data.gremlin.mapping.GremlinPersistentEntity;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.model.ConvertingPropertyAccessor;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;

import static com.zblumenf.spring.data.gremlin.common.Constants.*;

@NoArgsConstructor
public class GremlinSourceVertexWriter implements GremlinSourceWriter {

    @Override
    public void write(@NonNull Object domain, @NonNull MappingGremlinConverter converter,
                      @NonNull GremlinSource source) {
        if (!(source instanceof GremlinSourceVertex)) {
            throw new GremlinUnexpectedSourceTypeException("should be the instance of GremlinSourceVertex");
        }

        source.setId(converter.getIdFieldValue(domain));

        final GremlinPersistentEntity<?> persistentEntity = converter.getPersistentEntity(domain.getClass());
        final ConvertingPropertyAccessor accessor = converter.getPropertyAccessor(domain);

        for (final Field field : FieldUtils.getAllFields(domain.getClass())) {
            final PersistentProperty property = persistentEntity.getPersistentProperty(field.getName());
            if (property == null) {
                continue;
            }

            if (field.getName().equals(PROPERTY_ID_STRING) || field.getAnnotation(Id.class) != null) {
                continue;
            } else if (field.getName().equals(GREMLIN_PROPERTY_CLASSNAME)) {
                throw new GremlinEntityInformationException("Domain Cannot use pre-defined field name: "
                        + GREMLIN_PROPERTY_CLASSNAME);
            }

            source.setProperty(field.getName(), accessor.getProperty(property));
        }
    }
}

