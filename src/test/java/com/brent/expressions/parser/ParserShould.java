package com.brent.expressions.parser;

import com.brent.expressions.domain.Expression;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.brent.expressions.ExpressionTestBuilder.anExpression;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ParserShould {

    @ParameterizedTest(name = " {0} with given input {1}")
    @MethodSource("testCases")
    void evaluate_to(Expression expectedExpression, String expressionString) {
        var parser = new Parser();

        var actualExpression = parser.parseExpression(expressionString);

        Assertions.assertThat(actualExpression).usingRecursiveComparison().isEqualTo(expectedExpression);
    }

    private static Stream<Arguments> testCases() {
        return Stream.of(
                arguments(anExpression()
                        .withLHS("5").withOperator(">")
                        .withRHS(anExpression()
                                .withLHS("x")
                                .withOperator("*")
                                .withRHS(anExpression().withLHS("x")
                                .withOperator("+")
                                .withRHS("5").build()).build()).build(),
                        "5 > (x * (x + 5))"),
                arguments(anExpression().withLHS("5").withOperator(">").withRHS(anExpression().withLHS("x").withOperator("+").withRHS("5").build()).build(), "5 > (x + 5)"),
                arguments(anExpression().withLHS("5").withOperator(">").withRHS("x").build(), "5 > x"),
                arguments(anExpression().withLHS("x").withOperator(">").withRHS("5").build(), "x > 5"),
                arguments(anExpression().withLHS("x").withOperator(">=").withRHS("5").build(), "x >= 5"),
                arguments(anExpression().withLHS("x").withOperator("<").withRHS("5").build(), "x < 5"),
                arguments(anExpression().withLHS("x").withOperator("<=").withRHS("5").build(), "x <= 5"),
                arguments(anExpression().withLHS("x").withOperator("+").withRHS("5").build(), "x + 5"),
                arguments(anExpression().withLHS("x").withOperator("-").withRHS("5").build(), "x - 5"),
                arguments(anExpression().withLHS("x").withOperator("*").withRHS("5").build(), "x * 5"),
                arguments(anExpression().withLHS("x").withOperator("/").withRHS("5").build(), "x / 5")
        );
    }

}