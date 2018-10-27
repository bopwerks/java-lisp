package io.xlat.lisp;

// import junit.framework.Test;
// import junit.framework.TestCase;
// import junit.framework.TestSuite;
import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertTimeout;
// import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
// import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;

public class LexerTest {
    @Test
    public void testEOF() {
        StringReader sr = new StringReader("");
        Lexer L = new Lexer("stdin", sr);
        Token tok = L.peek();
        assertNotNull(tok);
        assertEquals(tok.type(), Token.Type.EOF);
    }

    @Test
    public void testOpenParen() {
        StringReader sr = new StringReader("(");
        Lexer L = new Lexer("stdin", sr);
        Token expected = new Token(Token.Type.LPAREN, '(');
        Token actual = L.peek();
        assertEquals(expected, actual);
    }

    @Test
    public void testCloseParen() {
        StringReader sr = new StringReader(")");
        Lexer L = new Lexer("stdin", sr);
        Token expected = new Token(Token.Type.RPAREN, ')');
        Token actual = L.peek();
        assertEquals(expected, actual);
    }

    @Test
    public void testInteger() {
        StringReader sr = new StringReader("42");
        Lexer L = new Lexer("stdin", sr);
        Token expected = new Token(Token.Type.INTEGER, "42");
        Token actual = L.peek();
        assertEquals(expected, actual);
    }

    @Test
    public void testString() {
        StringReader sr = new StringReader("\"hello\"");
        Lexer L = new Lexer("stdin", sr);
        Token expected = new Token(Token.Type.STRING, "hello");
        Token actual = L.peek();
        assertEquals(expected, actual);
    }

    @Test
    public void testStringEscape() {
        StringReader sr = new StringReader("\"h\nel\t\\\"lo\"");
        Lexer L = new Lexer("stdin", sr);
        Token expected = new Token(Token.Type.STRING, "h\nel\t\"lo");
        Token actual = L.peek();
        assertEquals(expected, actual);
    }

    @Test
    public void testStringEOF() {
        StringReader sr = new StringReader("\"hello");
        Lexer L = new Lexer("stdin", sr);
        Token expected = new Token(Token.Type.ERROR, "");
        Token actual = L.peek();
        assertEquals(expected.type(), actual.type());
        // assertThrows(SyntaxError.class, () -> { L.peek(); });
    }

    @Test
    public void testEmptyString() {
        StringReader sr = new StringReader("\"\"");
        Lexer L = new Lexer("stdin", sr);
        Token expected = new Token(Token.Type.STRING, "");
        Token actual = L.peek();
        assertEquals(expected, actual);
    }

    @Test
    public void testSymbol() {
        StringReader sr = new StringReader("foo");
        Lexer L = new Lexer("stdin", sr);
        Token expected = new Token(Token.Type.SYMBOL, "foo");
        Token actual = L.peek();
        assertEquals(expected, actual);
    }
}
