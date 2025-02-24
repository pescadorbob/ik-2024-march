package com.brent.expressions.evaluation;

import com.brent.expressions.domain.ArithmeticOperatorType;
import com.brent.expressions.domain.Operator;

public class OperatorEvaluatorFactoryProducer {
    public static OperatorEvaluatorFactory getFactory(Operator<?> operator){
        if(operator.getType() instanceof ArithmeticOperatorType){
            return new ArithmeticOperatorEvaluatorFactory();
        }
        return new RelationshipOperatorEvaluatorFactory();

    }

}
