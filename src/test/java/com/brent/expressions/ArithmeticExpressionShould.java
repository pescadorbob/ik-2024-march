package com.brent.expressions;

import com.brent.expressions.domain.ExpressionResult;
import com.brent.expressions.evaluation.ExpressionEvaluationEngine;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.brent.expressions.ExpressionTestBuilder.anExpression;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ArithmeticExpressionShould {

    @ParameterizedTest(name = " {0} when x={1} applied to {2}")
    @MethodSource("testCases")
    void evaluate_to(ExpressionResult expectedResult, String xValue,String expressionString) {
        var expression = anExpression().from(expressionString).build();


        var expressionEvaluationEngine = new ExpressionEvaluationEngine("x=" + xValue);

        var actual = expressionEvaluationEngine.evaluate(expression);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    private static Stream<Arguments> testCases() {
        return Stream.of(
                arguments(num(9), "4","5 + x"),
                arguments(num(9), "4","x + 5"),
                arguments(num(-1), "4","x - 5"),
                arguments(num(0), "4","x / 5"),
                arguments(exc("Cannot divide by zero"), "4","x / 0"),
                arguments(num(20), "4","x * 5")
        );
    }

    private static ExpressionResult exc(String error) {
        return new ExpressionResultError(error);
    }

    private static Object num(Number number) {
        return new ExpressionResult(number);
    }
}