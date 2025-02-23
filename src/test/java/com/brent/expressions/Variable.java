package com.brent.expressions;

public class Variable extends ExpressionElement {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
