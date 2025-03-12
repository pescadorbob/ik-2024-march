package com.brent.expressions.evaluation;

import com.brent.expressions.domain.Expression;

import java.util.HashMap;
import java.util.Map;

public class EvaluationRegistry<T extends Comparable<T>> {
    private final Map<Class<? extends Expression>,Evaluator<T>> evaluators;
    public EvaluationRegistry() {
        evaluators = new HashMap<>();
    }


    public Evaluator<T> getEvaluator(Expression element) {
        var evaluator = evaluators.get(element.getClass());
        if (evaluator == null) {
            throw new IllegalArgumentException(String.format("The expression evaluation %s isn't supported", element.getClass()));
        }
        return evaluator;
    }

    public void registerEvaluator(Class<? extends Expression> expressionClass, Evaluator<T> evaluator) {
        evaluators.put(expressionClass, evaluator);
    }
}
