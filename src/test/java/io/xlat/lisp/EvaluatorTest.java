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

public class EvaluatorTest {
    @Test
    public void testAdd() {
        StringReader sr = new StringReader("(+ 3 4)");
        Lexer L = new Lexer("stdin", sr);
        Object expr = Reader.read(L);
        Object val = Evaluator.evaluate(expr, new Env(null));
        assertEquals(val, 7);
    }

    @Test
    public void testSubtract() {
        StringReader sr = new StringReader("(- 4 3)");
        Lexer L = new Lexer("stdin", sr);
        Object expr = Reader.read(L);
        Object val = Evaluator.evaluate(expr, new Env(null));
        assertEquals(val, 1);
    }

    @Test
    public void testCons() {
        StringReader sr = new StringReader("(cons 3 nil)");
        Lexer L = new Lexer("stdin", sr);
        Object expr = Reader.read(L);
        Object val = Evaluator.evaluate(expr, new Env(null));
        assertTrue(val instanceof Cons);
        Cons c = (Cons) val;
        assertEquals(c.car, 3);
        assertNull(c.cdr);
    }

    @Test
    public void testQuote() {
        StringReader sr = new StringReader("(quote (hello))");
        Lexer L = new Lexer("stdin", sr);
        Object expr = Reader.read(L);
        Object val = Evaluator.evaluate(expr, new Env(null));
        assertTrue(val instanceof Cons);
        Cons c = (Cons) val;
        assertEquals(c.car, new Symbol("hello"));
        assertNull(c.cdr);
    }

    @Test
    public void testDoubleEqualsTrue() {
        StringReader sr = new StringReader("(= 4 4)");
        Lexer L = new Lexer("stdin", sr);
        Object expr = Reader.read(L);
        Object val = Evaluator.evaluate(expr, new Env(null));
        assertEquals(val, Symbol.T);
    }
    
    @Test
    public void testDoubleEqualsFalse() {
        StringReader sr = new StringReader("(= 4 3)");
        Lexer L = new Lexer("stdin", sr);
        Object expr = Reader.read(L);
        Object val = Evaluator.evaluate(expr, new Env(null));
        assertEquals(val, null);
    }

    @Test
    public void testLessThanTrue() {
        StringReader sr = new StringReader("(< 3 4)");
        Lexer L = new Lexer("stdin", sr);
        Object expr = Reader.read(L);
        Object val = Evaluator.evaluate(expr, new Env(null));
        assertEquals(val, Symbol.T);
    }

    @Test
    public void testLessThanFalse() {
        StringReader sr = new StringReader("(< 4 3)");
        Lexer L = new Lexer("stdin", sr);
        Object expr = Reader.read(L);
        Object val = Evaluator.evaluate(expr, new Env(null));
        assertEquals(val, null);
    }
    
    @Test
    public void testGreaterThanTrue() {
        StringReader sr = new StringReader("(> 4 3)");
        Lexer L = new Lexer("stdin", sr);
        Object expr = Reader.read(L);
        Object val = Evaluator.evaluate(expr, new Env(null));
        assertEquals(val, Symbol.T);
    }

    @Test
    public void testGreaterThanFalse() {
        StringReader sr = new StringReader("(> 3 4)");
        Lexer L = new Lexer("stdin", sr);
        Object expr = Reader.read(L);
        Object val = Evaluator.evaluate(expr, new Env(null));
        assertEquals(val, null);
    }

    @Test
    public void testLessThanEqTrue() {
        StringReader sr = new StringReader("(<= 3 3)");
        Lexer L = new Lexer("stdin", sr);
        Object expr = Reader.read(L);
        Object val = Evaluator.evaluate(expr, new Env(null));
        assertEquals(val, Symbol.T);
    }

    @Test
    public void testLessThanEqFalse() {
        StringReader sr = new StringReader("(<= 4 3)");
        Lexer L = new Lexer("stdin", sr);
        Object expr = Reader.read(L);
        Object val = Evaluator.evaluate(expr, new Env(null));
        assertEquals(val, null);
    }
    
    @Test
    public void testGreaterThanEqTrue() {
        StringReader sr = new StringReader("(>= 4 4)");
        Lexer L = new Lexer("stdin", sr);
        Object expr = Reader.read(L);
        Object val = Evaluator.evaluate(expr, new Env(null));
        assertEquals(val, Symbol.T);
    }

    @Test
    public void testGreaterThanEqFalse() {
        StringReader sr = new StringReader("(>= 3 4)");
        Lexer L = new Lexer("stdin", sr);
        Object expr = Reader.read(L);
        Object val = Evaluator.evaluate(expr, new Env(null));
        assertEquals(val, null);
    }

    @Test
    public void testOrSomeTrue() {
        StringReader sr = new StringReader("(or (> 3 4) (= 5 5))");
        Lexer L = new Lexer("stdin", sr);
        Object expr = Reader.read(L);
        Object val = Evaluator.evaluate(expr, new Env(null));
        assertEquals(val, Symbol.T);
    }

    @Test
    public void testOrFalse() {
        StringReader sr = new StringReader("(or (> 3 4) (= 5 4))");
        Lexer L = new Lexer("stdin", sr);
        Object expr = Reader.read(L);
        Object val = Evaluator.evaluate(expr, new Env(null));
        assertEquals(val, null);
    }

    @Test
    public void testAndTrue() {
        StringReader sr = new StringReader("(and (> 4 3) (= 5 5))");
        Lexer L = new Lexer("stdin", sr);
        Object expr = Reader.read(L);
        Object val = Evaluator.evaluate(expr, new Env(null));
        assertEquals(val, Symbol.T);
    }

    @Test
    public void testAndFalse() {
        StringReader sr = new StringReader("(and (> 4 3) (= 5 3))");
        Lexer L = new Lexer("stdin", sr);
        Object expr = Reader.read(L);
        Object val = Evaluator.evaluate(expr, new Env(null));
        assertEquals(val, null);
    }

    @Test
    public void testIfCons() {
        StringReader sr = new StringReader("(if (< 3 4) 5 6)");
        Lexer L = new Lexer("stdin", sr);
        Object expr = Reader.read(L);
        Object val = Evaluator.evaluate(expr, new Env(null));
        assertEquals(val, 5);
    }

    @Test
    public void testIfAlt() {
        StringReader sr = new StringReader("(if (> 3 4) 5 6)");
        Lexer L = new Lexer("stdin", sr);
        Object expr = Reader.read(L);
        Object val = Evaluator.evaluate(expr, new Env(null));
        assertEquals(val, 6);
    }
}
