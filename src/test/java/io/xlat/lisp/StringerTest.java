package io.xlat.lisp;

import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertTimeout;
// import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
// import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;

public class StringerTest {
    @Test
    public void testInteger() {
        StringReader sr = new StringReader("42");
        Lexer L = new Lexer("stdin", sr);
        Object obj = Reader.read(L);
        String s = Stringer.stringify(obj);
        assertEquals(s, "42");
    }
    
    @Test
    public void testString() {
        StringReader sr = new StringReader("\"hello\"");
        Lexer L = new Lexer("stdin", sr);
        Object obj = Reader.read(L);
        String s = Stringer.stringify(obj);
        assertEquals(s, "\"hello\"");
    }

    @Test
    public void testSymbol() {
        StringReader sr = new StringReader("foo");
        Lexer L = new Lexer("stdin", sr);
        Object obj = Reader.read(L);
        String s = Stringer.stringify(obj);
        assertEquals(s, "foo");
    }

    @Test
    public void testEmptyList() {
        StringReader sr = new StringReader("()");
        Lexer L = new Lexer("stdin", sr);
        Object obj = Reader.read(L);
        assertNull(obj);
        String s = Stringer.stringify(obj);
        assertEquals(s, "()");
    }

    @Test
    public void testListOneItem() {
        StringReader sr = new StringReader("(42)");
        Lexer L = new Lexer("stdin", sr);
        Object obj = Reader.read(L);
        String s = Stringer.stringify(obj);
        assertEquals(s, "(42)");
    }

    @Test
    public void testListTwoItems() {
        StringReader sr = new StringReader("(42 \"hello\")");
        Lexer L = new Lexer("stdin", sr);
        Object obj = Reader.read(L);
        String s = Stringer.stringify(obj);
        assertEquals(s, "(42 \"hello\")");
    }

    @Test
    public void testNestedList() {
        StringReader sr = new StringReader("((42 \"hello\"))");
        Lexer L = new Lexer("stdin", sr);
        Object obj = Reader.read(L);
        String s = Stringer.stringify(obj);
        assertEquals(s, "((42 \"hello\"))");
    }
}
