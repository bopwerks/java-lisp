package io.xlat.lisp;

import java.util.List;

class Stringer {
    public static String stringify(Object expr) {
        if (expr == null) {
            return "()";
        }
        if (expr instanceof Cons) {
            StringBuilder sb = new StringBuilder();
            Cons cons = (Cons) expr;
            sb.append('(');
            sb.append(stringify(cons.car));
            if (cons.cdr != null) {
                if (!(cons.cdr instanceof Cons)) {
                    sb.append(" . ");
                    sb.append(stringify(cons.cdr));
                } else {
                    for (cons = (Cons) cons.cdr; cons != null; cons = (Cons) cons.cdr) {
                        sb.append(' ');
                        sb.append(stringify(cons.car));
                    }
                }
            }
            sb.append(')');
            System.out.println(sb.toString());
            return sb.toString();
        } else if (expr instanceof Symbol) {
            Symbol sym = (Symbol) expr;
            return sym.toString();
        } else if (expr instanceof String) {
            StringBuilder sb = new StringBuilder();
            sb.append('"');
            sb.append(expr.toString());
            sb.append('"');
            return sb.toString();
        } else {
            return expr.toString();
        }
    }
}
