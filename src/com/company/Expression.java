package com.company;
import java.util.Objects;
import java.util.function.Function;

public interface Expression {
    Value<?> evaluate();
}

class Value<T> implements Expression {
    private final T value;

    public T getValue() {
        return value;
    }

    Value(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public Value<?> evaluate() {
        return this;
    }
}

class NumericValue extends Value<Integer> {
    NumericValue(Integer value) {
        super(value);
    }
}

class TextValue extends Value<String> {
    TextValue(String value) {
        super(value);
    }
}

class LogicalValue extends Value<Boolean> {
    LogicalValue(boolean value) {
        super(value);
    }
}

class Variable implements Expression {
    private final String name;

    Variable(String name, Function<String, Value<?>> variableValue) {
        this.name = name;
        this.variableValue = variableValue;
    }

    public String getName() {
        return name;
    }

    private final Function<String, Value<?>> variableValue;

    @Override
    public Value<?> evaluate() {
        Value<?> value = variableValue.apply(name);
        return value;
    }
}

interface OperatorExpression extends Expression {
}

abstract class UnaryOperator implements OperatorExpression {
    private final Expression value;

    UnaryOperator(Expression value) {
        this.value = value;
    }

    public Value<?> calc(Value<?> value) {
        return calc(getValue().evaluate());
    }

    public Expression getValue() {
        return value;
    }
}

abstract class BinaryOperator implements OperatorExpression {
    private final Expression left;
    private final Expression right;

    BinaryOperator(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Value<?> calc(Value<?> left, Value<?> right) {
        return calc(getLeft().evaluate(), getRight().evaluate());
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }
}

class EqualsOperator extends BinaryOperator {
    EqualsOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> calc(Value<?> left, Value<?> right) {
        if (!Objects.equals(left.getClass(), right.getClass())) {
            return new LogicalValue(false);
        }
        boolean result = left.getValue() == right.getValue();
        return new LogicalValue(result);
    }

    @Override
    public Value<?> evaluate() {
        return calc(getLeft().evaluate(), getRight().evaluate());
    }
}

class AdditionOperator extends BinaryOperator {
    AdditionOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> calc(Value<?> left, Value<?> right) {
        if (left instanceof NumericValue && right instanceof NumericValue) {
            return new NumericValue(((NumericValue) left).getValue() + ((NumericValue) right).getValue());
        } else {
            //basically concatenation
            return new TextValue(left.toString() + right.toString());
        }
    }

    @Override
    public Value<?> evaluate() {
        return calc(getLeft().evaluate(), getRight().evaluate());
    }
}

class SubtractionOperator extends BinaryOperator {
    SubtractionOperator(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value<?> calc(Value<?> left, Value<?> right) {
        if (left instanceof NumericValue && right instanceof NumericValue) {
            return new NumericValue(((NumericValue) left).getValue() - ((NumericValue) right).getValue());
        }
        throw new UnsupportedOperationException(String.format("Unsupported operation between %s, %s", left.toString(), right.toString()));
    }

    @Override
    public Value<?> evaluate() {
        return calc(getLeft().evaluate(), getRight().evaluate());
    }
}
