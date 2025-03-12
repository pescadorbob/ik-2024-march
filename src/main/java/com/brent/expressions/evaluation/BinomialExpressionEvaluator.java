package com.brent.expressions.evaluation;

import com.brent.expressions.domain.*;

public class BinomialExpressionEvaluator implements Evaluator {
    private final EvaluationRegistry registry;

    public BinomialExpressionEvaluator(EvaluationRegistry registry) {
        this.registry = registry;
    }

    @Override
    public ExpressionResult evaluate(Expression element) {
        if (element instanceof BinomialExpression binomialExpression) {
            return  evaluatePopulatedExpression(binomialExpression);
        }
        throw new IllegalArgumentException(String.format("The expression evaluation %s isn't supported", element));

    }
    private ExpressionResult evaluatePopulatedExpression(BinomialExpression populatedBinomialExpression) {
        var lhs = populatedBinomialExpression.getLHS();
        var operator = populatedBinomialExpression.getOperator();
        var rhs = populatedBinomialExpression.getRHS();

        var operatorEvaluatorFactory = OperatorEvaluatorFactoryProducer.getFactory(operator);
        var operatorEvaluator = operatorEvaluatorFactory.create(operator);

        var lhEvaluator = registry.getEvaluator(lhs);
        var rhEvaluator = registry.getEvaluator(rhs);
        var lhResult = lhEvaluator.evaluate(lhs);
        var rhResult = rhEvaluator.evaluate(rhs);

        return operatorEvaluator.evaluate(lhResult,rhResult);
    }

}
