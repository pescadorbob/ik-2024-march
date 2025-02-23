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

public class RelationshipExpressionShould {

    @ParameterizedTest(name = " {0} when x={1} applied to {2}")
    @MethodSource("notEqualToTestCases")
    void evaluate_to(boolean expectedResult, String xValue,String expressionString) {
        var expression = anExpression().from(expressionString).build();
        var expected = new ExpressionResult(expectedResult);

        var expressionEvaluationEngine = new ExpressionEvaluationEngine("x=" + xValue);

        var actual = expressionEvaluationEngine.evaluate(expression);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    private static Stream<Arguments> notEqualToTestCases() {
        return Stream.of(
                arguments(false, "4","x > 5"),
                arguments(false, "5","x > 5"),
                arguments(true, "6","x > 5"),
                arguments(false, "4","x >= 5"),
                arguments(true, "5","x >= 5"),
                arguments(true, "6","x >= 5"),
                arguments(true, "4","x < 5"),
                arguments(false, "5","x < 5"),
                arguments(false, "6","x < 5"),
                arguments(true, "4","x <= 5"),
                arguments(true, "5","x <= 5"),
                arguments(false, "6","x <= 5"),
                arguments(false, "4","x == 5"),
                arguments(true, "5","x == 5"),
                arguments(false, "6","x == 5"),
                arguments(true, "4","x != 5"),
                arguments(false, "5","x != 5"),
                arguments(true, "6","x != 5")
        );
    }
}