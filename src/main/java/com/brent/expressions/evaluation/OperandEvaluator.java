package com.brent.expressions.evaluation;

import com.brent.expressions.domain.Expression;
import com.brent.expressions.domain.ExpressionResult;
import com.brent.expressions.domain.Operand;

public class OperandEvaluator implements Evaluator {
    @Override
    public <T extends Comparable<T>> ExpressionResult<T> evaluate(Expression element) {
        if(element instanceof Operand<?> operand){
            @SuppressWarnings("unchecked")
            Operand<T> typedOperand = (Operand<T>) operand;
            return new ExpressionResult<>(typedOperand.getValue());
        }
        throw new IllegalArgumentException(String.format("The expression evaluation %s isn't supported", element));
    }
}
