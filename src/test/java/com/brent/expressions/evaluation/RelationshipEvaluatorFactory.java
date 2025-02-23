// RelationshipEvaluatorFactory.java
package com.brent.expressions.evaluation;

import com.brent.expressions.domain.ExpressionResult;
import com.brent.expressions.domain.RelationshipOperator;

public class RelationshipEvaluatorFactory {
    public RelationshipEvaluator create(RelationshipOperator operator) {
        return switch (operator.getType()) {
            case GREATER_THAN -> (lhs, rhs) -> {
                @SuppressWarnings("unchecked")
                Comparable<Object> leftValue = (Comparable<Object>) lhs.getValue();
                return new ExpressionResult(leftValue.compareTo(rhs.getValue()) > 0);
            };
            case LESS_THAN -> (lhs, rhs) -> {
                @SuppressWarnings("unchecked")
                Comparable<Object> leftValue = (Comparable<Object>) lhs.getValue();
                return new ExpressionResult(leftValue.compareTo(rhs.getValue()) < 0);
            };
            case LESS_THAN_OR_EQUAL -> (lhs, rhs) -> {
                @SuppressWarnings("unchecked")
                Comparable<Object> leftValue = (Comparable<Object>) lhs.getValue();
                return new ExpressionResult(leftValue.compareTo(rhs.getValue()) <= 0);
            };
        };
    }
}