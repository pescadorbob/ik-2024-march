package com.brent.expressions.evaluation;

import com.brent.expressions.domain.Operator;

public interface OperatorEvaluatorFactory {
    OperatorEvaluator create(Operator<?> operator);
}
