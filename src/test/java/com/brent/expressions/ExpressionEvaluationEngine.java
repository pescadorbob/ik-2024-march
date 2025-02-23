package com.brent.expressions;


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
        var relationshipOperator = populatedExpression.getRelationshipOperator();
        var rhs = populatedExpression.getRHS();

        var relationshipEvaluatorFactory = new RelationshipEvaluatorFactory();
        var relationshipEvaluator = relationshipEvaluatorFactory.create(relationshipOperator);

        var lhOperand = evaluate(lhs);
        var rhOperand = evaluate(rhs);
        return relationshipEvaluator.evaluate(lhOperand,rhOperand);
    }
    private Operand evaluate(ExpressionElement element){
        if(element instanceof Operand<?>) return (Operand) element;
        else throw new IllegalStateException("The expression evaluation only supports operands right now");
    }


    private Expression populateExpression(Expression expression, ExpressionContext expressionContext) {
        var populatedExpression = new Expression();
        for(var ele:expression.elements()){
            populatedExpression.add(assignVariable(ele,expressionContext));
        }
        return populatedExpression;
    }

    private ExpressionElement assignVariable(ExpressionElement ele,ExpressionContext context) {
        if(ele instanceof VariableOperand){
            var var = (VariableOperand)ele;
            return var.assignVariable(context);
        }
        return ele;
    }
}
