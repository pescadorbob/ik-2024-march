package com.brent.expressions;

public class RelationshipOperator extends ExpressionElement {
    RelationshipType type;
    public RelationshipOperator(String relation) {
        type = RelationshipType.fromSymbol(relation);
    }
}

enum RelationshipType {
    LESS_THAN("<"),
    GREATER_THAN(">");

    private final String representation;

    RelationshipType(String representation) {
        this.representation = representation;
    }

    public static RelationshipType fromSymbol(String symbol) {
        for (RelationshipType type : values()) {
            if (type.representation.equals(symbol)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown relation symbol: " + symbol);
    }
}