package com.brent.expressions;

import com.brent.expressions.domain.BinomialExpression;
import com.brent.expressions.domain.Expression;
import com.brent.expressions.domain.Operator;
import com.brent.expressions.parser.Parser;

import static com.brent.expressions.parser.OperandFactory.createOperand;

public class ExpressionTestBuilder {

    Expression lhs;
    Operator<?> operator;
    Expression rhs;

    public static ExpressionTestBuilder anExpression() {
        return new ExpressionTestBuilder();
    }

    public ExpressionTestBuilder() {
    }

    public BinomialExpression build() {
        if(lhs == null || operator == null || rhs == null) throw new IllegalArgumentException("no arguments can be null");
        return new BinomialExpression(lhs,operator,rhs);
    }

    public ExpressionTestBuilder withLHS(String lhs) {
        this.lhs = createOperand(lhs);

        return this;
    }

    public ExpressionTestBuilder withOperator(String operatorString) {
        operator = Parser.getOperator(operatorString);

        return this;
    }

    public ExpressionTestBuilder withRHS(String rhs) {
        this.rhs = createOperand(rhs);

        return this;
    }


    public ExpressionTestBuilder withRHS(BinomialExpression x) {
        this.rhs = x;
        return this;
    }
}
