package com.brent.expressions;

import org.junit.jupiter.api.Test;

import static com.brent.expressions.ExpressionTestBuilder.anExpression;
import static org.assertj.core.api.Assertions.assertThat;

public class GreaterThanExpressionShould {

    @Test
    void result_false_give_LHS_is_less_than_or_equal_to_RHS(){
        var expression = anExpression().from("x > 5").build();
        var expected = new ExpressionResult(false);

        var expressionEvaluationEngine = new ExpressionEvaluationEngine("x = 4");

        var actual = expressionEvaluationEngine.evaluate(expression);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
    @Test
    void result_true_given_LHS_is_greater_than_RHS(){
        var expression = anExpression().from("x > 5").build();
        var expected = new ExpressionResult(true);

        var expressionEvaluationEngine = new ExpressionEvaluationEngine("x = 6");

        var actual = expressionEvaluationEngine.evaluate(expression);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
