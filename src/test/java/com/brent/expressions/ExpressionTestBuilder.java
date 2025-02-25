package com.brent.expressions;

import com.brent.expressions.domain.Expression;
import com.brent.expressions.domain.ExpressionElement;
import com.brent.expressions.parser.Parser;

import java.util.ArrayList;
import java.util.List;

import static com.brent.expressions.parser.OperandFactory.createOperand;

public class ExpressionTestBuilder {

    List<ExpressionElement> elements;

    public static ExpressionTestBuilder anExpression() {
        return new ExpressionTestBuilder();
    }

    public ExpressionTestBuilder() {
        elements = new ArrayList<>();
    }

    public Expression build() {
        return new Expression(elements);
    }

    public ExpressionTestBuilder from(String tokenizedExpression) {
        var parser = new Parser();
        var tokens = parser.tokenize(tokenizedExpression);
        var lhsToken = tokens.getFirst();

        var operatorToken = tokens.get(1);
        var rhsToken = tokens.get(2);
        var lhsOperand = createOperand(lhsToken);
        var operator = Parser.getOperator(operatorToken);
        var rhsOperand = createOperand(rhsToken);
        elements.add(lhsOperand);
        elements.add(operator);
        elements.add(rhsOperand);
        return this;
    }


    public ExpressionTestBuilder withLHS(String lhs) {
        var lhsOperand = createOperand(lhs);
        elements.add(lhsOperand);
        return this;
    }

    public ExpressionTestBuilder withOperator(String operatorString) {
        var operator = Parser.getOperator(operatorString);
        elements.add(operator);
        return this;
    }

    public ExpressionTestBuilder withRHS(String number) {
        var rhsOperand = createOperand(number);
        elements.add(rhsOperand);
        return this;
    }


    public ExpressionTestBuilder withRHS(Expression x) {
        elements.add(x);
        return this;
    }
}
