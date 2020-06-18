import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class P2J5 {

    /*
    FIBS list will keep track of all Fibonacci numbers
    that have already been found.
    
    It is initialized with the first two numbers in the sequence.
     */
    private static final List<BigInteger> FIBS = new ArrayList<>();

    static {
        FIBS.add(BigInteger.ONE);
        FIBS.add(BigInteger.ONE);
    }

    /*
    expandFibs() is a helper method that will add the 
    next Fibonacci number to the FIBS list.
     */
    private static void expandFibs() {
        BigInteger nextFib = FIBS.get(FIBS.size() - 1).add(FIBS.get(FIBS.size() - 2));
        FIBS.add(nextFib);
    }

    /*
    This method finds the smallest amount of Fibonacci
    numbers that sum to a given BigInteger n.
    
    @param n The given BigInteger for which a sum will be found
    @return A list of the Fibonacci numbers that sum, in descending order.
     */
    public static List<BigInteger> fibonacciSum(BigInteger n) {
        List<BigInteger> resultList = new ArrayList<>();
        /*
        First, the FIBS list is expanded if its last number in
        sequence is smaller than n.
         */
        while (FIBS.get(FIBS.size() - 1).compareTo(n) < 0) {
            expandFibs();
        }

        /*
        The while loop finds the largest number in FIBS that
        is less than or equal to n.
        
        It will add that number to the resultList,
        subtract that number from n and set n to the result.
        
        It will continue as long as n has not been reduced to zero.
         */
        while (BigInteger.ZERO.compareTo(n) < 0) {
            int index = Collections.binarySearch(FIBS, n);
            if (index < 0) {
                index = -1 * (index + 2);
            }

            resultList.add(FIBS.get(index));
            n = n.subtract(FIBS.get(index));
        }

        return resultList;
    }
    
    /*
    This method takes an integer n and finds the smallest
    number composed of only 7s and/or 0s that n
    can fully divide.
    @param n The number for which a 7/0 dividend will be found.
                     It is assumed that n is given in base 10.
    @return A BigInteger number divisible by n will be returned.
    */
    public static BigInteger sevenZero(int n) {
        
        /*
        onlySevens will determine if numbers containing
        zeros are checked for.
        */
        boolean onlySevens = (n % 2 != 0 && n % 5 != 0);
        /*
        divisible will stop execution of the checking logic
        if a divisible number is found.
        */
        boolean divisible = false;
        
        /*
        degree will be the degree of the resulting BigInteger.
        It is initialized to one and then adjusted to be
        at least the degree of @param n.
        */
        BigInteger degree = BigInteger.valueOf(1L);
        
        /*
        Since whatever result is found will have leading 7s,
        BigInteger result is initialized to 7. 
        
        Each time the degree is increased, result is updated
        to have another 7 in its lowest decimal placeholder.
        
        Thus the result value will only ever hold all 7s UNLESS
        a resultWithZeros value (below) is found to be divisible by n.
        At that point, result will be updated to match resultWithZeros
        before being returned by the method.
        */
        BigInteger result = degree
                .multiply(BigInteger.valueOf(7L));
        
        /*
        This initial while loop matches the degree of
        result to the degree of n.
        */
        while (n / (degree.longValue() * 10L) >= 1) {
            degree = degree
                    .multiply(BigInteger.valueOf(10L));

            result = result.add(degree
                    .multiply(BigInteger.valueOf(7L)));
        }

        while (!divisible) {
            /*
            The current result value is first checked to see if it 
            is divisible by n.
            */
            divisible = BigInteger.ZERO
                    .equals(result.remainder(BigInteger.valueOf(n)));
            
            /*
            Calculations if n is divisible by 2 or 5 (ie result has zeros).
            */
            if (!onlySevens && !divisible) {
                /*
                A copy of result is made, to be then modified
                by subtraction to create 0s in its decimal
                placeholders (because result contains only 7s).
                */
                BigInteger resultWithZeros = result;
                /*
                degreeTracker will keep track of which decimal 
                placeholder is to be reduced to zero with subtraction.
                */
                BigInteger degreeTracker = BigInteger.valueOf(1L);
                
                /*
                This while loop runs as long as a dividend has not been
                found and the degree of the decimal place to create
                a zero is less than the overall degree of the result.
                
                This is because there must be a minimum of 1 leading
                7 in whatever the result will end up being.
                */
                while (!divisible && degreeTracker.compareTo(degree) < 0) {
                    /*
                    First the current degreeTracker value is multiplied by 7
                    and subtracted from resultWithZeros to create a zero
                    in its decimal placeholder.
                    */
                    resultWithZeros = resultWithZeros
                            .subtract(degreeTracker.multiply(BigInteger.valueOf(7L)));
                    
                    /*
                    Divisibility of resultWithZeros by n is checked and
                    if divisible, boolean divisible is set to true to 
                    stop the while loops.
                    */
                    divisible = BigInteger.ZERO
                            .equals(resultWithZeros
                                    .remainder(BigInteger.valueOf(n)));
                    
                    /*
                    If divisibility has been found, result is updated to 
                    match resultWithZeros.
                    */
                    if (divisible) {
                        result = resultWithZeros;
                    }
                    
                    /*
                    The degreeTracker is multiplied by 10 each time the
                    loop executes, so that it can be used to reduce the
                    next decimal place to zero.
                    */
                    degreeTracker = degreeTracker.multiply(BigInteger.valueOf(10L));
                }
            }
            
            /*
            If the result value is not divisible by n, then 
            the degree of result is increased by 1, with
            a 7 occupying the new decimal place added.
            */
            if (!divisible) {
                degree = degree
                        .multiply(BigInteger.valueOf(10L));

                result = result.add(degree
                        .multiply(BigInteger.valueOf(7L)));
            }
        }
        return result;
    }
}
