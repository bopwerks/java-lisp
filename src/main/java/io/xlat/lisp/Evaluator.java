package io.xlat.lisp;

import java.util.List;

class Evaluator {
    private static boolean isSymbol(Object expr) {
        return expr instanceof Symbol;
    }

    private static boolean isAtom(Object expr) {
        return expr instanceof Integer || expr instanceof String || expr instanceof Symbol;
    }

    private static Object car(Object expr) {
        if (!(expr instanceof Cons)) {
            return null;
        }
        Cons cons = (Cons) expr;
        return cons.car;
    }

    private static Object cdr(Object expr) {
        if (!(expr instanceof Cons)) {
            return null;
        }
        Cons cons = (Cons) expr;
        return cons.cdr;
    }

    private static boolean isFunc(Object expr, String name) {
        if (!(expr instanceof List)) {
            return false;
        }
        List<Object> list = (List<Object>) expr;
        if (list.size() == 0) {
            return false;
        }
        Object first = list.get(0);
        if (!isSymbol(first)) {
            return false;
        }
        Symbol sym = (Symbol) first;
        return sym.toString().equals(name);
    }
    
    public static Object evaluate(Object expr, Env env) {
        if (expr == null) {
            throw new IllegalArgumentException("Expression must be non-null");
        }
        if (env == null) {
            throw new IllegalArgumentException("Environment must be non-null");
        }
        System.out.printf("Evaluating %s%n", Stringer.stringify(expr));
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
            if (val == null) {
                throw new LookupError(sym);
            }
            return val;
        }
        // Strings, Integers, and other atomic objects
        if (isAtom(expr)) {
            return expr;
        }
        // Expressions
        Cons list = (Cons) expr;
        Object funcSym = list.first();
        if (funcSym == null) {
            throw new NullPointerException("car(expr) should be non-null");
        }
        if (!(funcSym instanceof Symbol)) {
            throw new LookupError("%s is not a function");
        }
        String funcName = funcSym.toString();
        if (funcName.equals("eql?")) {
            Object a = evaluate(list.second(), env);
            Object b = evaluate(list.third(), env);
            if (a != null && b != null) {
                return a.equals(b);
            } else if (a == null && b == null) {
                return Symbol.T;
            } else {
                return null;
            }
        } else if (funcName.equals("=")) {
            if (((Integer) evaluate(list.second(), env)).equals((Integer) evaluate(list.third(), env))) {
                return Symbol.T;
            } else {
                return null;
            }
        } else if (funcName.equals("<")) {
            if ((Integer) evaluate(list.second(), env) < (Integer) evaluate(list.third(), env)) {
                return Symbol.T;
            } else {
                return null;
            }
        } else if (funcName.equals("<=")) {
            if ((Integer) evaluate(list.second(), env) <= (Integer) evaluate(list.third(), env)) {
                return Symbol.T;
            } else {
                return null;
            }
        } else if (funcName.equals(">")) {
            if ((Integer) evaluate(list.second(), env) > (Integer) evaluate(list.third(), env)) {
                return Symbol.T;
            } else {
                return null;
            }
        } else if (funcName.equals(">=")) {
            if ((Integer) evaluate(list.second(), env) >= (Integer) evaluate(list.third(), env)) {
                return Symbol.T;
            } else {
                return null;
            }
        } else if (funcName.equals("if")) {
            Object cond = evaluate(list.second(), env);
            if (cond != null && cond.equals(Symbol.T)) {
                return evaluate(list.third(), env);
            } else {
                return evaluate(list.nth(4), env);
            }
        } else if (funcName.equals("or")) {
            Cons cons = list;
            do {
                cons = (Cons) cons.cdr;
                Object val = evaluate(cons.car, env);
                if (val != null && val.equals(Symbol.T)) {
                    return Symbol.T;
                }
            } while (cons.cdr != null);
            return null;
        } else if (funcName.equals("and")) {
            Cons cons = list;
            do {
                cons = (Cons) cons.cdr;
                Object val = evaluate(cons.car, env);
                if (val == null) {
                    return null;
                }
            } while (cons.cdr != null);
            return Symbol.T;
        } else if (funcName.equals("mkchan")) {
            return new Channel<Object>();
        } else if (funcName.equals("close")) {
            Channel<Object> chan = (Channel<Object>) evaluate(list.second(), env);
            chan.close();
        } else if (funcName.equals("send")) {
            Channel<Object> chan = (Channel<Object>) evaluate(list.second(), env);
            Object obj = evaluate(list.third(), env);
            chan.send(obj);
        } else if (funcName.equals("receive")) {
            Channel<Object> chan = (Channel<Object>) evaluate(list.second(), env);
            return chan.receive();
        } else if (funcName.equals("quote")) {
            return list.second();
        } else if (funcName.equals("cons")) {
            Cons c = new Cons();
            c.car = evaluate(list.second(), env);
            c.cdr = evaluate(list.third(), env);
            return c;
        } else if (funcName.equals("+")) {
            return (Integer) evaluate(list.second(), env) + (Integer) evaluate(list.third(), env);
        } else if (funcName.equals("-")) {
            return (Integer) evaluate(list.second(), env) - (Integer) evaluate(list.third(), env);
        }
        return null;
    }
}
