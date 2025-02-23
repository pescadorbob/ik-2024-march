package com.brent.expressions.evaluation;

import com.brent.expressions.domain.ExpressionResult;
import com.brent.expressions.domain.Operand;

public interface RelationshipEvaluator {
    ExpressionResult evaluate(Operand lhs, Operand rhs);
}
