# java-lisp

This is a Lisp interpreter written in Java. It has limitations. It provides CSP-style channels for concurrent programming.

## Example

The following code launches 500 threads. Each thread reads a number from an input channel and writes its increment to an output channel. The result is printed.

```
(define (add1 input output)
    (let ((n (receive input)))
      (if (null? n)
          (close output)
          (begin
           (send output (+ 1 n))
           (add1 input output)))))

(define (generate nthreads input)
    (if (= nthreads 0)
        input
        (let ((output (mkchan)))
          (go (lambda () (add1 input output)))
          (generate (- nthreads 1) output))))

(let ((input (mkchan)))
  (let ((output (generate 500 input)))
    (send input 100)
    (close input)
    (print "%d%n" (receive output))
    (receive output)))
```

## Building

This project uses Maven. Run `mvn compile test` and run the `io.xlat.lisp.Lisp` class.

## Bugs

Yes.
