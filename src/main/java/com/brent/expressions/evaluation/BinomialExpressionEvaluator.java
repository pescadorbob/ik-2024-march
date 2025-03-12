package com.brent.expressions.evaluation;

import com.brent.expressions.domain.BinomialExpression;
import com.brent.expressions.domain.Expression;
import com.brent.expressions.domain.ExpressionResult;

public class BinomialExpressionEvaluator<T extends Comparable<T>> implements Evaluator<T> {
    private final EvaluationRegistry<T> registry;

    public BinomialExpressionEvaluator(EvaluationRegistry<T> registry) {
        this.registry = registry;
    }

    @Override
    public ExpressionResult<T> evaluate(Expression element) {
        if (element instanceof BinomialExpression binomialExpression) {
            return  evaluatePopulatedExpression(binomialExpression);
        }
        throw new IllegalArgumentException(String.format("The expression evaluation %s isn't supported", element));

    }
    private ExpressionResult<T> evaluatePopulatedExpression(BinomialExpression populatedBinomialExpression) {
        var lhs = populatedBinomialExpression.getLHS();
        var operator = populatedBinomialExpression.getOperator();
        var rhs = populatedBinomialExpression.getRHS();

        var operatorEvaluatorFactory = OperatorEvaluatorFactoryProducer.getFactory(operator);
        var operatorEvaluator = operatorEvaluatorFactory.create(operator);

        Evaluator<T> lhEvaluator = registry.getEvaluator(lhs);
        Evaluator<T> rhEvaluator = registry.getEvaluator(rhs);

        ExpressionResult<T> lhResult = lhEvaluator.evaluate(lhs);
        ExpressionResult<T> rhResult =  rhEvaluator.evaluate(rhs);

        @SuppressWarnings("unchecked")
        ExpressionResult<T> result = (ExpressionResult<T>) operatorEvaluator.evaluate(lhResult,rhResult);
        return result;
    }

}
