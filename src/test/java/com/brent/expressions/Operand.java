package com.brent.expressions;

public abstract class Operand<T> implements ExpressionElement, Comparable<Operand<T>> {
    private T value;

    public Operand(T value){
        this.value = value;
    }
    public T getValue(){
        return value;
    }
    // Implement the compareTo method
    @Override
    public int compareTo(Operand<T> other) {
        if (this.value instanceof Comparable) {
            return ((Comparable<T>) this.value).compareTo(other.value);
        }
        throw new UnsupportedOperationException("Comparison not supported for this type");
    }
}
