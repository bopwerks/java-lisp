package io.xlat.lisp;

import java.io.PushbackReader;
import java.io.InputStreamReader;
import java.io.Reader;

import java.util.List;
import java.util.ArrayList;

public class Lisp {
    public static void main(String[] args) {
        final java.io.Reader isr = new InputStreamReader(System.in);
        final PushbackReader input = new PushbackReader(isr);
        final Env env = new Env(null);
        for (;;) {
            Object expr, val;
            try {
                expr = read(input);
            } catch (SyntaxError e) {
                System.err.println(e);
                continue;
            }
            if (expr == null) {
                break;
            }
            try {
                val = eval(expr, env);
            } catch (Exception e) {
                System.err.printf("Error: %s%n", e);
                continue;
            }
            print(val);
        }
    }

    private static Object read(PushbackReader r) {
        return null;
    }

    private static Object read(Lexer L) {
        Token tok = L.peek();
        switch (tok.type()) {
        case LPAREN:
            L.match(Token.Type.LPAREN); // Eat the open-paren.
            List<Object> list = new ArrayList<Object>();
            for (;;) {
                tok = L.peek();
                if (tok.type() == Token.Type.RPAREN) {
                    break;
                }
                list.add(read(L));
            }
            L.match(Token.Type.RPAREN);
            return list;
        case EOF:
        case ERROR:
            return null;
        case INTEGER:
            L.match(tok.type()); // Eat the integer.
            return Integer.parseInt(tok.lexeme());
        case STRING:
            L.match(tok.type()); // Eat the string.
            return tok.lexeme();
        case SYMBOL:
            L.match(tok.type()); // Eat the symbol.
            return new Symbol(tok.lexeme());
        default:
            return null;
        }
    }

    private static Object eval(Object expr, Env env) {
        return null;
    }

    private static void print(Object expr) {
        System.out.println(expr);
    }
}
