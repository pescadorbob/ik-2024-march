package com.brent.expressions;

import com.brent.expressions.domain.ExpressionResult;

public class ExpressionResultError extends ExpressionResult {
    String error;
    public ExpressionResultError(String error) {
        super(0);
        this.error = error;
    }

    @Override
    public String toString() {
        return "error='" + error + '\'' ;
    }
}
