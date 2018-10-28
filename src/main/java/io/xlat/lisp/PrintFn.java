package io.xlat.lisp;

import java.util.List;
import java.util.ArrayList;

class PrintFn implements Function {
    public Object apply(Cons args, Env env) {
        if (args == null) {
            System.out.println();
            return null;
        }
        Object obj = args.car;
        if (!(obj instanceof String)) {
            throw new SyntaxError("Print function expects format string as first argument");
        }
        String fmt = (String) obj;
        List<Object> vals = new ArrayList<Object>();
        while (args.cdr != null) {
            args = (Cons) args.cdr;
            Object val = Evaluator.eval(args.car, env);
            vals.add(val);
        }
        System.out.printf(fmt, vals.toArray());
        return null;
    }
}
