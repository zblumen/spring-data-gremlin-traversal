package com.zblumenf.spring.data.gremlin.exception;

public class GremlinInvalidEntityIdFieldException extends GremlinEntityInformationException {

    public GremlinInvalidEntityIdFieldException(String msg) {
        super(msg);
    }

    public GremlinInvalidEntityIdFieldException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
