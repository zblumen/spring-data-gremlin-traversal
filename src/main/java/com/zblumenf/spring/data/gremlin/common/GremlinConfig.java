package com.zblumenf.spring.data.gremlin.common;

import lombok.*;
import org.apache.tinkerpop.gremlin.driver.ser.Serializers;

@Getter
@Setter
@Builder(builderMethodName = "defaultBuilder")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class GremlinConfig {
    private String endpoint;

    private int port;

    private String username;

    private String password;

    private boolean sslEnabled;

    private boolean telemetryAllowed;

    private String serializer;

    private int maxContentLength;


    public static GremlinConfigBuilder builder(String endpoint, String username, String password) {
        return defaultBuilder()
                .endpoint(endpoint)
                .username(username)
                .password(password)
                .port(Constants.DEFAULT_ENDPOINT_PORT)
                .sslEnabled(true)
                .serializer(Serializers.GRAPHSON.toString())
                .telemetryAllowed(true);
    }
}