package io.xlat.lisp;

import java.util.concurrent.Semaphore;

class Channel<T> {
    private T obj;
    private Semaphore send;
    private Semaphore recv;
    private Semaphore sync;
    private boolean isClosed;

    public Channel() {
        send = new Semaphore(1);
        recv = new Semaphore(0);
        sync = new Semaphore(0);
    }

    public void send(T obj) {
        try {
            send.acquire();
            if (isClosed) {
                send.release();
                throw new IllegalStateException("Can't send on closed channel");
            }
            this.obj = obj;
            recv.release();
            sync.acquire();
        } catch (InterruptedException e) {
        }
    }

    public void close() {
        send(null);
    }

    public T receive() {
        try {
            recv.acquire();
            T rval = obj;
            if (rval == null) {
                isClosed = true;
            }
            sync.release();
            send.release();
            return rval;
        } catch (InterruptedException e) {
            return null;
        }
    }
}
