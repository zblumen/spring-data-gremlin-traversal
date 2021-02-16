package com.zblumenf.spring.data.gremlin.common;

import com.zblumenf.spring.data.gremlin.exception.GremlinIllegalConfigurationException;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.driver.ser.Serializers;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.springframework.lang.NonNull;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;

public class GremlinFactory {

    private Cluster gremlinCluster;

    private GremlinConfig gremlinConfig;

    private String traversalSourceName = Constants.DEFAULT_TRAVERSAL_SOURCE_NAME;

    private void setUp(@NonNull GremlinConfig gremlinConfig){
        final int port = gremlinConfig.getPort();
        if (port <= 0 || port > 65535) {
            gremlinConfig.setPort(Constants.DEFAULT_ENDPOINT_PORT);
        }

        final int maxContentLength = gremlinConfig.getMaxContentLength();
        if (maxContentLength <= 0) {
            gremlinConfig.setMaxContentLength(Constants.DEFAULT_MAX_CONTENT_LENGTH);
        }

        this.gremlinConfig = gremlinConfig;
    }

    public GremlinFactory(@NonNull GremlinConfig gremlinConfig) {
        setUp(gremlinConfig);
    }

    public GremlinFactory(@NonNull GremlinConfig gremlinConfig, @NonNull String traversalSourceName) {
        this.traversalSourceName = traversalSourceName;
        setUp(gremlinConfig);
    }

    private Cluster createGremlinCluster() throws GremlinIllegalConfigurationException {
        final Cluster cluster;

        try {
            cluster = Cluster.build(this.gremlinConfig.getEndpoint())
                    .serializer(Serializers.valueOf(this.gremlinConfig.getSerializer()).simpleInstance())
                    .credentials(this.gremlinConfig.getUsername(), this.gremlinConfig.getPassword())
                    .enableSsl(this.gremlinConfig.isSslEnabled())
                    .maxContentLength(this.gremlinConfig.getMaxContentLength())
                    .port(this.gremlinConfig.getPort())
                    .create();
        } catch (IllegalArgumentException e) {
            throw new GremlinIllegalConfigurationException("Invalid configuration of Gremlin", e);
        }

        return cluster;
    }

    public Client getGremlinClient() {

        if (this.gremlinCluster == null) {
            this.gremlinCluster = this.createGremlinCluster();
        }

        return this.gremlinCluster.connect();
    }

    public GraphTraversalSource generateGraphTraversalSource(){
        return generateGraphTraversalSource(this.traversalSourceName);
    }

    public GraphTraversalSource generateGraphTraversalSource(String traversalSourceName){
        return traversal().withRemote(DriverRemoteConnection.using(getGremlinClient(), traversalSourceName));
    }
}