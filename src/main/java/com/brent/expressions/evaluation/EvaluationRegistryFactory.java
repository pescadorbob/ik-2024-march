package com.brent.expressions.evaluation;

import com.brent.expressions.domain.BinomialExpression;
import com.brent.expressions.domain.NumericOperand;
import com.brent.expressions.domain.Operand;

public class EvaluationRegistryFactory {
    public static EvaluationRegistry createRegistry() {
        var registry = new EvaluationRegistry();
        registry.registerEvaluator(Operand.class,new OperandEvaluator());
        registry.registerEvaluator(NumericOperand.class,new OperandEvaluator());
        registry.registerEvaluator(BinomialExpression.class,new BinomialExpressionEvaluator(registry));

        return registry;
    }
}
