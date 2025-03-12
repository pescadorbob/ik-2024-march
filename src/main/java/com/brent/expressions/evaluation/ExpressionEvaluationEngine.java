package com.brent.expressions.evaluation;

import com.brent.expressions.domain.*;

public class ExpressionEvaluationEngine {
    private final ExpressionContext expressionContext;
    private final EvaluationRegistry evaluationRegistry;
    public ExpressionEvaluationEngine(String context) {
        expressionContext = parseContext(context);
        evaluationRegistry = EvaluationRegistryFactory.createRegistry();
    }

    private static ExpressionContext parseContext(String context) {
        var tokens = context.split("=");
        var lhs = tokens[0].trim();
        var rhs = tokens[1].trim();
        var expressionContext = new ExpressionContext();
        expressionContext.assign(lhs, rhs);
        return expressionContext;
    }

    public ExpressionResult<?> evaluate(BinomialExpression binomialExpression) {
        var populatedExpression = populateExpression(binomialExpression, expressionContext);
        return evaluate(populatedExpression);
    }


    private ExpressionResult<?> evaluate(Expression element) {
        var evaluator = evaluationRegistry.getEvaluator(element);
        return evaluator.evaluate(element);

    }

    private Expression populateExpression(BinomialExpression binomialExpression, ExpressionContext expressionContext) {
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