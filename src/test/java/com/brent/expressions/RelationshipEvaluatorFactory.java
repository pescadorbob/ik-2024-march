package com.brent.expressions;

public class RelationshipEvaluatorFactory {
    RelationshipEvaluator create(RelationshipOperator operator){
        if(operator.type == RelationshipType.GREATER_THAN){
            return (lhs, rhs) ->
                    new ExpressionResult(lhs.compareTo(rhs) > 0);
        } else {
            return (lhs, rhs) ->
                    new ExpressionResult(lhs.compareTo(rhs) < 0);
        }
    }
}
