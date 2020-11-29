package com.zblumenf.spring.data.gremlin.query.query;

import com.zblumenf.spring.data.gremlin.common.GremlinUtils;
import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSource;
import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSourceVertex;
import com.zblumenf.spring.data.gremlin.conversion.traversal.AbstractGremlinTraversalBuilder;
import com.zblumenf.spring.data.gremlin.conversion.traversal.GremlinTraversalBuilder;
import com.zblumenf.spring.data.gremlin.query.criteria.Criteria;
import com.zblumenf.spring.data.gremlin.query.criteria.CriteriaType;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.zblumenf.spring.data.gremlin.common.Constants.*;

public class QueryFindTraversalGenerator implements QueryTraversalGenerator {

    private final GremlinSource source;

    public QueryFindTraversalGenerator(@NonNull GremlinSource source) {
        this.source = source;
    }

    private String getCriteriaSubject(@NonNull Criteria criteria) {
        String subject = criteria.getSubject();

        if (subject.equals(this.source.getIdField().getName())) {
            subject = PROPERTY_ID; // If subject is @Id/id field, use id property in database.
        }

        return subject;
    }

    private GraphTraversal generateIsEqual(@NonNull Criteria criteria, GraphTraversal traversal) {
        final String subject = getCriteriaSubject(criteria);

        if (subject.equals(PROPERTY_ID)) {
            return AbstractGremlinTraversalBuilder.generateHasId(traversal, criteria.getSubValues().get(0)));
        } else {
            return AbstractGremlinTraversalBuilder.generateHas(traversal, subject, criteria.getSubValues().get(0));
        }
    }

    /**
     * Generate script with only one subject and no subValue, like findByActiveExists().
     *
     * @param criteria given query represent a query subject
     * @return simple script with keyword from criteria type
     */
    private String generateEmptyScript(@NonNull Criteria criteria) {
        final String subject = this.getCriteriaSubject(criteria);
        final String has = generateHas(subject, true);

        return String.format(GREMLIN_PRIMITIVE_WHERE, has);
    }

    /**
     * Generate script with only one subject and only one subValue, like findByCreateAtBefore(Date start).
     *
     * @param criteria given query represent a query subject
     * @return simple script with keyword from criteria type
     */
    private void generateBeforeOrAfterTraversal(@NonNull Criteria criteria, GraphTraversal traversal) {
        final CriteriaType type = criteria.getType();
        final String subject = this.getCriteriaSubject(criteria);
        final long milliSeconds = GremlinUtils.timeToMilliSeconds(criteria.getSubValues().get(0));

        final String values = String.format(GREMLIN_PRIMITIVE_VALUES, subject);
        final String query = String.format(CriteriaType.criteriaTypeToGremlin(type), milliSeconds);
        final String content = String.join(GREMLIN_PRIMITIVE_INVOKE, values, query);

        return String.format(GREMLIN_PRIMITIVE_WHERE, content);
    }

    /**
     * Generate script with only one subject and two subValue, like findByCreateAtBetween(Date start, Date end).
     *
     * @param criteria given query represent a query subject
     * @return simple script with keyword from criteria type
     */
    private String generateDoubleScript(Criteria criteria) {
        final CriteriaType type = criteria.getType();
        final String subject = this.getCriteriaSubject(criteria);
        final long start = GremlinUtils.toPrimitiveLong(criteria.getSubValues().get(0));
        final long end = GremlinUtils.toPrimitiveLong(criteria.getSubValues().get(1));

        final String values = String.format(GREMLIN_PRIMITIVE_VALUES, subject);
        final String query = String.format(CriteriaType.criteriaTypeToGremlin(type), start, end);
        final String content = String.join(GREMLIN_PRIMITIVE_INVOKE, values, query);

        return String.format(GREMLIN_PRIMITIVE_WHERE, content);
    }

    /**
     * Generate script combined by AND/OR keyword.
     *
     * @param left  sub script on left
     * @param right sub script on right
     * @param type  should be AND/OR
     * @return combined script with AND/OR
     */
    private String generateCombinedScript(@NonNull String left, @NonNull String right, CriteriaType type) {
        final String operation = CriteriaType.criteriaTypeToGremlin(type);
        final String content = String.join(GREMLIN_PRIMITIVE_INVOKE, left, operation, right);

        return String.format(GREMLIN_PRIMITIVE_WHERE, content);
    }


    private void addTraversalStepsFromCriteria(@NonNull Criteria criteria, GraphTraversal traversal){
        final CriteriaType type = criteria.getType();

        switch (type) {
            case IS_EQUAL:
                generateIsEqual(criteria, traversal);
                break;
            case AND:
            case OR:
                final String left = this.generateScriptTraversal(criteria.getSubCriteria().get(0));
                final String right = this.generateScriptTraversal(criteria.getSubCriteria().get(1));

                return this.generateCombinedScript(left, right, type);
            case AFTER:
            case BEFORE:
                return this.generateSingleScript(criteria);
            case BETWEEN:
                return this.generateDoubleScript(criteria);
            case EXISTS:
                return this.generateEmptyScript(criteria);
            default:
                throw new UnsupportedOperationException("unsupported Criteria type");
        }
    }


    private String generateScriptTraversal(@NonNull Criteria criteria) {
        final CriteriaType type = criteria.getType();

        switch (type) {
            case IS_EQUAL:
                return this.generateIsEqual(criteria);
            case AND:
            case OR:
                final String left = this.generateScriptTraversal(criteria.getSubCriteria().get(0));
                final String right = this.generateScriptTraversal(criteria.getSubCriteria().get(1));

                return this.generateCombinedScript(left, right, type);
            case AFTER:
            case BEFORE:
                return this.generateSingleScript(criteria);
            case BETWEEN:
                return this.generateDoubleScript(criteria);
            case EXISTS:
                return this.generateEmptyScript(criteria);
            default:
                throw new UnsupportedOperationException("unsupported Criteria type");
        }
    }

    private GraphTraversal generateTraversal(@NonNull GremlinQuery query, GraphTraversalSource g) {
        final Criteria criteria = query.getCriteria();
        GraphTraversal traversal;
        if (this.source instanceof GremlinSourceVertex) {
            traversal = g.V();
        //} else if (this.source instanceof GremlinSourceEdge) {
       //     scriptList.add(GREMLIN_PRIMITIVE_EDGE_ALL);
        } else {
            throw new UnsupportedOperationException("Cannot generate script from graph entity");
        }

        if(this.source.getLabelProperty().equals(DEFAULT_LABEL_PROPERTY_KEY)){
            traversal.hasLabel(this.source.getLabel());
        }else{
            traversal.has(this.source.getLabelProperty(), this.source.getLabel());
        }


        final List<String> scriptList = new ArrayList<>();

        scriptList.add(GREMLIN_PRIMITIVE_GRAPH);

        if (this.source instanceof GremlinSourceVertex) {
            scriptList.add(GREMLIN_PRIMITIVE_VERTEX_ALL);
        } else if (this.source instanceof GremlinSourceEdge) {
            scriptList.add(GREMLIN_PRIMITIVE_EDGE_ALL);
        } else {
            throw new UnsupportedOperationException("Cannot generate script from graph entity");
        }

        scriptList.add(this.generateScriptTraversal(criteria));

        return traversal;
    }


    @Override
    @SuppressWarnings("unchecked")
    public GraphTraversal generate(@NonNull GremlinQuery query, GraphTraversalSource g) {
        return generateTraversal(query, g)
    }
}
