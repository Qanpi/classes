package com.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatementParser {
    private final List<Token> tokens;
    private int position;

    private final Map<String, Value<?>> variables;

    StatementParser(List<Token> tokens) {
        this.tokens = tokens;
        this.variables = new HashMap<>();
    }

    Statement parse() {
        Token token = next();
        Expression value;
        switch(token.getType()) {
            case Identifier:
                next(); //should be an assignment operator
                value = readRecurse();
                //System.out.println(value.evaluate().toString());
                return new AssignStatement(token.getValue(), value, variables::put);
            case Keyword:
                switch(token.getValue()) {
                    case "output":
                        value = readRecurse();
                        return new OutputStatement(value);
                    case "end":

                    case "if":
                        Expression condition = readRecurse();
                        ConditionStatement conditionStatement = new ConditionStatement(condition);
                        while(!peek(Token.Type.Keyword, "")) {
                            Statement statement = parse();
                            conditionStatement.addStatement(statement);
                        }
                        next(); //end if
                        return conditionStatement;
                }
        }
        return null;
    }

    Expression readRecurse() {
        Expression left = nextExpression();
        while(peek(Token.Type.Operator)) {
            Token operation = next();
            Expression right = nextExpression();
            switch(operation.getValue()) {
                case "+": left = new AdditionOperator(left, right); break;
                case "-": left = new SubtractionOperator(left, right); break;
                case "==": left = new EqualsOperator(left, right); break;
            }
        }
        return left;
    }

    Expression nextExpression() {
        Token token = next();
        String value = token.getValue();
        switch(token.getType()) {
            case Numeric:
                return new NumericValue(Integer.parseInt(value));
            case Logical:
                return new LogicalValue(Boolean.parseBoolean(value));
            case Text:
                return new TextValue(value);
            case Identifier:
//                if(value.equals("input")) {
//                    next(); //should be the (
//                    while(!peek(Token.Type.GroupDivider)) {
//                        Token arg = next();
//                    }
//                    return new InputStatement();
//                    next();
//                }
            default:
                return new Variable(value, variables::get);
        }
    }

    private Token next() {
        if (position < tokens.size()) {
            Token token = tokens.get(position);
            position++;
            return token;
        }
        return null; //take care of this case
    }

    private boolean peek(Token.Type type) {
        if(position < tokens.size()) {
            Token token = tokens.get(position);
            return token.getType() == type;
        }
        return false;
    }

    private boolean peek(Token.Type type, String keyword) {
        if(position < tokens.size()) {
            Token token = tokens.get(position);
            return token.getType() == type && token.getValue().equals(keyword);
        }
        return false;
    }
}
