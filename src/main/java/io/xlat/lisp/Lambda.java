package io.xlat.lisp;

class Lambda implements Function {
    private Cons params;
    private Cons body;
    private Env env;
    
    public Lambda(Cons params, Cons body) {
        this.params = params;
        this.body = body;
    }

    public String toString() {
        return String.format("Lambda@%d", hashCode());
    }

    public Object apply(Cons args, Env parent) {
        Env env = new Env(parent);
        // Bind args to params in env
        Cons p = params;
        Cons a = args;
        while (p != null && a != null) {
            Symbol sym = (Symbol) p.car;
            Object val = a.car;
            env.put(sym, val);
            p = (Cons) p.cdr;
            a = (Cons) a.cdr;
        }
        if (p != null || a != null) {
            throw new SyntaxError("Parameter-argument mismatch");
        }
        // Evaluate each expression in the body
        p = body;
        Object val = null;
        while (p != null) {
            val = Evaluator.eval(p.car, env);
            p = (Cons) p.cdr;
        }
        return val;
    }
}
