import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;

import java.io.*;
import java.util.*;
import java.util.zip.CRC32;

public class P2J3Test {

    private static final int SEED = 12345;
    private static final int ROUNDS = 1000;
    
    @Test public void testPancakeScramble() throws IOException {
        CRC32 check = new CRC32();
        BufferedReader fr = new BufferedReader(
            new InputStreamReader(new FileInputStream("warandpeace.txt"), "UTF-8")
        );
        String line = fr.readLine();
        while(line != null) {
            String result = P2J3.pancakeScramble(line);
            check.update(result.getBytes());
            line = fr.readLine();
        }
        fr.close();
        assertEquals(1606800991L, check.getValue());
    }
        
    @Test public void testReverseVowels() throws IOException {
        CRC32 check = new CRC32();
        BufferedReader fr = new BufferedReader(
            new InputStreamReader(new FileInputStream("warandpeace.txt"), "UTF-8")
        );
        String line = fr.readLine();
        while(line != null) {
            String result = P2J3.reverseVowels(line);
            check.update(result.getBytes());
            line = fr.readLine();
        }
        fr.close();
        assertEquals(3844894811L, check.getValue());        
    }
    
    @Test public void testReverseAscendingSubarrays() {
        Random rng = new Random(SEED);
        CRC32 check = new CRC32();
        for(int i = 0; i < ROUNDS; i++) {
            int len = rng.nextInt(1000);
            int[] a = new int[len];
            for(int j = 0; j < len; j++) {
                a[j] = rng.nextInt(1000000);
            }
            P2J3.reverseAscendingSubarrays(a);
            for(int j = 0; j < len; j++) {
                check.update(a[j]);
            }
        }
        assertEquals(3118921076L, check.getValue());
    }
    
    @Test public void testSubsetSum() {
        Random rng = new Random(SEED);
        CRC32 check = new CRC32();
        for(int i = 0; i < ROUNDS; i++) {
            int len = rng.nextInt(20) + 3;
            int[] a = new int[len];
            a[0] = rng.nextInt(3) + 1;
            int sum = a[0];
            for(int j = 1; j < len; j++) {
                a[j] = a[j-1] + rng.nextInt(10) + 1;
                sum += a[j];
            }
            sum = sum / 2;
            Arrays.sort(a);
            for(int k = 0; k < 10; k++) {
                int goal = rng.nextInt(sum) + sum;
                boolean result = P2J3.subsetSum(a, len, goal);
                if(result) { check.update(k); }
                else { check.update(k-1); }
            }
        }
        assertEquals(2783861284L, check.getValue());
    }
}
