package io.xlat.lisp;

class Token {
    public enum Type {
        EOF,
        ERROR,
        INTEGER,
        LPAREN,
        RPAREN,
        STRING,
        SYMBOL
    }
    
    private String lexeme;
    private Type type;

    public Token(Type type, String lexeme) {
        if (lexeme == null) {
            throw new IllegalArgumentException("Lexeme must be non-null");
        }
        this.type = type;
        this.lexeme = lexeme;
    }

    public Token(Type type, int ch) {
        this(type, new StringBuilder().appendCodePoint(ch).toString());
    }

    public String lexeme() {
        return lexeme;
    }

    public Type type() {
        return type;
    }

    public String toString() {
        return lexeme;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Token)) {
            return false;
        }
        Token tok = (Token) obj;
        return tok.lexeme().equals(lexeme) && tok.type().equals(type);
    }

    public int hashCode() {
        return lexeme.hashCode() * 31 + type.hashCode();
    }
}
