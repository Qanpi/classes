package com.company;
import static ibio.Helpers.*; //static allows to use the methods without calling the class

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pseudocode {
    static String source = "if 3==3 \n output 3 \n end if";

    static void test() {
        Tokenizer tz = new Tokenizer(source);
        List<Token> tokens = tz.parse();
        tokens.forEach(token -> {
            System.out.printf("%s: %s \n", token.getType(), token.getValue());;
        });

        StatementParser sp = new StatementParser(tokens);
        Statement s = sp.parse();
        try {
            s.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


