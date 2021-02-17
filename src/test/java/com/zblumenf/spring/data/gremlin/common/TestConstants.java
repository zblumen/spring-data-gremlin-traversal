package com.zblumenf.spring.data.gremlin.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestConstants {

    public static final int DEFAULT_ENDPOINT_PORT = 443;
    public static final int ILLEGAL_ENDPOINT_PORT = -1;
    public static final String FAKE_ENDPOINT = "XXX-xxx.XXX-xxx.cosmosdb.azure.com";
    public static final String FAKE_USERNAME = "XXX-xxx.username";
    public static final String FAKE_PASSWORD = "XXX-xxx.password";
    public static final String EMPTY_STRING = "";

    public static final String VERTEX_PERSON_LABEL = "label-person";

    public static final String VERTEX_PERSON_ID = "233333";
    public static final String VERTEX_PERSON_NAME = "incarnation-p-lee";

    public static final String VERTEX_PERSON_0_ID = "000000";
    public static final String VERTEX_PERSON_0_NAME = "silencer";

    public static final String VERTEX_PERSON_1_ID = "111111";
    public static final String VERTEX_PERSON_1_NAME = "templar-assassin";

    public static final String VERTEX_PROJECT_LABEL = "label-project";

    public static final String VERTEX_PROJECT_ID = "666666";
    public static final String VERTEX_PROJECT_NAME = "spring-data-gremlin";
    public static final String VERTEX_PROJECT_URI = "https://github.com/Incarnation-p-lee/spring-data-gremlin.git";

    public static final String VERTEX_PROJECT_0_ID = "222222";
    public static final String VERTEX_PROJECT_0_NAME = "spring-data-documentdb";
    public static final String VERTEX_PROJECT_0_URI = "https://github.com/Microsoft/spring-data-documentdb";

    public static final String VERTEX_SERVICE_LABEL = "label-service";

    public static final String VERTEX_LABEL = "label-vertex";
    public static final String VERTEX_ADVANCED_USER_LABEL = "label-advanced-user";
    public static final String VERTEX_BOOK_LABEL = "label-book";
    public static final String VERTEX_GROUP_OWNER_LABEL = "label-group-owner";
    public static final String VERTEX_LIBRARY_LABEL = "label-library";
}
