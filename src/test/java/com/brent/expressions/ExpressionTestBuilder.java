package com.brent.expressions;

import com.brent.expressions.domain.*;

import java.util.*;

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
        var lhsToken = tokens.getFirst();

        var operatorToken = tokens.get(1);
        var rhsToken = tokens.get(2);
        var lhsOperand = new VariableOperand(lhsToken);
        var operator = getOperator(operatorToken);
        var rhsOperand = new NumericOperand<>(parseInt(rhsToken));
        elements.add(lhsOperand);
        elements.add(operator);
        elements.add(rhsOperand);
        return this;
    }

    private static Operator getOperator(String operatorToken) {
        Operator operator;
        if(ArithmeticOperatorType.isRepresentedBy(operatorToken)){
            operator = new ArithmeticOperator(operatorToken);
        } else {
            operator = new RelationshipOperator(operatorToken);
        }
        return operator;
    }

    private List<String> tokenize(String tokenizedExpression) {
        var operators = getAllOperators();
        var tokens = new ArrayList<String>();
        textToTokens(tokenizedExpression, tokens, operators);
        return tokens;

    }

    private static List<String> getAllOperators() {
        var operators = new ArrayList<>(Arrays.stream(RelationshipOperatorType.values()).map(RelationshipOperatorType::getSymbol).toList());
        operators.addAll(Arrays.stream(ArithmeticOperatorType.values()).map(ArithmeticOperatorType::getSymbol).toList());
        return operators;
    }

    private void textToTokens(String tokenizedExpression, ArrayList<String> tokens, List<String> operators) {
        if (tokenizedExpression.isEmpty()) {
            return;
        }
        int nextIndex = addNextOperand(tokenizedExpression, tokens, operators);
        int operatorLength = addNextOperatorIfExists(tokenizedExpression.substring(nextIndex), tokens, operators);
        textToTokens(tokenizedExpression.substring(nextIndex + operatorLength).trim(), tokens, operators);
    }

    private int addNextOperand(String tokenizedExpression, ArrayList<String> tokens, List<String> operators) {
        var nextOperatorIndex = nextOperatorIndex(tokenizedExpression, operators);
        String operand;
        if (nextOperatorIndex < 0) {
            operand = tokenizedExpression;
        } else {
            operand = tokenizedExpression.substring(0, nextOperatorIndex);
        }
        tokens.add(operand.trim());
        return operand.length();
    }

    private int addNextOperatorIfExists(String tokenizedExpression, ArrayList<String> tokens, List<String> operators) {
        if (tokenizedExpression.isEmpty()) return 0;

        String operator = "";
        var possibleOperators = new ArrayList<String>();
        for(var op:operators){
            if(tokenizedExpression.contains(op)){
                operator = op;
                possibleOperators.add(operator);
            }
        }
        possibleOperators.sort((o1, o2) -> o1.length()-o2.length());

        operator = possibleOperators.getLast();
        tokens.add(operator);
        return operator.length();
    }

    private int nextOperatorIndex(String expression, List<String> operators) {
        int index = -1;
        for (var operator : operators) {
            if (expression.contains(operator)) {
                index = expression.indexOf(operator);
                break;
            }
        }
        return index;
    }

}
