package com.brent.expressions;

import com.brent.expressions.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

public class ExpressionTestBuilder {

    List<ExpressionElement> elements;

    public static ExpressionTestBuilder anExpression() {
        return new ExpressionTestBuilder();
    }

    public ExpressionTestBuilder() {
        elements = new ArrayList<>();
    }

    Expression build() {
        return new Expression(elements);
    }

    public ExpressionTestBuilder from(String tokenizedExpression) {

        var tokens = tokenize(tokenizedExpression);
        var lhs = tokens.getFirst();

        var relation = tokens.get(1);
        var rhs = tokens.get(2);
        var variable = new VariableOperand(lhs);
        var relationshipOperator = new RelationshipOperator(relation);
        var literal = new NumericOperand<>(parseInt(rhs));
        elements.add(variable);
        elements.add(relationshipOperator);
        elements.add(literal);
        return this;
    }

    private List<String> tokenize(String tokenizedExpression) {
        var operators = Arrays.stream(RelationshipType.values()).map(RelationshipType::getSymbol).toList();
        var tokens = new ArrayList<String>();
        textToTokens(tokenizedExpression, tokens, operators);
        return tokens;

    }

    private void textToTokens(String tokenizedExpression, ArrayList<String> tokens, List<String> operators) {
        if (tokenizedExpression.isEmpty()) {
            return;
        }
        int nextIndex = addNextOperand(tokenizedExpression, tokens, operators);
        int operatorLength = addNextOperatorIfExists(tokenizedExpression.substring(nextIndex), tokens);
        textToTokens(tokenizedExpression.substring(nextIndex + operatorLength ),tokens,operators);
    }

    private int addNextOperand(String tokenizedExpression, ArrayList<String> tokens, List<String> operators) {
        var nextOperatorIndex = nextOperatorIndex(tokenizedExpression, operators);
            String operand;
        if(nextOperatorIndex<0){
            operand = tokenizedExpression;
        } else {
            operand = tokenizedExpression.substring(0, nextOperatorIndex);
        }
        tokens.add(operand);
        return operand.length();
    }

    private int addNextOperatorIfExists(String tokenizedExpression, ArrayList<String> tokens) {
        if(tokenizedExpression.length()<=0) return 0;

        var operator = tokenizedExpression.substring(0,1);

        tokens.add(operator);
        return 1;
    }

    private int nextOperatorIndex(String expression, List<String> operators) {
        int index = -1;
        for(var operator:operators){
            if(expression.contains(operator)){
                index = expression.indexOf(operator);
                break;
            }
        }
        return index;
    }

}
