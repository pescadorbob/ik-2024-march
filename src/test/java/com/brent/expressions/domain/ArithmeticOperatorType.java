package com.brent.expressions.domain;

public enum ArithmeticOperatorType {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/");

    private final String symbol;

    ArithmeticOperatorType(String symbol) {
        this.symbol = symbol;
    }

    public static boolean isRepresentedBy(String symbol){
        boolean isRepresentedBy = false;
        for (ArithmeticOperatorType type : values()) {
            if (type.symbol.equals(symbol)) {
                isRepresentedBy = true;
                break;
            }
        }
        return isRepresentedBy;
    }
    public static ArithmeticOperatorType fromRepresentation(String symbol) {
        for (ArithmeticOperatorType type : values()) {
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