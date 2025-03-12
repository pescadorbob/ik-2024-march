package com.brent.expressions.domain;

public class BinomialExpression implements ExpressionElement {
    ExpressionElement lhs;
    ExpressionElement operator;
    ExpressionElement rhs;


    public BinomialExpression(ExpressionElement lhs, ExpressionElement operator, ExpressionElement rhs) {
        assert lhs !=null;
        assert operator !=null;
        assert rhs !=null;
        this.lhs = lhs;
        this.operator = operator;
        this.rhs = rhs;
    }

    public BinomialExpression() {
    }


    public ExpressionElement getLHS() {
        return lhs;
    }

    public Operator getOperator() {
        return (Operator) operator;
    }

    public ExpressionElement getRHS() {
        return rhs;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s",lhs,operator,rhs );
    }

    public void setLHS(ExpressionElement expressionElement) {
        this.lhs = expressionElement;
    }

    public void setRHS(ExpressionElement expressionElement) {
        this.rhs = expressionElement;
    }

    public void setOperator(ExpressionElement operator) {
        this.operator = operator;
    }
}
