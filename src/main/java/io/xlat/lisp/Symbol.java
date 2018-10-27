package io.xlat.lisp;

class Symbol {
    public final static Symbol NIL = new Symbol("nil");
    public final static Symbol T = new Symbol("t");

    private String str;
    
    public Symbol(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Symbol must be non-null");
        }
        this.str = str;
    }
    
    public String toString() {
        return str;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Symbol)) {
            return false;
        }
        Symbol sym = (Symbol) obj;
        return sym.str.equals(this.str);
    }

    public int hashCode() {
        return str.hashCode();
    }
}
