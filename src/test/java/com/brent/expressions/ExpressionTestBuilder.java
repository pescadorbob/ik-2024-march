package com.brent.expressions;

import com.brent.expressions.domain.*;
import com.brent.expressions.parser.Parser;

import java.util.*;

import static java.lang.Integer.parseInt;

public class ExpressionTestBuilder {

    List<ExpressionElement> elements;

    public static ExpressionTestBuilder anExpression() {
        return new ExpressionTestBuilder();
    }

    public ExpressionTestBuilder() {
        elements = new ArrayList<>();
    }

    Expression build() {
        return new Expression(elements);
    }

    public ExpressionTestBuilder from(String tokenizedExpression) {
        var parser = new Parser();
        var tokens = parser.tokenize(tokenizedExpression);
        var lhsToken = tokens.getFirst();

        var operatorToken = tokens.get(1);
        var rhsToken = tokens.get(2);
        var lhsOperand = new VariableOperand(lhsToken);
        var operator = Parser.getOperator(operatorToken);
        var rhsOperand = new NumericOperand<>(parseInt(rhsToken));
        elements.add(lhsOperand);
        elements.add(operator);
        elements.add(rhsOperand);
        return this;
    }


}
