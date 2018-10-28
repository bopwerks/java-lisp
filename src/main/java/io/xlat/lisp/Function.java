package io.xlat.lisp;

interface Function {
    public Object apply(Cons args, Env env);
}
