package com.brent.expressions.parser;

import com.brent.expressions.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.brent.expressions.evaluation.OperatorFactory.createOperator;
import static com.brent.expressions.parser.OperandFactory.createOperand;

public class Parser {
    public static Operator<?> getOperator(String operatorToken) {
        return createOperator(operatorToken);
    }
    public List<String> tokenize(String tokenizedExpression) {
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

    public Expression parseExpression(String expression) {
        var parser = new Parser();
        var tokens = parser.tokenize(expression);
        var lhsToken = tokens.getFirst();

        var operatorToken = tokens.get(1);
        var rhsToken = tokens.get(2);

        var lhsOperand = createOperand(lhsToken);
        var operator = Parser.getOperator(operatorToken);
        var rhsOperand = createOperand(rhsToken);

        List<ExpressionElement> elements = new ArrayList<>();
        elements.add(lhsOperand);
        elements.add(operator);
        elements.add(rhsOperand);
        return new Expression(elements);
    }
}
