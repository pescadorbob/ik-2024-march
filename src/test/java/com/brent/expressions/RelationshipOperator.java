package com.brent.expressions;

import static com.brent.expressions.RelationshipType.fromSymbol;

public class RelationshipOperator implements ExpressionElement {
    RelationshipType type;
    public RelationshipOperator(String relation) {
        type = fromSymbol(relation);
    }

}

