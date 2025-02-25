package com.brent.expressions.domain;

public class VariableOperand extends Operand<String> {

    public VariableOperand(String value) {
        super(value);
    }

    public ExpressionElement assignVariable(ExpressionContext context) {
        return context.get(getValue());
    }

}
