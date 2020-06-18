import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;

import java.io.*;
import java.util.*;
import java.util.zip.CRC32;

public class P2J4Test {

    private static final int SEED = 12345;
    
    @Test public void testFirstMissingPositive() {
        Random rng = new Random(SEED);
        List<Integer> items = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            items.clear();
            int miss = 1 + rng.nextInt(10000);
            for(int j = 1; j < miss; j++) {
                int rep = 1 + rng.nextInt(10);
                for(int k = 0; k < rep; k++) { items.add(j); }
            }
            int more = rng.nextInt(1000);
            for(int j = 0; j < more; j++) {
                items.add(rng.nextInt(100000000));
            }
            Collections.shuffle(items, rng);
            int ans = P2J4.firstMissingPositive(items);
            assertEquals(miss, ans);
        }
    }
    
    @Test public void testRunningMedianOfThree() {
        Random rng = new Random(SEED);
        CRC32 check = new CRC32();
        List<Integer> items = new ArrayList<>();
        for(int i = 0; i < 4000; i++) {
            items.clear();
            for(int j = 0; j < i; j++) { items.add(rng.nextInt(100000)); }
            List<Integer> ans = P2J4.runningMedianOfThree(items);
            check.update(ans.size());
            for(int e: ans) { check.update(e); }
        }
        assertEquals(4053632720L, check.getValue());
    }
    
    @Test public void testSortByElementFrequency() {
        Random rng = new Random(SEED);
        CRC32 check = new CRC32();
        List<Integer> items = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            items.clear();
            for(int j = 0; j < i; j++) {
                int rep = rng.nextInt(100), e = rng.nextInt(10000000);
                for(int k = 0; k < rep; k++) { items.add(e); }
            }
            Collections.shuffle(items, rng);
            P2J4.sortByElementFrequency(items);
            for(int e: items) { check.update(e); }
        }
        assertEquals(3052228947L, check.getValue());
    }
    
    @Test public void testFactorFactorial() {
        CRC32 check = new CRC32();
        for(int n = 2; n < 1000; n++) {
            List<Integer> ans = P2J4.factorFactorial(n);
            check.update(ans.size());
            for(int e: ans) { check.update(e); }
        }
        assertEquals(1488430729L, check.getValue());
    }
}
