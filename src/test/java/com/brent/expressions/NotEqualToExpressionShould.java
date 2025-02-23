package com.brent.expressions;

import com.brent.expressions.domain.ExpressionResult;
import com.brent.expressions.evaluation.ExpressionEvaluationEngine;
import org.junit.jupiter.api.Test;

import static com.brent.expressions.ExpressionTestBuilder.anExpression;
import static org.assertj.core.api.Assertions.assertThat;

public class NotEqualToExpressionShould {

    @Test
    void evaluate_to_true_given_LHS_is_less_than_to_RHS(){
        var expression = anExpression().from("x!=5").build();
        var expected = new ExpressionResult(true);

        var expressionEvaluationEngine = new ExpressionEvaluationEngine("x=4");

        var actual = expressionEvaluationEngine.evaluate(expression);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
    @Test
    void evaluate_to_false_given_LHS_is_equal_to_RHS(){
        var expression = anExpression().from("x!=5").build();
        var expected = new ExpressionResult(false);

        var expressionEvaluationEngine = new ExpressionEvaluationEngine("x=5");

        var actual = expressionEvaluationEngine.evaluate(expression);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
    @Test
    void evaluate_to_true_given_LHS_is_greater_than_RHS(){
        var expression = anExpression().from("x!=5").build();
        var expected = new ExpressionResult(true);

        var expressionEvaluationEngine = new ExpressionEvaluationEngine("x=6");

        var actual = expressionEvaluationEngine.evaluate(expression);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
