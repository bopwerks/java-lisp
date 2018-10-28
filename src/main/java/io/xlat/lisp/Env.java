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
        if (map.containsKey(sym)) {
            return map.get(sym);
        }
        if (parent == null) {
            return Symbol.UNDEFINED;
        }
        return parent.get(sym);
    }

    public Object get(String sym) {
        return get(new Symbol(sym));
    }

    public void put(Symbol sym, Object expr) {
        map.put(sym, expr);
    }

    public void put(String sym, Object expr) {
        put(new Symbol(sym), expr);
    }
}
