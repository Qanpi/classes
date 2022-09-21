package com.company;

public class Token {
    enum Type {
        Keyword("(loop|while|output|end|if|else)"),
        Whitespace("\\s+"),
        Numeric("[0-9]+"),
        Logical("(TRUE|FALSE)"),
        Text("(?<=\\').*(?=\\')"),
        Identifier("[a-zA-Z_]+[a-zA-Z0-9]*"),
        GroupDivider("(\\(|\\))"),
        Operator("(\\={1,2}|\\+|\\-)");

        Type(String s) {
            regex = s;
        }

        private final String regex;
        public String getRegex() {
            return regex;
        }
    }

    Token(Token.Type type, String value) {
        this.type = type;
        this.value = value;
    }

    private Type type;
    private String value;

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
