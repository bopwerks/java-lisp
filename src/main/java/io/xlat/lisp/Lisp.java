package io.xlat.lisp;

import java.io.PushbackReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

public class Lisp implements Runnable {
    private java.io.Reader input;
    private Writer output;
    private Lexer lexer;
    private Env env;
    
    public Lisp(java.io.Reader input, Writer output) {
        if (input == null) {
            throw new IllegalArgumentException("Input must be non-null");
        }
        if (output == null) {
            throw new IllegalArgumentException("Output must be non-null");
        }
        this.input = input;
        this.output = output;
        this.lexer = new Lexer(input.toString(), input);

        env = new Env(null);
        env.put("+", new AddFn());
        env.put("-", new SubtractFn());
        env.put("*", new MultiplyFn());
        env.put("cons", new ConsFn());
        env.put("=", new EqualsFn());
        env.put("sleep", new SleepFn());
        env.put("mkchan", new MakeChanFn());
        env.put("send", new SendFn());
        env.put("receive", new ReceiveFn());
        env.put("print", new PrintFn());
        env.put("null?", new NullFn());
        env.put("close", new CloseFn());
    }

    private Object read() {
        Object val = Reader.read(lexer);
        return val;
    }

    private Object eval(Object expr) {
        Object val = Evaluator.eval(expr, env);
        return val;
    }

    private void print(Object expr) throws IOException {
        if (expr == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Stringer.stringify(expr));
        sb.append('\n');
        output.write(sb.toString());
        output.flush();
    }

    public void run() {
        try {
            for (;;) {
                print(eval(read()));
            }
        } catch (IOException e) {
        } catch (QuitException e) {
        }
    }
    
    public static void main(String[] args) {
        java.io.Reader isr = new InputStreamReader(System.in);
        Writer osw = new OutputStreamWriter(System.out);
        Lisp lisp = new Lisp(isr, osw);
        lisp.run();
    }
}
