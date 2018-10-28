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

public class AddFnTest {
    @Test
    public void testGoodArity() {
        Function add = new AddFn();
        Env env = new Env(null);
        Cons args = Cons.fromElements(3, 4);
        Object val = add.apply(args, env);
        assertEquals(val, 7);
    }

    @Test
    public void testBadArity() {
        Function add = new AddFn();
        Env env = new Env(null);
        Cons args = Cons.fromElements(3, 4, 5);
        assertThrows(SyntaxError.class, () -> add.apply(args, env));
    }
}
