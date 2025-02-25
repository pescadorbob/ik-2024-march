package com.brent.expressions.parser;

import org.junit.jupiter.api.Test;

import static com.brent.expressions.ExpressionTestBuilder.anExpression;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ParserShould {

    @Test
    void produce_an_expression_given_an_expression_string() {
        var expressionString = "x + 5";
        var expectedExpression = anExpression()
                .withLHS("x")
                .withOperator("+")
                .withRHS("5").build();
        var parser = new Parser();

        var actualExpression = parser.parseExpression(expressionString);

        assertThat(actualExpression).usingRecursiveComparison().isEqualTo(expectedExpression);
    }
}