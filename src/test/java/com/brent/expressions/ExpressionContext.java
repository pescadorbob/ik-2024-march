package com.brent.expressions;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class ExpressionContext {
    Map<String,Operand<?>> values;
    public ExpressionContext(){
        values = new HashMap<>();
    }
    public void assign(String lhs, String rhs) {
        values.put(lhs, new NumericOperand<>(parseInt(rhs)));
    }

    public Operand<?> get(String name) {
        return values.get(name);
    }
}
