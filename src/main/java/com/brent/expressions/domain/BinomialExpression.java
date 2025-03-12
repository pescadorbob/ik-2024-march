package com.brent.expressions.domain;

public class BinomialExpression implements Expression {
    Expression lhs;
    Expression operator;
    Expression rhs;


    public BinomialExpression(Expression lhs, Expression operator, Expression rhs) {
        assert lhs !=null;
        assert operator !=null;
        assert rhs !=null;
        this.lhs = lhs;
        this.operator = operator;
        this.rhs = rhs;
    }

    public BinomialExpression() {
    }


    public Expression getLHS() {
        return lhs;
    }

    public Operator getOperator() {
        return (Operator) operator;
    }

    public Expression getRHS() {
        return rhs;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s",lhs,operator,rhs );
    }

    public void setLHS(Expression expression) {
        this.lhs = expression;
    }

    public void setRHS(Expression expression) {
        this.rhs = expression;
    }

    public void setOperator(Expression operator) {
        this.operator = operator;
    }
}
