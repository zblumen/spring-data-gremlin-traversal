package com.zblumenf.spring.data.gremlin.conversion.source.writer;

import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSource;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;

public abstract class AbstractPropertyWriter {

    protected static void setPropertyFromField(@NonNull GremlinSource source, @NonNull Field field, Object value) {
        source.setProperty(field.getName(), value);
    }

}
