package com.brent.expressions.domain;

public enum RelationshipType {
    NOT_EQUAL_TO("!="),
    EQUAL_TO("=="),
    LESS_THAN("<"),
    LESS_THAN_OR_EQUAL("<="),
    GREATER_THAN_OR_EQUAL(">="),
    GREATER_THAN(">");

    private final String symbol;

    RelationshipType(String symbol) {
        this.symbol = symbol;
    }

    public static RelationshipType fromSymbol(String symbol) {
        for (RelationshipType type : values()) {
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