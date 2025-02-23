package com.brent.expressions.domain;

public enum RelationshipOperatorType {
    NOT_EQUAL_TO("!="),
    EQUAL_TO("=="),
    LESS_THAN("<"),
    LESS_THAN_OR_EQUAL("<="),
    GREATER_THAN_OR_EQUAL(">="),
    GREATER_THAN(">");

    private final String symbol;

    RelationshipOperatorType(String symbol) {
        this.symbol = symbol;
    }

    public static RelationshipOperatorType fromRepresentation(String symbol) {
        for (RelationshipOperatorType type : values()) {
            if (type.symbol.equals(symbol)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown relation symbol: " + symbol);
    }

    public String getSymbol() {
        return symbol;
    }
}