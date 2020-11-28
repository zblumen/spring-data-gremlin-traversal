package com.zblumenf.spring.data.gremlin.exception;

import org.springframework.dao.InvalidDataAccessApiUsageException;

public class GremlinIllegalConfigurationException extends InvalidDataAccessApiUsageException {

    public GremlinIllegalConfigurationException(String msg) {
        super(msg);
    }

    public GremlinIllegalConfigurationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

