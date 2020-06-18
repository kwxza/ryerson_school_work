import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Primes {

    /*
    knownPrimes will hold the list of primes as
    they are found by the class methods.
    It is being used to cache found values
    in order to speed up the operation of 
    the Prime class's methods.
     */
    private static final ArrayList<Integer> knownPrimes = new ArrayList<>(1_000_000);

    /*
    As there is no constructor for this class, 
    the knownPrimes list will be statically
    initialized with the first 2 prime numbers.
     */
    static {
        Collections.addAll(knownPrimes, 2, 3);
    }

    /*
    When called, expandPrimes will find the next prime number
    not currently in the knownPrimes List, and add it to the List.
     */
    private static void expandPrimes() {
        /*
        possiblePrime is the number currently being tested
        for primality. It is initially set to be 2 greater than
        the last prime number in the knownPrimes List.
         */
        int possiblePrime = knownPrimes.get(knownPrimes.size() - 1) + 2;

        /*
        The boolean isPrime will control the execution
        of the while loop. It will be changed to true
        once a prime number has been found.
         */
        boolean isPrime = false;

        /*
        primeIndex is the index of the current 
        element from knownPrimes that is being
        used to test possiblePrime for primality.
        
        We start at index 1, which is the prime number 3.
        This is because testing divisibility of 2 is trivial,
        as any even number divisible by 2 is not prime,
        other than 2 itself.
         */
        int primeIndex = 1;

        /*
        primeDivisor is the current element from
        knownPrimes that is being tested against
        possiblePrime for divisibility. 
         */
        int primeDivisor = knownPrimes.get(primeIndex);

        /*
        The while loop will execute as long as a prime
        number has not yet been found.
         */
        while (!isPrime) {
            /*
            The divisibility of primeDivisor into
            possiblePrime will only be tested if the
            square of primeDivisor is less than 
            or equal to possiblePrime.
             */
            if (primeDivisor * primeDivisor <= possiblePrime) {
                /*
                If possiblePrime is not divisible by primeDivisor,
                the potential for primality still exists, so 
                primeDivisor is updated to the next element
                from the knownPrimes List.
                 */
                if (possiblePrime % primeDivisor != 0) {
                    primeDivisor = knownPrimes.get(++primeIndex);
                } else {
                    /*
                    If possiblePrime is divisible by primeDivisor, 
                    then a new number must be tested for primality.
                    Thus, possiblePrime is incremented by 2 and 
                    primeDivisor is reset to the first element
                    in the knownPrimes List.
                     */
                    possiblePrime += 2;
                    primeIndex = 0;
                    primeDivisor = knownPrimes.get(primeIndex);
                }
            } else {
                /*
                If the square of primeDivisor is greater than or equal
                to possiblePrime, then it is no longer possible for any
                further prime number from knownPrimes to be a 
                divisor for possiblePrime. Thus, possiblePrime is a 
                prime number so the boolean isPrime is changed
                to true to halt the while loop's execution.
                 */
                isPrime = true;
            }
        }
        /*
        Once the while loop has finished execution,
        possiblePrime is a prime number and so it
        is added to the knownPrimes List.
         */
        knownPrimes.add(possiblePrime);
    }

    /*
    isPrime() will take a given parameter n and
    return true if n is prime or false otherwise.
     */
    public static boolean isPrime(int n) {
        /*
        If n is less than 2 (the first prime number)
        or if n is divisible by but not equal to 2,
        then the method will return false.
        
        If n is not equal to 3, but divisible by 3,
        the method returns false.
        
        These checks are here to speed up
        calculation based on the seeded prime
        numbers of the knownPrimes List.
         */
        if (n < 2 || (n != 2 && n % 2 == 0)) {
            return false;
        } else if (n != 3 && n % 3 == 0) {
            return false;
        }

        /*
        We first assume that the given 
        paramter n is prime and then
        attempt to disprove this
        assumption.
        
        isPrime will be used to exit the 
        various while loops of the method
        if primality of n has been disproven.
         */
        boolean isPrime = true;

        /*
        If the given parameter n is greater than the last
        element in the knownPrimes List, then 
        expandPrimes() is called until the last prime 
        number in knownPrimes is greater than or 
        equal to parameter n.
         */
        if (n > knownPrimes.get(knownPrimes.size() - 1)) {
            while (isPrime
                    && knownPrimes.get(knownPrimes.size() - 1) < n) {
                expandPrimes();
                /*
                Each time expandPrimes() is called, the newly found
                prime number is tested for divisibility against n.
                
                If divisibility is found, then parameter n can be 
                disqualifed as being prime without having to find
                further prime numbers up to n. 
                 */
                if (n % knownPrimes.get(knownPrimes.size() - 1) == 0) {
                    isPrime = false;
                }
            }
            /*
            If the while loop finishes execution and the last
            prime number found by expandPrimes() is not
            equal to n, then n is not a prime number.
            Thus, comparing n to the last number in
            knownPrimes sets boolean isPrime to the
            correct value to be returned from the method.
             */
            isPrime = (n == knownPrimes.get(knownPrimes.size() - 1));
        } else {

            /*
            If n is not greater than the last element of
            the knownPrimes List, then n will be tested
            for primality by dividing it by each element
            in knownPrimes that is less than or equal 
            to n's square root. 
            
            The logic here works the same way as that
            found in the expandPrimes() method.
             */
            int primeIndex = 0;
            int primeDivisor = knownPrimes.get(primeIndex);
            while (isPrime && primeDivisor * primeDivisor <= n) {
                /*
                If a divisor is found for n, then 
                isPrime is set to false.
                 */
                if (n % primeDivisor == 0) {
                    isPrime = false;
                }
                primeDivisor = knownPrimes.get(++primeIndex);
            }
        }

        /*
        After all the method logic has executed,
        the final value of isPrime will reflect
        n's primality and can be returned from
        the method.
         */
        return isPrime;
    }


    /*
    kthPrime() takes a given parameter k
    and returns the kth prime from the
    knownPrimes List, with indexing 
    beginning from zero.
    
    If k is larger than the last index of
    knownPrimes, the method will call
    expandPrimes until the last index of
    knownPrimes is equal to k.
     */
    public static int kthPrime(int k) throws IllegalArgumentException {
        if (k < 0) {
            throw new IllegalArgumentException();
        }

        if (k > knownPrimes.size() - 1) {
            while (k > knownPrimes.size() - 1) {
                expandPrimes();
            }
        }
        return knownPrimes.get(k);
    }

    /*
    significantFactors will store the largest prime
    factor of each non-prime integer that has a
    prime factor larger than 29. This caching
    will be taken advantage of by the 
    factorize() method.
    
    29 was arbitrarily chosen as a cutoff threshold 
    to reduce the size of the significantFactors Map.
    
    The idea behind this is as follows: 
        If an integer n has a non-prime factor whose
        largest prime factor has already been found,
        then dividing n by this prime factor will reduce
        the iteration steps necessary for the factorize
        method to complete factorization.
     */
    private static final Map<Integer, Integer> significantFactors = new HashMap<>();
    
    /*
    factorize() takes a given parameter n and returns
    its prime factors as an ArrayList sorted into
    ascending order.
    */
    public static List<Integer> factorize(int n) throws IllegalArgumentException {
        /*
        The method throws an IllegalArgumentException if 
        the given parameter is less than all prime numbers.
         */
        if (n < 2) {
            throw new IllegalArgumentException();
        }

        /*
        primeFactors will hold all the factors of 
        given parameter n.
         */
        ArrayList<Integer> primeFactors = new ArrayList<>();

        /*
        If given parameter n is prime, the method
        can skip the factorization logic and the 
        primeFactors List will hold only n itself.
         */
        if (isPrime(n)) {
            primeFactors.add(n);
        } else {
            /*
            Parameter n is stored to a variable called
            quotient, which will be reduced successively
            through division by prime numbers until
            all prime factors of n have been found.
             */
            int quotient = n;
            /*
            primeIndex will be used to set the value
            of the primeDivisor from within the
            outer while loop.
             */
            int primeIndex = 0;
            /*
            Boolean quotientIsPrime will halt the outer 
            while loop immediately if the quotient has 
            been reduced to a prime number.
             */
            boolean quotientIsPrime = false;

            /*
            The while loop will stop execution in 2 cases:
                1. The quotient has been reduced to a prime
                    number after division.
                2. The quotient has been reduced to 1 after
                    division.
             */
            while (quotient > 1 && !quotientIsPrime) {
                /*
                primeDivisor will be tested for divisibility
                against the quotient. Each time the outer
                while loop executes, primeDivisor's value 
                is changed to the next successive prime.
                 */
                int primeDivisor = knownPrimes.get(primeIndex);
                /*
                Boolean divisible will halt the two inner while
                loops. Its value will be changed to false once
                the quotient is no longer divisible by the
                relevant divisor of each loop.
                 */
                boolean divisible = true;

                /*
                The first nested while loop will divide the quotient by 
                the current primeDivisor until it is no longer divisible. 
                 */
                while (divisible) {

                    /*
                    If the quotient variable's value is divisible
                    by the primeDivisor, then the primeDivisor's
                    value is added to the primeFactors List and
                    the quotient variable's value is set equal
                    to the result of the division.
                     */
                    if (quotient % primeDivisor == 0) {
                        primeFactors.add(primeDivisor);
                        quotient /= primeDivisor;

                        /*
                        Once the quotient value has been changed,
                        its new value is checked to see if it matches
                        an integer key in the significantFactors Map.
                        
                        If it does, that integer key's corresponding
                        significant prime factor is also a factor of n.
                        Thus, this factor can be recorded and used
                        to reduce the quotient immediately, as opposed
                        to having to be reached by iteration through
                        possible prime factors from knownPrimes.
                         */
                        if (significantFactors.containsKey(quotient)) {
                            /*
                            significantDivisor will hold the prime factor
                            found from the significantFactors Map.
                            */
                            int significantDivisor = significantFactors.get(quotient);
                            
                            /*
                            The second nested while loop will divide the quotient
                            by the significantDivisor until it is no longer divisible.
                            */
                            while (divisible) {
                                /*
                                Conditional logic is used here to check
                                divisibility of the significantDivisor.
                                This is because a significantDivisor may
                                be able to divide the quotient more than once.
                                
                                For each time the significantDivisor is divisible
                                into the quotient, its value is added to the 
                                primeFactors List. The quotient is then set 
                                equal to the result of the division.
                                */
                                if (quotient % significantDivisor == 0) {
                                    primeFactors.add(significantDivisor);
                                    quotient /= significantDivisor;
                                } else {
                                    /*
                                    Once the quotient is no longer divisible by
                                    the significantDivisor, boolean divisible is
                                    set to false so that the second nested 
                                    while loop can cease execution.
                                    */
                                    divisible = false;
                                }
                            }
                            /*
                            After the second nested while loop has halted,
                            boolean divisible must be reset to true so that
                            the quotient can continue to be divided by the 
                            primeDivisor.
                            */
                            divisible = true;
                        }
                    } else {
                        /*
                        Once the quotient is no longer divisible by the primeDivisor,
                        boolean divisible is set to false so that the first nested
                        while loop can cease execution and a new primeDivisor
                        can be selected from the knownPrimes List.
                        */
                        divisible = false;
                    }
                }
                
                /*
                After a primeDivisor has been divided as many times as
                possible into the quotient, the resulting quotient value is 
                checked for primality. If it is found to be prime, its value
                will be added to the primeFactors List and execution
                of the outer while loop will be halted.
                */
                quotientIsPrime = isPrime(quotient);
                if (quotientIsPrime) {
                    primeFactors.add(quotient);
                }
                /*
                The primeIndex is incremented at the end of the 
                outer while loop, so that a new primeDivisor 
                can be selected from the knownPrimes List.
                */
                primeIndex++;
            }
            
            /*
            Once the outer while loop has ceased execution, all
            prime factors of n have been found. 
            
            However, the primeFactors List is unsorted due to 
            smaller factors being inserted into the list after larger 
            factors drawn from the significantFactors Map. 
            
            Thus, the primeFactors list must be sorted before 
            being returned from the method.
            */
            Collections.sort(primeFactors);
            
            /*
            Now that the primeFactors list is sorted, the largest
            prime factor of n can be determined and stored to the
            significantFactors map. Doing this will speed up 
            computation of prime factors for larger values
            of n during subsequent calls to the factorize() method.
            
            To reduce the size of the significantFactors map,
            only prime factors larger than 29 will be stored.
            */
            if (Collections.max(primeFactors) > 29) {
                significantFactors.put(n, Collections.max(primeFactors));
            }
        }
        
        return primeFactors;
    }
}