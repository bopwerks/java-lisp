package io.xlat.lisp;

import java.util.List;
import java.util.ArrayList;

class NullFn extends AbstractFunction {
    protected List<Class> arity() {
        List<Class> params = new ArrayList<Class>();
        params.add(Object.class);
        return params;
    }

    protected Object run(Env env, Object... args) {
        if (args[0] == null) {
            return Symbol.T;
        } else {
            return null;
        }
    }
}
