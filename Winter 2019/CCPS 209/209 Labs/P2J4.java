import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class P2J4 {

    public static List<Integer> runningMedianOfThree(List<Integer> items) {

        //subTrio will hold the current trio of numbers 
        //from which to take the median
        ArrayList<Integer> subTrio = new ArrayList<>();

        //resultList will hold the resulting median value list
        ArrayList<Integer> resultList = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            //This logic runs once indexing has reached
            //the 3rd element in the list
            if (i >= 2) {
                //The element at the current index is added to
                //the subTrio list, as well as the 2 elements that
                //come before it in the original list
                for (int j = i - 2; j <= i; j++) {
                    subTrio.add(items.get(j));
                }

                //The subTrio list is sorted in ascending order
                Collections.sort(subTrio);

                //The middle element of the three is the median,
                //so it is added to the resultList
                resultList.add(subTrio.get(1));

                //The subTrio list is cleared in preparation
                //for the next 3 elements to be compared
                subTrio.removeAll(subTrio);

            } else {
                //This will add the 1st two elements of the 
                //original list to the result list
                resultList.add(items.get(i));
            }
        }
        return resultList;
    }

    public static int firstMissingPositive(List<Integer> items) {
        int result = 1; //default result value is 1, for empty List input
        int index = 0; //Index of list item being examined
        int count = 1; //positive integers found, beginning value is 1
        boolean missing = false; //controls while loop

        //Sort the input list into ascending order
        Collections.sort(items);

        //Loop executes as long as no missing integer has been found
        //AND there are still list elements left to examine.
        while (!missing && index < items.size()) {
            //First check if the list item is equal to the count
            if (items.get(index) != count) {
                //If it is unequal, check if it is the 
                //first item in the list
                if (index != 0) {
                    //If it is not the next consecutive integer,
                    //then the result has been found
                    if (items.get(index) != count + 1) {
                        //Change boolean to true to end loop
                        missing = true;
                        //Set result to 1 greater than the count
                        result = count + 1;
                    } else {
                        //If the list item is the next consecutive
                        //integer, increase the count by 1
                        count++;
                    }
                } else {
                    //If the list item that doesn't equal count is the
                    //first item in the list, the default result value
                    //of 1 is the correct answer, so the boolean
                    //is changed to true in order to end the loop.
                    missing = true;
                }
            }
            index++;
        }

        return result;
    }

    public static void sortByElementFrequency(List<Integer> items) {
        //Counter map that tracks the frequency of elements
        //from the input list.
        Map<Integer, Integer> counter = new HashMap<>();

        //Creating the counter map by iterating through
        //each element of the input list
        for (Integer item : items) {
            //The getOrDefault statement will check to see
            //if the key is already in the map, returning 0
            //otherwise which will indicate that a new
            //key must be put in the map with a value
            //of 1 (ie - first time this key has been seen in
            //the input list).
            if (counter.getOrDefault(item, 0) == 0) {
                counter.put(item, 1);
            } else {
                //Otherwise, when a key is encountered,
                //its frequency-counter value in the
                //map is incremented.
                counter.put(item, counter.get(item) + 1);
            }
        }

        //Implementing Comparator strategy object which will
        //order the integers in the list, first by their frequency of
        //appearance, then by their natural order if their
        //frequency of appearance is equal.
        class freqCompare implements Comparator<Integer> {

            //Overriding the compare method of Comparator interface
            @Override
            public int compare(Integer itemA, Integer itemB) {
                //If the frequency of the two integers is equal, then
                //they will be put in their ascending order. 
                if (counter.get(itemA) == counter.get(itemB)) {
                    return itemA - itemB;
                }

                //Otherwise, the two integers will be put in 
                //descending order of their frequency.
                return (counter.get(itemB) - counter.get(itemA));
            }

        }

        //The Collections.sort() method is given the item list
        //along with the Comparator strategy object, so that
        //the desired ordering can be achieved.
        Collections.sort(items, new freqCompare());
    }

    public static List<Integer> factorFactorial(int n) {
        ArrayList<Integer> primeFactors = new ArrayList<>();
        Map<Integer, Integer> primes = new TreeMap<>();

        //First compute all primes up to n
        for (int i = 2; i <= n; i++) {
            boolean isPrime = true;
            for (int j = 2; j <= Math.sqrt(i); j++) {
                if (i % j == 0) {
                    isPrime = false;
                }
            }
            if (isPrime) {
                primes.put(i, 0);
            }
        }

        //Iterating through each key in the
        //map of prime factors
        for (Integer key : primes.keySet()) {
            //Test each number between the key and n
            //to find how many times the prime factor 
            //represented by the key can divide into it. 
            for (int i = key; i <= n; i++) {

                boolean divisible = true;
                int possibleFactor = i;

                while (divisible) {
                    if (possibleFactor % key == 0) {
                        primes.put(key, primes.get(key) + 1);
                        possibleFactor = possibleFactor / key;
                    } else {
                        divisible = false;
                    }
                }
            }
            
            //Add the key to the primeFactors list as many
            //times as its value
            for(int j = 0; j < primes.get(key); j++){
                primeFactors.add(key);
            }
        }

        return primeFactors;
    }
}