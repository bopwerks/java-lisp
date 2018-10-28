package io.xlat.lisp;

import java.util.List;
import java.util.ArrayList;

class ConsFn extends AbstractFunction {
    public String name() {
        return "cons";
    }
    
    protected List<Class> arity() {
        List<Class> params = new ArrayList<Class>();
        params.add(Object.class);
        params.add(Object.class);
        return params;
    }

    protected Object run(Env env, Object... args) {
        Cons c = new Cons();
        c.car = args[0];
        c.cdr = args[1];
        return c;
    }
}
