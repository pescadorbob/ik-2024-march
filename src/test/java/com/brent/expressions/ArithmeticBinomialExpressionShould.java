package com.brent.expressions;

import com.brent.expressions.domain.ExpressionResult;
import com.brent.expressions.evaluation.ExpressionEvaluationEngine;
import com.brent.expressions.parser.Parser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ArithmeticBinomialExpressionShould {

    @ParameterizedTest(name = " {0} when x={1} applied to {2}")
    @MethodSource("testCases")
    void evaluate_to(ExpressionResult<?> expectedResult, String xValue,String expressionString) {
        var parser = new Parser();
        var expression = parser.parseExpression(expressionString);


        var expressionEvaluationEngine = new ExpressionEvaluationEngine("x=" + xValue);

        var actual = expressionEvaluationEngine.evaluate(expression);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    private static Stream<Arguments> testCases() {
        return Stream.of(
                arguments(num(14), "4","5 + (5 + x)"),
                arguments(num(19), "4","(5 + 5) + (5 + x)"),
                arguments(num(90), "4","(5 + 5) * (5 + x)"),
                arguments(num(9), "4","5 + x"),
                arguments(num(9), "4","x + 5"),
                arguments(num(-1), "4","x - 5"),
                arguments(num(0), "4","x / 5"),
                arguments(error(), "4","x / 0"),
                arguments(num(20), "4","x * 5")
        );
    }

    private static ExpressionResult<?> error() {
        return new ExpressionResultError("Cannot divide by zero");
    }

    private static <T extends Number & Comparable<T>> ExpressionResult<T> num(T number) {
        return new ExpressionResult<>(number);
    }
}