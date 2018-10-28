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
import java.util.List;

public class ReaderTest {
    @Test
    public void testEOF() {
        StringReader sr = new StringReader("");
        Lexer L = new Lexer("stdin", sr);
        assertThrows(QuitException.class, () -> Reader.read(L));
    }
    
    @Test
    public void testInteger() {
        StringReader sr = new StringReader("42");
        Lexer L = new Lexer("stdin", sr);
        Object obj = Reader.read(L);
        assertEquals(obj, 42);
    }

    @Test
    public void testString() {
        StringReader sr = new StringReader("\"hello\"");
        Lexer L = new Lexer("stdin", sr);
        Object obj = Reader.read(L);
        assertEquals(obj, "hello");
    }

    @Test
    public void testSymbol() {
        StringReader sr = new StringReader("foo");
        Lexer L = new Lexer("stdin", sr);
        Object obj = Reader.read(L);
        assertEquals(obj, new Symbol("foo"));
    }

    @Test
    public void testError() {
        StringReader sr = new StringReader("\"");
        Lexer L = new Lexer("stdin", sr);
        Object obj = Reader.read(L);
        assertTrue(obj instanceof SyntaxError);
    }

    @Test
    public void testEmptyList() {
        StringReader sr = new StringReader("()");
        Lexer L = new Lexer("stdin", sr);
        Object obj = Reader.read(L);
        assertNull(obj);
    }

    @Test
    public void testListOneElement() {
        StringReader sr = new StringReader("(42)");
        Lexer L = new Lexer("stdin", sr);
        Object obj = Reader.read(L);
        assertTrue(obj instanceof Cons);
        Cons cons = (Cons) obj;
        assertEquals(cons.car, 42);
    }

    @Test
    public void testListTwoElements() {
        StringReader sr = new StringReader("(42 \"hello\")");
        Lexer L = new Lexer("stdin", sr);
        Object obj = Reader.read(L);
        assertTrue(obj instanceof Cons);
        Cons cons = (Cons) obj;
        assertEquals(cons.car, 42);
        cons = (Cons) cons.cdr;
        assertTrue(cons instanceof Cons);
        assertEquals(cons.car, "hello");
        assertNull(cons.cdr);
    }

    @Test
    public void testNestedList() {
        StringReader sr = new StringReader("((42 \"hello\"))");
        Lexer L = new Lexer("stdin", sr);
        
        Object obj = Reader.read(L);
        assertTrue(obj instanceof Cons);
        Cons cons1 = (Cons) obj;

        assertTrue(cons1.car instanceof Cons);
        assertNull(cons1.cdr);

        Cons cons2 = (Cons) cons1.car;
        assertEquals(cons2.car, 42);
        assertTrue(cons2.cdr instanceof Cons);

        cons2 = (Cons) cons2.cdr;
        assertEquals(cons2.car, "hello");
        assertNull(cons2.cdr);
    }
}
