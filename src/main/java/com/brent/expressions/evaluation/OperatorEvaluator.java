package com.brent.expressions.evaluation;

import com.brent.expressions.domain.ExpressionResult;
import com.brent.expressions.domain.Operand;

public interface OperatorEvaluator<T extends Comparable<T>> {
    ExpressionResult<T> evaluate(Operand<T> lhs, Operand<T> rhs);
}
