package com.brent.expressions.domain;

public class ExpressionResult {
    private boolean booleanResult;
    private Number numberResult;

    public ExpressionResult(boolean result) {
        this.booleanResult = result;
    }

    public ExpressionResult(Number expectedResult) {
        this.numberResult = expectedResult;
    }

    @Override
    public String toString() {
        if(numberResult!=null) return "" + numberResult;
        return "" + booleanResult;
    }
}
