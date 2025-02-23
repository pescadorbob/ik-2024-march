package com.brent.expressions.evaluation;

import com.brent.expressions.domain.ExpressionResult;
import com.brent.expressions.domain.RelationshipOperator;


public class RelationshipEvaluatorFactory {
    public RelationshipEvaluator create(RelationshipOperator operator) {
        return switch (operator.getType()) {
            case GREATER_THAN -> new RelationshipEvaluator() {
                @Override
                public <T extends Comparable<T>> ExpressionResult evaluate(
                        com.brent.expressions.domain.Operand<T> lhs,
                        com.brent.expressions.domain.Operand<T> rhs) {
                    return new ExpressionResult(lhs.compareTo(rhs) > 0);
                }
            };
            case LESS_THAN -> new RelationshipEvaluator() {
                @Override
                public <T extends Comparable<T>> ExpressionResult evaluate(
                        com.brent.expressions.domain.Operand<T> lhs,
                        com.brent.expressions.domain.Operand<T> rhs) {
                    return new ExpressionResult(lhs.compareTo(rhs) < 0);
                }
            };
        };
    }
}