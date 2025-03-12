package com.brent.expressions.evaluation;

import com.brent.expressions.ExpressionResultError;
import com.brent.expressions.domain.ArithmeticOperator;
import com.brent.expressions.domain.ExpressionResult;
import com.brent.expressions.domain.Operand;
import com.brent.expressions.domain.Operator;

public class ArithmeticOperatorEvaluatorFactory implements OperatorEvaluatorFactory {
    @Override
    public OperatorEvaluator create(Operator<?> operator) {
        if (!(operator instanceof ArithmeticOperator arithmeticOperator)) {
            throw new IllegalArgumentException("Operator must be an ArithmeticOperator");
        }

        return switch (arithmeticOperator.getType()) {
            case ADD -> (lhs, rhs) -> evaluateArithmetic(lhs, rhs, (a, b) -> a.doubleValue() + b.doubleValue());
            case SUBTRACT -> (lhs, rhs) -> evaluateArithmetic(lhs, rhs, (a, b) -> a.doubleValue() - b.doubleValue());
            case MULTIPLY -> (lhs, rhs) -> evaluateArithmetic(lhs, rhs, (a, b) -> a.doubleValue() * b.doubleValue());
            case DIVIDE -> (lhs, rhs) -> evaluateArithmetic(lhs, rhs, (a, b) -> {
                if (0 == b.doubleValue() || b.intValue() == 0) {
                    throw new IllegalArgumentException("Cannot divide by zero");
                }
                return a.doubleValue() / b.doubleValue();
            });
        };
    }

    @SuppressWarnings("unchecked")
    private ExpressionResult evaluateArithmetic(Operand<?> lhs, Operand<?> rhs, ArithmeticOperation operation) {
        if (!(lhs.getValue() instanceof Number leftNum) || !(rhs.getValue() instanceof Number rightNum)) {
            throw new IllegalArgumentException("Arithmetic operations require numeric operands");
        }

        double result;
        try {
            result = operation.apply(leftNum, rightNum);
        } catch (IllegalArgumentException e) {
            return new ExpressionResultError(e.getMessage());
        }

        if (leftNum instanceof Integer && rightNum instanceof Integer) {
            return new ExpressionResult((int) result);
        }

        return new ExpressionResult(result);
    }

    @FunctionalInterface
    private interface ArithmeticOperation {
        double apply(Number lhs, Number rhs);
    }
}