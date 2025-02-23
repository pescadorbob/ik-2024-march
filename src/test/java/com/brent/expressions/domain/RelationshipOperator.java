package com.brent.expressions.domain;

import static com.brent.expressions.domain.RelationshipType.fromSymbol;

public class RelationshipOperator implements ExpressionElement {
    RelationshipType type;
    public RelationshipOperator(String relation) {
        type = fromSymbol(relation);
    }

    public RelationshipType getType(){
        return type;
    }
}

