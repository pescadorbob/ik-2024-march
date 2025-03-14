package com.brent.expressions.evaluation;

import com.brent.expressions.domain.ExpressionResult;
import com.brent.expressions.domain.Operand;
import com.brent.expressions.domain.Operator;
import com.brent.expressions.domain.RelationshipOperator;

public class RelationshipOperatorEvaluatorFactory implements OperatorEvaluatorFactory {
    public OperatorEvaluator create(Operator<?> operator) {
        if (!(operator instanceof RelationshipOperator relationshipOperator)) {
            throw new IllegalArgumentException("Operator must be a relationship Operator");
        }

        return switch (relationshipOperator.getType()) {
            case NOT_EQUAL_TO -> (lhs, rhs) -> compareOperands(lhs, rhs, comp -> comp != 0);
            case EQUAL_TO -> (lhs, rhs) -> compareOperands(lhs, rhs, comp -> comp == 0);
            case GREATER_THAN_OR_EQUAL -> (lhs, rhs) -> compareOperands(lhs, rhs, comp -> comp >= 0);
            case GREATER_THAN -> (lhs, rhs) -> compareOperands(lhs, rhs, comp -> comp > 0);
            case LESS_THAN -> (lhs, rhs) -> compareOperands(lhs, rhs, comp -> comp < 0);
            case LESS_THAN_OR_EQUAL -> (lhs, rhs) -> compareOperands(lhs, rhs, comp -> comp <= 0);
        };
    }

    @SuppressWarnings("unchecked")
    private ExpressionResult compareOperands(Operand<?> lhs, Operand<?> rhs, ComparisonPredicate predicate) {
        Comparable<Object> leftValue = (Comparable<Object>) lhs.getValue();
        int comparisonResult = leftValue.compareTo(rhs.getValue());
        return new ExpressionResult(predicate.test(comparisonResult));
    }

    @FunctionalInterface
    private interface ComparisonPredicate {
        boolean test(int comparisonResult);
    }
}