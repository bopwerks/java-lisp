package io.xlat.lisp;

class Cons {
    public Object car;
    public Object cdr;

    public boolean equals(Object obj) {
        if (!(obj instanceof Cons)) {
            return false;
        }
        Cons c = (Cons) obj;
        return c.car == car && c.cdr == cdr;
    }

    public int hashCode() {
        int h1, h2;

        h1 = h2 = 0;
        if (car != null) {
            h1 = car.hashCode();
        }
        if (cdr != null) {
            h2 = car.hashCode();
        }
        return h1 * 31 + h2;
    }

    public Object first() {
        return nth(1);
    }

    public Object second() {
        return nth(2);
    }

    public Object third() {
        return nth(3);
    }

    public Object nth(int n) {
        Cons cons = this;
        while (--n > 0) {
            cons = (Cons) cons.cdr;
        }
        return cons.car;
    }
}
