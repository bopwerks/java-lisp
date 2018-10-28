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

public class LambdaTest {
    @Test
    public void testLambda() {
        Env env = new Env(null);
        env.put("+", new AddFn());
        Cons params = Cons.fromElements(new Symbol("a"), new Symbol("b"));
        Cons body = Cons.fromElements(Cons.fromElements(new Symbol("+"), new Symbol("a"), new Symbol("b")));
        Function fn = new Lambda(params, body);
        Cons args = Cons.fromElements(3, 4);
        Object val = fn.apply(args, env);
        assertEquals(val, 7);
    }
}
