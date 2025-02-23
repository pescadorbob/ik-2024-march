package com.brent.expressions;

public class Literal extends ExpressionElement {
    private final String value;

    public Literal(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
