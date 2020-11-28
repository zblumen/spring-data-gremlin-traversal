package com.zblumenf.spring.data.gremlin.query.criteria;

public enum CriteriaType {
    IS_EQUAL,
    OR,
    AND,
    EXISTS,
    AFTER,
    BEFORE,
    BETWEEN;

    public static String criteriaTypeToGremlin(CriteriaType type) {
        switch (type) {
            case OR:
                return GREMLIN_PRIMITIVE_OR;
            case AND:
                return GREMLIN_PRIMITIVE_AND;
            case AFTER:
                return GREMLIN_PRIMITIVE_IS_GT;
            case BEFORE:
                return GREMLIN_PRIMITIVE_IS_LT;
            case BETWEEN:
                return GREMLIN_PRIMITIVE_IS_BETWEEN;
            default:
                throw new UnsupportedOperationException("Unsupported criteria type.");
        }
    }
}
