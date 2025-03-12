package com.brent.expressions.parser;

import com.brent.expressions.domain.NumericOperand;
import com.brent.expressions.domain.Operand;
import com.brent.expressions.domain.VariableOperand;

import static java.lang.Character.isDigit;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class OperandFactory {
    public static Operand<?> createOperand(String representation){
        char firstLetter = representation.charAt(0);
        Operand<?> operand;
        if(isDigit(firstLetter)){
            if(representation.contains(".")){
                operand = new NumericOperand<>(parseDouble(representation));
            } else {
                operand = new NumericOperand<>(parseInt(representation));
            }
        } else {
            operand = new VariableOperand(representation);
        }
        return operand;
    }
}
