package com.brent.expressions.domain;


import static com.brent.expressions.domain.ArithmeticOperatorType.fromRepresentation;

public class ArithmeticOperator extends Operator {
    ArithmeticOperatorType type;
    public ArithmeticOperator(String operatorRepresentation) {
        type = fromRepresentation(operatorRepresentation);
    }

    public ArithmeticOperatorType getType(){
        return type;
    }
}

