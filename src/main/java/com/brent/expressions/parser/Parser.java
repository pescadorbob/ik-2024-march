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
        textToTokens(tokenizedExpression.trim(), tokens, operators);
        return tokens;
    }

    private static List<String> getAllOperators() {
        var operators = new ArrayList<>(Arrays.stream(RelationshipOperatorType.values()).map(RelationshipOperatorType::getSymbol).toList());
        operators.addAll(Arrays.stream(ArithmeticOperatorType.values()).map(ArithmeticOperatorType::getSymbol).toList());
        return operators;
    }

    private void textToTokens(String expression, ArrayList<String> tokens, List<String> operators) {
        if (expression.isEmpty()) {
            return;
        }

        // Handle parentheses
        if (expression.startsWith("(")) {
            int closingParenIndex = findMatchingClosingParen(expression);
            if (closingParenIndex == -1) {
                throw new IllegalArgumentException("Mismatched parentheses");
            }
            // Add the parenthesized expression as a single token
            tokens.add(expression.substring(0, closingParenIndex + 1));
            // Process the rest of the expression after the closing parenthesis
            String remaining = expression.substring(closingParenIndex + 1).trim();
            if (!remaining.isEmpty()) {
                int operatorLength = addNextOperatorIfExists(remaining, tokens, operators);
                textToTokens(remaining.substring(operatorLength).trim(), tokens, operators);
            }
            return;
        }

        int nextIndex = addNextOperand(expression, tokens, operators);
        int operatorLength = addNextOperatorIfExists(expression.substring(nextIndex), tokens, operators);
        textToTokens(expression.substring(nextIndex + operatorLength).trim(), tokens, operators);
    }

    private int findMatchingClosingParen(String expression) {
        int depth = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                depth++;
            } else if (expression.charAt(i) == ')') {
                depth--;
                if (depth == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    private int addNextOperand(String tokenizedExpression, ArrayList<String> tokens, List<String> operators) {
        int nextOperatorIndex = findNextOperatorIndex(tokenizedExpression, operators);
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
            if(tokenizedExpression.substring(0,op.length()).contains(op)){
                operator = op;
                possibleOperators.add(operator);
            }
        }
        possibleOperators.sort((o1, o2) -> o1.length()-o2.length());

        operator = possibleOperators.getLast();
        tokens.add(operator);
        return operator.length();
    }

    private int findNextOperatorIndex(String expression, List<String> operators) {
        int earliest = expression.length();
        for (String operator : operators) {
            int index = expression.indexOf(operator);
            if (index >= 0 && index < earliest) {
                earliest = index;
            }
        }
        return earliest == expression.length() ? -1 : earliest;
    }

    public Expression parseExpression(String expression) {
        var tokens = tokenize(expression);
        return buildExpression(tokens);
    }

    private Expression buildExpression(List<String> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Empty expression");
        }

        var lhsToken = tokens.getFirst();
        ExpressionElement lhs;

        // Handle parenthesized expression in LHS
        if (lhsToken.startsWith("(")) {
            String innerExpr = lhsToken.substring(1, lhsToken.length() - 1);
            lhs = parseExpression(innerExpr);
        } else {
            lhs = createOperand(lhsToken);
        }

        var operatorToken = tokens.get(1);
        var rhsToken = tokens.get(2);

        var operator = getOperator(operatorToken);

        ExpressionElement rhs;
        // Handle parenthesized expression in RHS
        if (rhsToken.startsWith("(")) {
            String innerExpr = rhsToken.substring(1, rhsToken.length() - 1);
            rhs = parseExpression(innerExpr);
        } else {
            rhs = createOperand(rhsToken);
        }

        List<ExpressionElement> elements = new ArrayList<>();
        elements.add(lhs);
        elements.add(operator);
        elements.add(rhs);
        return new Expression(elements);
    }
}