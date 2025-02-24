package com.brent.expressions.domain;

public class Operator<T extends Enum<T>> implements ExpressionElement {
    protected final T type;

    protected Operator(T type){
        this.type = type;
    }

    public T getType(){
        return type;
    }
}
