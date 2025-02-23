package com.brent.expressions.evaluation;

import com.brent.expressions.domain.ExpressionResult;
import com.brent.expressions.domain.RelationshipOperator;
import com.brent.expressions.domain.RelationshipType;

public class RelationshipEvaluatorFactory {
    RelationshipEvaluator create(RelationshipOperator operator){
        if(operator.getType() == RelationshipType.GREATER_THAN){
            return (lhs, rhs) ->
                    new ExpressionResult(lhs.compareTo(rhs) > 0);
        } else {
            return (lhs, rhs) ->
                    new ExpressionResult(lhs.compareTo(rhs) < 0);
        }
    }
}
