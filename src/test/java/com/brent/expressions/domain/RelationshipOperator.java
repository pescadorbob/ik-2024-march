package com.brent.expressions.domain;

import static com.brent.expressions.domain.RelationshipOperatorType.fromRepresentation;

public class RelationshipOperator extends Operator<RelationshipOperatorType> {

    public RelationshipOperator(String operatorRepresentation) {
        super(fromRepresentation(operatorRepresentation));
    }
}

