import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;

import java.io.*;
import java.util.*;
import java.util.zip.CRC32;
import java.math.BigInteger;

public class P2J5Test {

    private static final int SEED = 12345;
    private static final BigInteger TWO = new BigInteger("2");
    
    @Test public void testFibonacciSum() {
        CRC32 check = new CRC32();
        Random rng = new Random(SEED);
        BigInteger curr = BigInteger.ONE;
        for(int i = 0; i < 1000; i++) {
            List<BigInteger> result = P2J5.fibonacciSum(curr);
            for(BigInteger b: result) {
                check.update(b.toString().getBytes());
            }
            curr = curr.add(new BigInteger("" + (rng.nextInt(5) + 1)));
            curr = curr.multiply(TWO);
        }
        assertEquals(702310714L, check.getValue());
    }
    
    @Test public void testSevenZero() {
        CRC32 check = new CRC32();
        for(int i = 2; i < 300; i++) {
            BigInteger result = P2J5.sevenZero(i);
            check.update(result.toString().getBytes());
        }
        assertEquals(3791754529L, check.getValue());
    }
}
