package com.brent.expressions.domain;

public class NumericOperand<T extends Number & Comparable<T>> extends Operand<T> {


    public NumericOperand(T value){
        super(value);
    }

}
