package com.zblumenf.spring.data.gremlin.query;

import com.zblumenf.spring.data.gremlin.common.GremlinFactory;
import com.zblumenf.spring.data.gremlin.conversion.MappingGremlinConverter;
import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSource;
import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSourceVertex;
import com.zblumenf.spring.data.gremlin.exception.GremlinUnexpectedEntityTypeException;
import com.zblumenf.spring.data.gremlin.query.query.GremlinQuery;
import com.zblumenf.spring.data.gremlin.query.query.QueryFindTraversalGenerator;
import com.zblumenf.spring.data.gremlin.query.query.QueryTraversalGenerator;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.process.traversal.step.util.WithOptions;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class GremlinTraversalTemplate implements GremlinOperations, ApplicationContextAware {

    private final GremlinFactory factory;
    private final MappingGremlinConverter mappingConverter;

    private Client gremlinClient;
    private ApplicationContext context;

    public GremlinTraversalTemplate(@NonNull GremlinFactory factory, @NonNull MappingGremlinConverter converter) {
        this.factory = factory;
        this.mappingConverter = converter;
    }

    @NonNull
    private List<Map<Object, Object>> executeQueryToValueMapList(@NonNull GraphTraversal traversal) {
        return new ArrayList<Map<Object, Object>>(traversal.valueMap().with(WithOptions.tokens).toList());
    }

    @NonNull
    private void executeQueryToIterate(@NonNull GraphTraversal traversal) {
        traversal.iterate();
    }

    private <T> T recoverDomain(@NonNull GremlinSource<T> source, @NonNull List<Map<Object, Object>> results) {
        final T domain;
        final Class<T> domainClass = source.getDomainClass();

        source.doGremlinResultRead(results);
        domain = this.mappingConverter.read(domainClass, source);

        /*if (source instanceof GremlinSourceEdge) {
            this.completeEdge(domain, (GremlinSourceEdge) source);
        }*/

        return domain;
    }

    private <T> List<T> recoverDomainList(@NonNull GremlinSource<T> source, @NonNull List<Map<Object, Object>> results) {
        return results.stream().map(r -> recoverDomain(source, Collections.singletonList(r))).collect(toList());
    }

    public ApplicationContext getApplicationContext() {
        return this.context;
    }

    @Override
    public MappingGremlinConverter getMappingConverter() {
        return this.mappingConverter;
    }



    @Override
    public void setApplicationContext(@NonNull ApplicationContext context) throws BeansException {
        this.context = context;
    }

    public Client getGremlinClient() {
        if (this.gremlinClient == null) {
            this.gremlinClient = this.factory.getGremlinClient();
        }

        return this.gremlinClient;
    }

    private <T> T findByIdInternal(@NonNull GremlinSource<T> source) {
        final GraphTraversal traversal = source
                .getGremlinTraversalBuilder()
                .buildFindByIdTraversal(source, this.factory.generateGraphTraversalSource());
        final List<Map<Object, Object>> results = this.executeQueryToValueMapList(traversal);

        if (results.isEmpty()) {
            return null;
        }

        return recoverDomain(source, results);
    }

    @Override
    public <T> T findVertexById(@NonNull Object id, GremlinSource<T> source) {
        if (source instanceof GremlinSourceVertex) {
            source.setId(id);
            return this.findByIdInternal(source);
        }

        throw new GremlinUnexpectedEntityTypeException("should be vertex domain for findVertexById");
    }

    @Override
    public <T> void deleteById(@NonNull Object id, @NonNull GremlinSource<T> source) {
        source.setId(id);

        final GraphTraversal traversal = source
                .getGremlinTraversalBuilder()
                .buildDeleteByIdTraversal(source, this.factory.generateGraphTraversalSource());

        executeQueryToIterate(traversal);
    }

    private <T> List<Map<Object, Object>> insertInternal(@NonNull T object, @NonNull GremlinSource<T> source) {
        this.mappingConverter.write(object, source);

        final GraphTraversal traversal = source
                .getGremlinTraversalBuilder()
                .buildInsertTraversal(source, this.factory.generateGraphTraversalSource());
        return this.executeQueryToValueMapList(traversal);
    }

    @Override
    public <T> T insert(@NonNull T object, GremlinSource<T> source) {
        /*final boolean entityGraph = source instanceof GremlinSourceGraph;

        if (!entityGraph && source.getIdField().isAnnotationPresent(GeneratedValue.class)
                && source.getId().isPresent()) {
            throw new GremlinInvalidEntityIdFieldException("The entity meant to be created has a non-null id "
                    + "that is marked as @GeneratedValue");
        }*/

        // The current implementation doesn't support creating graphs that contain both edges
        // and vertices that have null (generated) ids. In this case, vertex and edge creation
        // need to be performed in two consecutive steps.
        // TODO(SOON) Add this verification in the GremlinSourceGraphWriter

        final List<Map<Object, Object>> results = insertInternal(object, source);

        if (!results.isEmpty()) {
            return recoverDomain(source, results);
            /*if (entityGraph) {
                return recoverGraphDomain((GremlinSourceGraph<T>) source, results);
            } else {
                return recoverDomain(source, results);
            }*/
        }
        return null;
    }

    @Override
    public <T> T findById(@NonNull Object id, @NonNull GremlinSource<T> source) {
       /* if (source instanceof GremlinSourceGraph) {
            throw new UnsupportedOperationException("Gremlin graph cannot be findById.");
        }*/

        source.setId(id);

        return findByIdInternal(source);
    }

    @Override
    public <T> boolean existsById(@NonNull Object id, @NonNull GremlinSource<T> source) {
        return findById(id, source) != null;
    }

    @Override
    public <T> List<T> find(@NonNull GremlinQuery query, @NonNull GremlinSource<T> source) {
        final QueryTraversalGenerator generator = new QueryFindTraversalGenerator(source);
        final GraphTraversal traversal = generator.generate(query, this.factory.generateGraphTraversalSource());
        final List<Map<Object, Object>> results = this.executeQueryToValueMapList(traversal);

        if (results.isEmpty()) {
            return Collections.emptyList();
        }

        return this.recoverDomainList(source, results);
    }
}
