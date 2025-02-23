package com.brent.expressions.domain;

import static com.brent.expressions.domain.RelationshipOperatorType.fromRepresentation;

public class RelationshipOperator extends Operator {
    RelationshipOperatorType type;
    public RelationshipOperator(String operatorRepresentation) {
        type = fromRepresentation(operatorRepresentation);
    }

    public RelationshipOperatorType getType(){
        return type;
    }
}

