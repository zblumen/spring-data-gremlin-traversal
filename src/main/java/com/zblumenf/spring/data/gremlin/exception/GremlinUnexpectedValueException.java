package com.zblumenf.spring.data.gremlin.exception;

public class GremlinUnexpectedValueException extends GremlinEntityInformationException{
    public GremlinUnexpectedValueException(String msg) {
        super(msg);
    }

    public GremlinUnexpectedValueException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
