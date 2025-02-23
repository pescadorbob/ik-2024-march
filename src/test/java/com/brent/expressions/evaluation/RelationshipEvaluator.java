package com.brent.expressions.evaluation;

import com.brent.expressions.domain.ExpressionResult;
import com.brent.expressions.domain.Operand;

public interface RelationshipEvaluator {
    <T extends Comparable<T>> ExpressionResult evaluate(Operand<T> lhs, Operand<T> rhs);
}