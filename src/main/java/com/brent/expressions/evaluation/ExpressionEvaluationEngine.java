package com.brent.expressions.evaluation;

import com.brent.expressions.domain.*;

import javax.swing.text.Element;

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
        expressionContext.assign(lhs,rhs);
        return expressionContext;
    }

    public ExpressionResult evaluate(Expression expression) {
        var populatedExpression = populateExpression(expression,expressionContext);
        return evaluatePopulatedExpression(populatedExpression);
    }

    private ExpressionResult evaluatePopulatedExpression(Expression populatedExpression) {
        var lhs = populatedExpression.getLHS();
        var operator = populatedExpression.getOperator();
        var rhs = populatedExpression.getRHS();

        var operatorEvaluatorFactory = OperatorEvaluatorFactoryProducer.getFactory(operator);
        var operatorEvaluator = operatorEvaluatorFactory.create(operator);

        var lhOperand = evaluate(lhs);
        var rhOperand = evaluate(rhs);
        return operatorEvaluator.evaluate(lhOperand, rhOperand);
    }

    private Operand<?> evaluate(ExpressionElement element) {
        if (element instanceof Operand<?> operand) {
            return operand;
        } else if (element instanceof Expression expression){
            ExpressionResult result = evaluatePopulatedExpression(expression);
            if(result.getResultType() == ResultType.BOOLEAN){
                throw new IllegalStateException("The expression evaluation doesn't support boolean operands");
            } else {
                return new NumericOperand(result.getNumberResult());
            }
        }
        throw new IllegalArgumentException(String.format("The expression evaluation %s isn't supported", element));
    }

    private Expression populateExpression(Expression expression, ExpressionContext expressionContext) {
        var populatedExpression = new Expression();
        for(var ele:expression.elements()){
            populatedExpression.add(assignVariable(ele,expressionContext));
        }
        return populatedExpression;
    }

    private ExpressionElement assignVariable(ExpressionElement ele,ExpressionContext context) {
        if(ele instanceof VariableOperand var){
            return var.assignVariable(context);
        } else if(ele instanceof Expression expression){
            return populateExpression(expression,context);
        }
        return ele;
    }
}