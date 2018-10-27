package io.xlat.lisp;

import java.io.PushbackReader;
import java.io.IOException;

class Lexer {
    private static final int EOF = -1;
    private final PushbackReader in;
    private String name;
    private int line;
    private int ch;
    private Token tok;
    
    public Lexer(String name, java.io.Reader input) {
        if (input == null) {
            throw new IllegalArgumentException("Input must be non-null");
        }
        in = new PushbackReader(input);
        this.name = name;
        line = 1;
        try {
            ch = in.read(); // Prime the pump.
        } catch (IOException e) {
            ch = EOF;
        }
        tok = read();
    }

    public Token peek() {
        return tok;
    }

    public void match(Token.Type type) throws SyntaxError {
        if (!tok.type().equals(type)) {
            throw new SyntaxError("%s:%d: Unexpected token %s", name, line, tok);
        }
        tok = read();
    }

    private Token read() {
        try {
            while (ch != EOF && Character.isWhitespace(ch)) {
                if (ch == '\n') {
                    ++line;
                }
                ch = in.read();
            }
            if (ch == EOF) {
                return new Token(Token.Type.EOF, "<EOF>");
            }
            if (ch == '(') {
                ch = in.read(); // Eat the character.
                return new Token(Token.Type.LPAREN, '(');
            }
            if (ch == ')') {
                ch = in.read(); // Eat the character.
                return new Token(Token.Type.RPAREN, ')');
            }
            if (Character.isDigit(ch)) {
                StringBuilder sb = new StringBuilder();
                sb.appendCodePoint(ch);
                while ((ch = in.read()) != EOF && Character.isDigit(ch)) {
                    sb.appendCodePoint(ch);
                }
                if (ch != EOF && ch != ')' && !Character.isWhitespace(ch)) {
                    ch = EOF;
                    return new Token(Token.Type.ERROR,
                                     String.format("%s:%d: Unexpected character: '%c'", name, line, ch));
                }
                return new Token(Token.Type.INTEGER, sb.toString());
            }
            if (ch == '"') {
                ch = in.read(); // Eat the open-quote.
                StringBuilder sb = new StringBuilder();
                while (ch != EOF && ch != '"') {
                    if (ch == '\\') {
                        ch = in.read(); // Eat the first backslash.
                        switch (ch) {
                        case EOF:
                            return new Token(Token.Type.ERROR,
                                             String.format("%s:%d: Unexpected end of input", name, line));
                        case 't':
                            sb.append('\t');
                            break;
                        case 'n':
                            sb.append('\n');
                            break;
                        case '\\':
                            sb.append('\\');
                            break;
                        case '"':
                            sb.append('"');
                            break;
                        default:
                            sb.appendCodePoint(ch);
                            break;
                        }
                    } else {
                        sb.appendCodePoint(ch);
                    }
                    ch = in.read(); // Eat the character.
                }
                if (ch == EOF) {
                    return new Token(Token.Type.ERROR,
                                     String.format("%s:%d: Unexpected end of input", name, line));
                }
                ch = in.read(); // Eat the close-quote.
                return new Token(Token.Type.STRING, sb.toString());
            }
            // Symbols
            StringBuilder sb = new StringBuilder();
            sb.appendCodePoint(ch);
            while ((ch = in.read()) != EOF && ch != ')' && !Character.isWhitespace(ch)) {
                sb.appendCodePoint(ch);
            }
            System.out.printf("Read symbol %s%n", sb.toString());
            return new Token(Token.Type.SYMBOL, sb.toString());
        } catch (IOException err) {
            ch = EOF;
            return new Token(Token.Type.ERROR, err.toString());
        }
    }
}
