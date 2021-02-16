package com.zblumenf.spring.data.gremlin.repository.support;

import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSource;
import com.zblumenf.spring.data.gremlin.query.GremlinOperations;
import com.zblumenf.spring.data.gremlin.repository.GremlinRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class SimpleGremlinRepository<T, ID extends Serializable> implements GremlinRepository<T, ID> {

    private final GremlinEntityInformation<T, ID> information;

    private final GremlinOperations operations;

    public SimpleGremlinRepository(GremlinEntityInformation<T, ID> information, @NonNull ApplicationContext context) {
        this(information, context.getBean(GremlinOperations.class));
    }

    public SimpleGremlinRepository(GremlinEntityInformation<T, ID> information, @NonNull GremlinOperations operations) {
        this.operations = operations;
        this.information = information;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends T> S save(@NonNull S domain) {
        final GremlinSource<T> source = this.information.createGremlinSource();

        source.setId(this.information.getId(domain));

        return (S) this.operations.save(domain, source);
    }

    @Override
    public <S extends T> Iterable<S> saveAll(@NonNull Iterable<S> domains) {
        return StreamSupport.stream(domains.spliterator(), true).map(this::save).collect(toList());
    }

    @Override
    public Iterable<T> findAll() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<T> findAllById(@NonNull Iterable<ID> ids) {
        return StreamSupport.stream(ids.spliterator(), true).map(this::findById)
                .filter(Optional::isPresent).map(Optional::get).collect(toList());
    }

    @Override
    public Optional<T> findById(@NonNull ID id) {
        final T domain = this.operations.findById(id, this.information.createGremlinSource());

        return domain == null ? Optional.empty() : Optional.of(domain);
    }


    /**
     * The total number of vertex and edge, vertexCount and edgeCount is also available.
     *
     * @return the count of both vertex and edge.
     */
    @Override
    public long count() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(@NonNull T domain) {
        this.operations.deleteById(this.information.getId(domain), this.information.createGremlinSource());
    }

    @Override
    public void deleteById(@NonNull ID id) {
        this.operations.deleteById(id, this.information.createGremlinSource());
    }

    @Override
    public void deleteAll() {
        this.operations.deleteAll(this.information.createGremlinSource());
    }


    @Override
    public void deleteAll(@NonNull Iterable<? extends T> domains) {
        domains.forEach(this::delete);
    }


    @Override
    public boolean existsById(@NonNull ID id) {
        return this.operations.existsById(id, this.information.createGremlinSource());
    }
}
