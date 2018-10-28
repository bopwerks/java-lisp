package io.xlat.lisp;

import java.util.List;

class Evaluator {
    public static Object eval(Object expr, Env env) {
        if (env == null) {
            throw new IllegalArgumentException("Environment must be non-null");
        }
        if (expr == null) {
            return null;
        }
        // Symbols
        if (isSymbol(expr)) {
            Symbol sym = (Symbol) expr;
            if (sym.equals(Symbol.NIL)) {
                return null;
            }
            if (sym.equals(Symbol.T)) {
                return Symbol.T;
            }
            Object val = env.get(sym);
            if (val != null && val.equals(Symbol.UNDEFINED)) {
                throw new LookupError(sym);
            }
            return val;
        }
        // Strings, Integers, and other atomic objects
        if (isAtom(expr)) {
            return expr;
        }
        // Special forms
        Cons list = (Cons) expr;
        if (list.car == null) {
            throw new NullPointerException("nil is not a function");
        }
        if (list.car instanceof Symbol) {
            Symbol funcSym = (Symbol) list.car;
            String funcName = funcSym.toString();
            if (funcName.equals("if")) {
                Object cond = eval(list.second(), env);
                if (cond != null) {
                    return eval(list.third(), env);
                } else {
                    return eval(list.nth(4), env);
                }
            } else if (funcName.equals("or")) {
                Cons cons = list;
                do {
                    cons = (Cons) cons.cdr;
                    Object val = eval(cons.car, env);
                    if (val != null && val.equals(Symbol.T)) {
                        return Symbol.T;
                    }
                } while (cons.cdr != null);
                return null;
            } else if (funcName.equals("and")) {
                Cons cons = list;
                do {
                    cons = (Cons) cons.cdr;
                    Object val = eval(cons.car, env);
                    if (val == null) {
                        return null;
                    }
                } while (cons.cdr != null);
                return Symbol.T;
            } else if (funcName.equals("quote")) {
                return list.second();
            } else if (funcName.equals("lambda")) {
                list = (Cons) list.cdr;
                Cons params = (Cons) list.car;
                Cons body = (Cons) list.cdr;
                return new Lambda(params, body);
            } else if (funcName.equals("define")) {
                list = (Cons) list.cdr;
                Cons params = (Cons) list.car;
                Symbol func = (Symbol) params.car;
                params = (Cons) params.cdr;
                Cons body = (Cons) list.cdr;
                env.put(func, new Lambda(params, body));
                return null;
            } else if (funcName.equals("go")) {
                list = (Cons) list.cdr;
                Cons lambda = (Cons) list.car;
                Object fnobj = eval(lambda, env);
                if (fnobj == null || !(fnobj instanceof Function)) {
                    throw new SyntaxError("go form requires a lambda");
                }
                Function func = (Function) fnobj;
                Thread t = new Thread(() -> func.apply(null, env));
                t.start();
                return null;
            } else if (funcName.equals("begin")) {
                list = (Cons) list.cdr;
                Object val = null;
                while (list != null) {
                    val = eval(list.car, env);
                    list = (Cons) list.cdr;
                }
                return val;
            } else if (funcName.equals("let")) {
                list = (Cons) list.cdr;
                Cons pairs = (Cons) list.car;
                Cons body = (Cons) list.cdr;
                
                Env newenv = new Env(env);
                Cons pair = (Cons) pairs.car;
                Symbol key = (Symbol) pair.first();
                Object val = eval(pair.second(), env);
                env.put(key, val);
                while (pairs.cdr != null) {
                    pairs = (Cons) pairs.cdr;
                    pair = (Cons) pairs.car;
                    key = (Symbol) pair.first();
                    val = eval(pair.second(), env);
                    env.put(key, val);
                }
                val = null;
                while (body != null) {
                    val = eval(body.car, env);
                    body = (Cons) body.cdr;
                }
                return val;
            }
        }
        // Function calls
        Cons evalList = mapEval(list, env);
        if (evalList.car == null || !(evalList.car instanceof Function)) {
            throw new LookupError("No such function: %s", Stringer.stringify(list.car));
        }
        Function func = (Function) evalList.car;
        return func.apply((Cons) evalList.cdr, env);
    }

    private static Cons mapEval(Cons list, Env env) {
        if (list == null) {
            return null;
        }
        Cons rval = new Cons();
        Cons c = rval;
        c.car = eval(list.car, env);
        c.cdr = null;
        Cons p = c;
        while (list.cdr != null) {
            list = (Cons) list.cdr;
            p.cdr = c = new Cons();
            c.car = eval(list.car, env);
            c.cdr = null;
            p = c;
        }
        return rval;
    }
    
    private static boolean isSymbol(Object expr) {
        return expr instanceof Symbol;
    }

    private static boolean isAtom(Object expr) {
        return expr != null && !(expr instanceof Cons);
    }
}
