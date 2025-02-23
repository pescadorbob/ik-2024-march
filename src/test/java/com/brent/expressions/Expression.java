package com.brent.expressions;

import java.util.ArrayList;
import java.util.List;

public class Expression {
    List<ExpressionElement> expressionElements;
    public Expression(List<ExpressionElement> elements){
        expressionElements = new ArrayList<>(elements);
    }

    public Expression() {
        expressionElements = new ArrayList<>();
    }

    public List<ExpressionElement> elements() {
        return expressionElements;
    }

    public void add(ExpressionElement expressionElement) {
        expressionElements.add(expressionElement);
    }

    public ExpressionElement getLHS() {
        return expressionElements.getFirst();
    }

    public RelationshipOperator getRelationshipOperator() {
        return (RelationshipOperator) expressionElements.get(1);
    }

    public ExpressionElement getRHS() {
        return expressionElements.get(2);
    }
}
