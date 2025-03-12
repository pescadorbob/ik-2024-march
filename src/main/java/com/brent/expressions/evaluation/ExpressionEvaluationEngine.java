package com.brent.expressions.evaluation;

import com.brent.expressions.domain.*;

public class ExpressionEvaluationEngine {
    private final ExpressionContext expressionContext;

    public ExpressionEvaluationEngine(String context) {
        expressionContext = parseContext(context);
    }

    private static ExpressionContext parseContext(String context) {
        var tokens = context.split("=");
        var lhs = tokens[0].trim();
        var rhs = tokens[1].trim();
        var expressionContext = new ExpressionContext();
        expressionContext.assign(lhs, rhs);
        return expressionContext;
    }

    public ExpressionResult evaluate(BinomialExpression binomialExpression) {
        var populatedExpression = populateExpression(binomialExpression, expressionContext);
        return evaluatePopulatedExpression(populatedExpression);
    }

    private ExpressionResult evaluatePopulatedExpression(BinomialExpression populatedBinomialExpression) {
        var lhs = populatedBinomialExpression.getLHS();
        var operator = populatedBinomialExpression.getOperator();
        var rhs = populatedBinomialExpression.getRHS();

        var operatorEvaluatorFactory = OperatorEvaluatorFactoryProducer.getFactory(operator);
        var operatorEvaluator = operatorEvaluatorFactory.create(operator);

        var lhOperand = evaluate(lhs);
        var rhOperand = evaluate(rhs);
        return operatorEvaluator.evaluate(lhOperand, rhOperand);
    }

    private Operand<?> evaluate(Expression element) {
        if (element instanceof Operand<?> operand) {
            return operand;
        } else if (element instanceof BinomialExpression binomialExpression) {
            ExpressionResult result = evaluatePopulatedExpression(binomialExpression);
            if (result.getResultType() == ResultType.BOOLEAN) {
                throw new IllegalStateException("The expression evaluation doesn't support boolean operands");
            } else {
                return new NumericOperand(result.getNumberResult());
            }
        }
        throw new IllegalArgumentException(String.format("The expression evaluation %s isn't supported", element));
    }

    private BinomialExpression populateExpression(BinomialExpression binomialExpression, ExpressionContext expressionContext) {
        var populatedExpression = new BinomialExpression();

        populatedExpression.setLHS(assignVariable(binomialExpression.getLHS(), expressionContext));
        populatedExpression.setOperator(binomialExpression.getOperator());
        populatedExpression.setRHS(assignVariable(binomialExpression.getRHS(), expressionContext));

        return populatedExpression;
    }

    private Expression assignVariable(Expression ele, ExpressionContext context) {
        if (ele instanceof VariableOperand var) {
            return var.assignVariable(context);
        } else if (ele instanceof BinomialExpression binomialExpression) {
            return populateExpression(binomialExpression, context);
        }
        return ele;
    }
}