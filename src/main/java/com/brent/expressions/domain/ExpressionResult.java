package com.brent.expressions.domain;

public class ExpressionResult {
    private ResultType resultType;
    private boolean booleanResult;
    private Number numberResult;

    public ExpressionResult(boolean result) {
        this.booleanResult = result;
        this.resultType = ResultType.BOOLEAN;
    }

    public ExpressionResult(Number expectedResult) {
        this.numberResult = expectedResult;
        this.resultType = ResultType.NUMERICAL;
    }

    @Override
    public String toString() {
        if(numberResult!=null) return "" + numberResult;
        return "" + booleanResult;
    }

    public boolean isBooleanResult() {
        return booleanResult;
    }

    public Number getNumberResult() {
        return numberResult;
    }

    public ResultType getResultType() {
        return resultType;
    }
}
