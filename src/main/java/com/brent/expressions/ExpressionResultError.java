package com.brent.expressions;

import com.brent.expressions.domain.ExpressionResult;

public class ExpressionResultError<T extends Comparable<T>> extends ExpressionResult<T> {
    String error;
    public ExpressionResultError(String error) {
        super(null);
        this.error = error;
    }

    @Override
    public String toString() {
        return "error='" + error + '\'' ;
    }
}
