package com.brent.expressions.domain;

public abstract class Operand<T extends Comparable<T>> implements ExpressionElement, Comparable<Operand<T>> {
    private final T value;

    public Operand(T value){
        this.value = value;
    }
    public T getValue(){
        return value;
    }
    // Implement the compareTo method
    @Override
    public int compareTo(Operand<T> other) {
        return value.compareTo(other.value);
    }

    @Override
    public String toString() {
        return "" + value ;
    }
}
