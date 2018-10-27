package io.xlat.lisp;

import java.util.List;
import java.util.ArrayList;

class Reader {
    public static Object read(Lexer L) {
        if (L == null) {
            throw new IllegalArgumentException("Lexer must be non-null");
        }
        Token tok = L.peek();
        switch (tok.type()) {
        case LPAREN:
            L.match(Token.Type.LPAREN); // Eat the open-paren.
            if (L.peek().type() == Token.Type.RPAREN) {
                L.match(Token.Type.RPAREN);
                return null;
            }
            Cons head = new Cons();
            Cons cur = head;
            cur.car = read(L);
            cur.cdr = null;
            Cons prev = cur;
            while (L.peek().type() != Token.Type.RPAREN) {
                prev.cdr = new Cons();
                cur = (Cons) prev.cdr;
                cur.car = read(L);
                System.out.printf("Read %s%n", cur.car);
                cur.cdr = null;
                prev = cur;
            }
            L.match(Token.Type.RPAREN);
            return head;
        case EOF:
            return null;
        case ERROR:
            return new SyntaxError(tok.lexeme());
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
}
