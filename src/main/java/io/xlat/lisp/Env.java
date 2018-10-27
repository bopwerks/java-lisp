package io.xlat.lisp;

import java.util.Map;
import java.util.HashMap;

class Env {
    private Map<Symbol, Object> map;
    private Env parent;
    
    public Env(Env parent) {
        this.parent = parent;
        map = new HashMap<Symbol, Object>();
    }

    public Object get(Symbol sym) {
        Object expr = map.get(sym);
        if (expr != null) {
            return expr;
        }
        if (parent == null) {
            return null;
        }
        return parent.get(sym);
    }

    public void put(Symbol sym, Object expr) {
        map.put(sym, expr);
    }
}
