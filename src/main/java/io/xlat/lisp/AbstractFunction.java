package io.xlat.lisp;

import java.util.List;
import java.util.ArrayList;

abstract class AbstractFunction implements Function {
    // abstract protected String name();

    abstract protected List<Class> arity();

    abstract protected Object run(Env env, Object... args);

    public Object apply(Cons args, Env env) {
        // Verify that types match.
        List<Object> evaluatedArgs = new ArrayList<Object>();
        while (args != null) {
            Object arg = Evaluator.eval(args.car, env);
            if (arg instanceof Error) {
                return arg;
            }
            evaluatedArgs.add(arg);
            args = (Cons) args.cdr;
        }
        List<Class> types = arity();
        if (evaluatedArgs.size() != types.size()) {
            throw new SyntaxError("Function expects %d arguments", types.size());
        }
        for (int i = 0; i < types.size(); ++i) {
            Object arg = evaluatedArgs.get(i);
            if (arg == null) {
                continue;
            }
            Class argType = evaluatedArgs.get(i).getClass();
            Class expectedType = types.get(i);
            if (!expectedType.isAssignableFrom(argType)) {
                throw new SyntaxError("Function expects a %s at argument %d",
                                      expectedType, i+1);
            }
        }
        return run(env, evaluatedArgs.toArray());
    }

    public String toString() {
        return String.format("Lambda@%d", hashCode());
    }
}
