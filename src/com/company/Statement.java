package com.company;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static ibio.Helpers.input;
import static ibio.Helpers.output;

public interface Statement {
    void execute() throws Exception;
}

class OutputStatement implements Statement {
    private final Expression expression;

    OutputStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        Value<?> value = expression.evaluate();
        output(value.toString());
    }
}

class AssignStatement implements Statement {
    private final String name;
    private final Expression expression;

    private final BiConsumer<String, Value<?>> variableSetter;

    AssignStatement(String name, Expression expression, BiConsumer<String, Value<?>> variableSetter) {
        this.name = name;
        this.expression = expression;
        this.variableSetter = variableSetter;
    }

    @Override
    public void execute() {
        variableSetter.accept(name, expression.evaluate());
    }
}

class InputStatement implements Statement {
    private final String prompt;
    private final String name;
    private final BiConsumer<String, Value<?>> variableSetter;

    InputStatement(String prompt, String name, BiConsumer<String, Value<?>> variableSetter) {
        this.prompt = prompt;
        this.name = name;
        this.variableSetter = variableSetter;
    }

    @Override
    public void execute() {
        String line = input(prompt);
        Value<?> value;
        if (line.matches("[0-9]+")) {
            value = new NumericValue(Integer.parseInt(line));
        } else if (line.matches("TRUE|FALSE")) {
            value = new LogicalValue(Boolean.valueOf(line));
        } else {
            value = new TextValue(line);
        }

        variableSetter.accept(name, value);
    }
}

class CompositeStatement implements Statement {
    private final List<Statement> statements2Execute = new ArrayList<>();

    public void addStatement(Statement statement) {
        if (statement != null) { // why check agains null? just precaution?
            statements2Execute.add(statement);
        }
    }

    @Override
    public void execute() throws Exception {
        statements2Execute.forEach(s -> {
            try {
                s.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

class ConditionStatement extends CompositeStatement {
    private final Expression expression;

    ConditionStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() throws Exception {
        Value<?> value = expression.evaluate();
        if (value instanceof LogicalValue) {
            if (((LogicalValue) value).getValue()) {
                super.execute();
            }
        } else {
            throw new Exception(String.format("Cannot logically evaluate non-logical value %s", value));
        }
    }
}
