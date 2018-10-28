package io.xlat.lisp;

import java.util.List;
import java.util.ArrayList;

class CloseFn extends AbstractFunction {
    protected List<Class> arity() {
        List<Class> params = new ArrayList<Class>();
        params.add(Channel.class);
        return params;
    }

    protected Object run(Env env, Object... args) {
        Channel<Object> chan = (Channel<Object>) args[0];
        chan.close();
        return null;
    }
}
