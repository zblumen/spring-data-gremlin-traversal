package com.zblumenf.spring.data.gremlin.exception;

import org.springframework.dao.TypeMismatchDataAccessException;

public class GremlinAnnotationTypeException extends TypeMismatchDataAccessException {

    public GremlinAnnotationTypeException(String msg) {
        super(msg);
    }

    public GremlinAnnotationTypeException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
