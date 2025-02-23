package com.brent.expressions;

import java.util.ArrayList;
import java.util.List;

public class ExpressionTestBuilder {

    List<ExpressionElement> elements;

    public static ExpressionTestBuilder anExpression(){
        return new ExpressionTestBuilder();
    }
    public ExpressionTestBuilder(){
        elements = new ArrayList<>();
    }
    Expression build(){
        return new Expression();
    }

    public ExpressionTestBuilder from(String tokenizedExpression) {
        var tokens = tokenizedExpression.split(" ");
        var lhs = tokens[0];
        var relation = tokens[1];
        var rhs = tokens[2];
        var variable = new Variable(lhs);
        var relationshipOperator = new RelationshipOperator(relation);
        var literal = new Literal(rhs);
        elements.add(variable);
        elements.add(relationshipOperator);
        elements.add(literal);
        return this;
    }

}
