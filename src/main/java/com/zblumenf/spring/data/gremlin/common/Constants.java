package com.zblumenf.spring.data.gremlin.common;

import org.apache.tinkerpop.gremlin.structure.T;

public class Constants {
    public static final String DEFAULT_VERTEX_LABEL = "";
    public static final String DEFAULT_EDGE_LABEL = "";
    public static final String DEFAULT_COLLECTION_NAME = "";
    public static final int DEFAULT_ENDPOINT_PORT = 443;
    public static final String DEFAULT_REPOSITORY_IMPLEMENT_POSTFIX = "Impl";
    public static final String DEFAULT_TRAVERSAL_SOURCE_NAME = "g";
    public static final int DEFAULT_MAX_CONTENT_LENGTH = 65536;

    public static final String GREMLIN_PROPERTY_CLASSNAME = "_classname";

    public static final T PROPERTY_ID = T.id;
    public static final T PROPERTY_LABEL = T.label;

    public static final String PROPERTY_ID_STRING = "id";
    public static final String PROPERTY_LABEL_STRING = "label";
}
