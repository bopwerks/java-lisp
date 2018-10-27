package io.xlat.lisp;

class SyntaxError extends java.lang.Error {
    public SyntaxError(String fmt, Object ...args) {
        super(String.format(fmt, args));
    }
}
