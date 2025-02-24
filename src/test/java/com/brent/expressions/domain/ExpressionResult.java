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

    public boolean isBooleanResult() {
        return booleanResult;
    }

    public Number getNumberResult() {
        return numberResult;
    }

    @Override
    public String toString() {
        if(numberResult!=null) return "" + numberResult;
        return "" + booleanResult;
    }
}
