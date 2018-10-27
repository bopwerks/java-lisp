package io.xlat.lisp;

// import junit.framework.Test;
// import junit.framework.TestCase;
// import junit.framework.TestSuite;
import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertTimeout;
// import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
// import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;

public class ChannelTest {
    @Test
    public void testChannel() {
        Channel<Integer> nums = new Channel<Integer>();
        Thread t1 = new Thread(() -> {
                nums.send(42);
                nums.close();
            });
        Thread t2 = new Thread(() -> {
                Integer n = nums.receive();
                assertEquals(n, new Integer(42));
                n = nums.receive();
                assertNull(n);
            });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
        }
    }
}
