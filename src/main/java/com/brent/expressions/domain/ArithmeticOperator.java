package com.brent.expressions.domain;


import static com.brent.expressions.domain.ArithmeticOperatorType.fromRepresentation;

public class ArithmeticOperator extends Operator<ArithmeticOperatorType> {
    public ArithmeticOperator(String operatorRepresentation) {
        super(fromRepresentation(operatorRepresentation));
    }

}

