package io.xlat.lisp;

import java.util.List;
import java.util.ArrayList;

class EqualsFn extends AbstractFunction {
    protected List<Class> arity() {
        List<Class> params = new ArrayList<Class>();
        params.add(Integer.class);
        params.add(Integer.class);
        return params;
    }

    protected Object run(Env env, Object... args) {
        Integer a = (Integer) args[0];
        Integer b = (Integer) args[1];
        if (a.equals(b)) {
            return Symbol.T;
        } else {
            return null;
        }
    }
}
