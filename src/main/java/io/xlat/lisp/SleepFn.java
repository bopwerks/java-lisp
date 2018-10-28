package io.xlat.lisp;

import java.util.List;
import java.util.ArrayList;

class SleepFn extends AbstractFunction {
    protected List<Class> arity() {
        List<Class> params = new ArrayList<Class>();
        params.add(Integer.class);
        return params;
    }

    protected Object run(Env env, Object... args) {
        Integer ms = (Integer) args[0];
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
        return null;
    }
}
