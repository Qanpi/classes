package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    private final List<Token> tokens;
    private final String source;

    Tokenizer(String source) {
        this.source = source;
        this.tokens = new ArrayList<Token>();
    }

    int nextToken(int pos) throws Exception {
        String code = source.substring(pos);
        for (Token.Type type : Token.Type.values()) {
            Pattern pattern = Pattern.compile("^" + type.getRegex()); //always search for the pattern at the beginning of string
            Matcher matcher = pattern.matcher(code);
            if (matcher.find()) { //potentially stack matcher without resetting everytime
                if (type != Token.Type.Whitespace) {
                    String value = matcher.group();
                    Token token = new Token(type, value);
                    tokens.add(token);
                }
                return matcher.group().length();
            }
        }
        throw new Exception("invalid expression: " + code);
    }

    List<Token> parse() {
        int pos = 0;
        while (pos < source.length()) {
            try {
                pos += nextToken(pos);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tokens;
    }

}
