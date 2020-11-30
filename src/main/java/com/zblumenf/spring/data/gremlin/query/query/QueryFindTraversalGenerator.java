package com.zblumenf.spring.data.gremlin.query.query;

import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSource;
import com.zblumenf.spring.data.gremlin.conversion.source.GremlinSourceVertex;
import com.zblumenf.spring.data.gremlin.query.criteria.Criteria;
import com.zblumenf.spring.data.gremlin.query.criteria.CriteriaType;
import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
import org.springframework.lang.NonNull;

import static com.zblumenf.spring.data.gremlin.common.Constants.*;


//TODO: Needs Serious Clean Up
public class QueryFindTraversalGenerator extends AbstractGremlinTraversalGenerator implements QueryTraversalGenerator {

    private final GremlinSource source;

    public QueryFindTraversalGenerator(@NonNull GremlinSource source) {
        this.source = source;
    }

    private String getCriteriaSubject(@NonNull Criteria criteria) {
        String subject = criteria.getSubject();

        if (subject.equals(this.source.getIdField().getName())) {
            subject = PROPERTY_ID_STRING; // If subject is @Id/id field, use id property in database.
        }

        return subject;
    }

    private GraphTraversal generateIsEqualTraversal(@NonNull Criteria criteria, GraphTraversal traversal) {
        final String subject = getCriteriaSubject(criteria);

        if (subject.equals(PROPERTY_ID)) {
            return generateHasId(traversal, criteria.getSubValues().get(0));
        } else {
            if(source.getPropertyPs().containsKey(subject)){
                P<?> p = (P<?>)source.getPropertyPs().get(subject);
                return generateHasPredicateValue(traversal, subject, p.eq(convertToQueryValue(criteria.getSubValues().get(0))));
            }
            return generateHas(traversal, subject, criteria.getSubValues().get(0));
        }
    }

    private void addOrTraversal(GraphTraversal parentTraversal, GraphTraversal[] subTraversals){
        parentTraversal.or(subTraversals);
    }


    private void addTraversalStepsFromCriteria(@NonNull Criteria criteria, GraphTraversal traversal){
        final CriteriaType type = criteria.getType();
        final String subject = this.getCriteriaSubject(criteria);
        final boolean hasP = source.getPropertyPs().containsKey(subject);
        final P<?> p;
        if(hasP){
            p = (P<?>)source.getPropertyPs().get(subject);
        }else{
            p = P.eq(0); //'this will generate a predicate for staic functional use at least
        }

        switch (type) {
            case IS_EQUAL:
                generateIsEqualTraversal(criteria, traversal);
                break;

            case AND:
                this.addTraversalStepsFromCriteria(criteria.getSubCriteria().get(0), traversal);
                this.addTraversalStepsFromCriteria(criteria.getSubCriteria().get(1), traversal);
                break;

            case OR:
                GraphTraversal[] anonymousTraversals = new GraphTraversal[]{__.start(), __.start()};
                this.addTraversalStepsFromCriteria(criteria.getSubCriteria().get(0), anonymousTraversals[0]);
                this.addTraversalStepsFromCriteria(criteria.getSubCriteria().get(1), anonymousTraversals[1]);
                addOrTraversal(traversal, anonymousTraversals);
                break;

            case AFTER:
                generateHasPredicateValue(traversal, subject, p.gt(convertToQueryValue(criteria.getSubValues().get(0))));
                break;

            case BEFORE:
                generateHasPredicateValue(traversal, subject, p.lt(convertToQueryValue(criteria.getSubValues().get(0))));
                break;

            case BETWEEN:
                generateHasPredicateValue(traversal, subject,
                        p.between(convertToQueryValue(criteria.getSubValues().get(0)), convertToQueryValue(criteria.getSubValues().get(1))));
                break;

            case EXISTS:
                generateHas(traversal, subject, true);
                break;

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

        addHasLabel(traversal, source);

        addTraversalStepsFromCriteria(criteria, traversal);

        return traversal;
    }


    @Override
    @SuppressWarnings("unchecked")
    public GraphTraversal generate(@NonNull GremlinQuery query, GraphTraversalSource g) {
        return generateTraversal(query, g);
    }
}
