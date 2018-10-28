package io.xlat.lisp;

import java.util.List;
import java.util.ArrayList;

class MakeChanFn extends AbstractFunction {
    protected List<Class> arity() {
        return new ArrayList<Class>();
    }

    protected Object run(Env env, Object... args) {
        return new Channel<Object>();
    }
}
