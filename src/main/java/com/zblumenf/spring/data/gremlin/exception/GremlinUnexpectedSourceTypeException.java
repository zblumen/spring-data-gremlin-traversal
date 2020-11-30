package com.zblumenf.spring.data.gremlin.exception;

import org.springframework.dao.TypeMismatchDataAccessException;

public class GremlinUnexpectedSourceTypeException extends TypeMismatchDataAccessException {

    public GremlinUnexpectedSourceTypeException(String msg) {
        super(msg);
    }

    public GremlinUnexpectedSourceTypeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}