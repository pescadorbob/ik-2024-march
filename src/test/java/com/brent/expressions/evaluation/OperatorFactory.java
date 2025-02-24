package com.brent.expressions.evaluation;

import com.brent.expressions.domain.ArithmeticOperator;
import com.brent.expressions.domain.Operator;
import com.brent.expressions.domain.RelationshipOperator;

public class OperatorFactory {
    public static Operator<?> createOperator(String representation){
        // First try relationship operators
        try {
            return new RelationshipOperator(representation);
        } catch (IllegalArgumentException e){
            // Not a relationship operator, try arithmetic
            try {
                return new ArithmeticOperator(representation);
            } catch (IllegalArgumentException e2){
                throw new IllegalArgumentException("Unknown operator: " + representation,e2);
            }
        }
    }


}
