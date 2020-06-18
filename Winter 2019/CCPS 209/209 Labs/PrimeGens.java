import java.util.Iterator;

public class PrimeGens {

    public static class TwinPrimes implements Iterator<Integer> {

        private int primeIndex = 0;

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Integer next() {
            while (true) {
                int possibleTwin = Primes.kthPrime(primeIndex++);

                if (Primes.isPrime(possibleTwin + 2)) {
                    return possibleTwin;
                }
            }
        }

    }

    public static class SafePrimes implements Iterator<Integer> {

        int primeIndex = 0;

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Integer next() {
            while (true) {
                int possiblySafe = Primes.kthPrime(primeIndex++);

                if (Primes.isPrime((possiblySafe - 1) / 2)) {
                    return possiblySafe;
                }
            }
        }

    }

    public static class StrongPrimes implements Iterator<Integer> {

        int primeIndex = 1;

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Integer next() {
            while (true) {
                int possiblyStrong = Primes.kthPrime(primeIndex++);
                
                if (possiblyStrong > (Primes.kthPrime(primeIndex) 
                        + Primes.kthPrime(primeIndex - 2)) / 2) {
                    return possiblyStrong;
                }   
            }
        }

    }
}