package com.brent.expressions.domain;

public class Operator<T extends Enum<T>>  {
    protected final T type;

    protected Operator(T type){
        this.type = type;
    }

    public T getType(){
        return type;
    }
    @Override
    public String toString() {
        return "" + type ;
    }
}
