package com.zblumenf.spring.data.gremlin.common;

import org.apache.tinkerpop.gremlin.structure.T;

public class Constants {
    public static final String DEFAULT_VERTEX_LABEL = "";
    public static final String DEFAULT_EDGE_LABEL = "";
    public static final String DEFAULT_COLLECTION_NAME = "";
    public static final String DEFAULT_LABEL_PROPERTY_KEY = "_t_label_default";
    public static final int DEFAULT_ENDPOINT_PORT = 443;
    public static final String DEFAULT_REPOSITORY_IMPLEMENT_POSTFIX = "Impl";
    public static final String DEFAULT_TRAVERSAL_SOURCE_NAME = "g";
    public static final int DEFAULT_MAX_CONTENT_LENGTH = 65536;

    public static final String GREMLIN_MODULE_NAME = "Gremlin";
    public static final String GREMLIN_MODULE_PREFIX = "gremlin";
    public static final String GREMLIN_MAPPING_CONTEXT = "gremlinMappingContext";

    public static final String GREMLIN_PRIMITIVE_AND = "and()";
    public static final String GREMLIN_PRIMITIVE_OR = "or()";
    public static final String GREMLIN_PRIMITIVE_WHERE = "where(%s)";

    public static final String GREMLIN_QUERY_BARRIER = "barrier";

    public static final String GREMLIN_PRIMITIVE_VALUES = "values('%s')";
    public static final String GREMLIN_PRIMITIVE_IS = "is(%s)";
    public static final String GREMLIN_PRIMITIVE_GT = "gt(%d)";
    public static final String GREMLIN_PRIMITIVE_LT = "lt(%d)";
    public static final String GREMLIN_PRIMITIVE_BETWEEN = "between(%d, %d)";

    public static final String GREMLIN_PRIMITIVE_IS_GT = String.format(GREMLIN_PRIMITIVE_IS, GREMLIN_PRIMITIVE_GT);
    public static final String GREMLIN_PRIMITIVE_IS_LT = String.format(GREMLIN_PRIMITIVE_IS, GREMLIN_PRIMITIVE_LT);
    public static final String GREMLIN_PRIMITIVE_IS_BETWEEN = String.format(
            GREMLIN_PRIMITIVE_IS,
            GREMLIN_PRIMITIVE_BETWEEN
    );

    public static final String GREMLIN_PROPERTY_CLASSNAME = "_classname";

    public static final T PROPERTY_ID = T.id;
    public static final T PROPERTY_LABEL = T.label;

    public static final String PROPERTY_ID_STRING = "id";
    public static final String PROPERTY_LABEL_STRING = "label";
}
