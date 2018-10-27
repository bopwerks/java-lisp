package io.xlat.lisp;

class LookupError extends java.lang.Error {
    public LookupError(Symbol sym) {
        super(String.format("Symbol '%s' is undefined", sym));
    }

    public LookupError(String fmt, Object ...args) {
        super(String.format(fmt, args));
    }
}
