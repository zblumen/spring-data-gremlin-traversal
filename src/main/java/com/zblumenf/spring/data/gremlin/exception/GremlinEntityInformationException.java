package com.zblumenf.spring.data.gremlin.exception;

import org.springframework.dao.TypeMismatchDataAccessException;

public class GremlinEntityInformationException extends TypeMismatchDataAccessException {

    public GremlinEntityInformationException(String msg) {
        super(msg);
    }

    public GremlinEntityInformationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}