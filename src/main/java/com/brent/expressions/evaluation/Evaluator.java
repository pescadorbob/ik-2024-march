package com.brent.expressions.evaluation;

import com.brent.expressions.domain.Expression;
import com.brent.expressions.domain.ExpressionResult;

public interface Evaluator<T extends Comparable<T>> {
    ExpressionResult<T> evaluate(Expression element);
}
