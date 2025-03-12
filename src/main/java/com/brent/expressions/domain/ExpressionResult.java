package com.brent.expressions.domain;

public class ExpressionResult<T extends Comparable<T>> extends Operand<T> {

    public ExpressionResult(T result) {
        super(result);
    }

}
