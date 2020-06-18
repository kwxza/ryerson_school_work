public class P2J3
{
    public static void reverseAscendingSubarrays(int[] items) {

        //If the input array is null or zero in length, the program
        //will exit without attempting to reverse 
        if (items == null || items.length == 0) {
            return;
        }

        //subStart variable will keep track of the beginning index of each
        //array subsequence to be reversed. 
        //At the beginning of analyzing an array, it is initialized to zero.
        int subStart = 0;

        //The for-loop starts the currIndex at 1 for a new array being
        //analyzed and continues to execute the loop until the index
        //has traversed the array's entire length.
        for (int currIndex = 1; currIndex < items.length; currIndex++) {

            //If the element at currIndex is smaller than the element
            //before it, this indicates the end of a subsequence that will
            //then need to be reversed. 
            //If the element at currIndex is the last element in the
            // input array, this also indicates the end of a subsequence
            //that must be reversed.
            if (items[currIndex] < items[currIndex - 1] || currIndex == items.length - 1) {
                
                //The following logic for the subsequence reversal is based
                //upon the currIndex value representing the element
                //after the end of the subsequence.
                //Thus, if the currIndex is the LAST element in the array
                //and also the last element in a subsequence to be reversed,
                //its value will be incremented by 1 so that the
                //reversal logic will still work for the subsequence
                //at the end of the input array.
                if(currIndex == items.length - 1 && items[currIndex] > items[currIndex-1]) currIndex++;
                
                //The segmentLength variable equals the number of array
                //elements between the beginning of the subsequence and the
                //current index, not including the element at the 
                //current index (unless the current index is the last
                //element in the array).
                
                //The segment length will be used to find the midpoint between
                //subStart and currIndex. The midpoint will be used to 
                //determine how many times to run the reversal loop.
                int segmentLength = currIndex - subStart;
                
                //The following for-loop will reverse the subsequence starting 
                //from the element at index subStart and ending at the element
                //that comes before index currIndex.
                for (int counter = 0; subStart + counter < subStart + segmentLength / 2; counter++) {
                    
                    //The subsequence reversal loop will start by switching
                    //the two elements at either end of the subsequence.
                    //It will then work its way inwards, stopping once
                    //it reaches the midpoint of the subsequence.
                    
                    //This is controlled by the counter variable which is
                    //initialized and incremented by the for-loop.
                    //Each time the loop executes, the counter variable
                    //is incremented, moving the left-side index of the 
                    //subsequence to the right (towards the midpoint) 
                    // and the right-side index of the subsequence 
                    // to the left (towards the midpoint).
                    
                    //The index of the midpoint is found by dividing the 
                    //segment length in half. The value found must be
                    //added to the index of the beginning of the 
                    //subsequence (subStart) to find the proper midpoint
                    //index of each subSequence.
                    //This is defined as the stopping condition of the for-loop
                    //(subStart + segmentLength / 2).
                    
                    //The temp variable will hold the value of the element 
                    //on the right side of the midpoint (the element
                    //closer to the end of the subsequence).
                    int temp = items[currIndex - 1 - counter];
                    
                    //The right-side element is then updated to hold
                    //the value of the left-side element (the element
                    //closer to the beginning of the subsequence).
                    items[currIndex - 1 - counter] = items[subStart + counter];
                    
                    //Once the left-side element has been updated, the
                    //left-side element is updated to hold the value
                    //of the temp variable, which is holding the original
                    //value of the right-side element.
                    items[subStart + counter] = temp;
                }
                
                //After the entire subsequence has been reversed,
                //the subStart variable is set to equal currIndex, which is
                //the index of the element after the subsequence that has
                //just been reversed. 
                
                //This reset of subStart occurs just before the method 
                //continues on to examine the following elements in the 
                //input array.
                subStart = currIndex;
            }
        }
    }
    
    public static String pancakeScramble(String text){
        //scrambledStr is a reference to the varying string
        //objects that are being created through reversal and
        //concatenation.
        //I specify reference because technically, each time the for-loop
        //exectues, it creates a new string for revsub, concatenates
        //that with a substring of the existing scrambledStr and then
        //resets the scrambledStr reference to the new String object
        //that has been created.
        String scrambledStr = text;
        
        for(int i = 2; i <= text.length(); i++){
            //revSub is the portion of the string that is currently 
            //being reversed
            String revSub = new StringBuilder(scrambledStr.substring(0, i)).reverse().toString();
            scrambledStr = revSub + scrambledStr.substring(i);
        }
        
        return scrambledStr;
    }
    
    public static String reverseVowels(String text) {

        //The input string is converted into a char array for ease
        //of iterability and access to Character methods.
        char[] strArr = text.toCharArray();

        //The length value will be used to halt the outer while-loop.
        int strLen = strArr.length;

        //leftCount and rightCount are pointers to keep track of
        //how far vowel-search has progressed from
        //either end of the character array.
        int leftCount = 0;
        int rightCount = 1;

        //The boolean value will control execution of the 
        //inner while-loop.
        boolean leftVowel = false;

        //The nested while-loops will reverse the vowels of the char
        //array in place, so long as the sum of the pointer values
        //is less than the length of the original input string.
        //leftCount is incremented with each cycle of the outer loop, while
        //rightCount is incremented with each cycle of the inner loop.
        while (leftCount + rightCount < strLen) {
            //The outer loop iterates through the array from left to right,
            //checking for the LHS vowel.
            //If a vowel is found, the boolean is set to true
            //which enables execution of the inner loop.
            if (Character.toString(strArr[leftCount]).matches("[aeiouAEIOU]")) {
                leftVowel = true;
            }
            while (leftVowel) {
                //The inner loop iterates through the array from right to left, 
                //checking for the RHS vowels.
                if (Character.toString(strArr[strLen - rightCount]).matches("[aeiouAEIOU]")) {
                    //When a RHS vowel is found, its value is placed in a holder variable.
                    char holder = strArr[strLen - rightCount];
                    
                    //The holder vowel's case is then changed to match whether 
                    //the LHS vowel is upper/lowercase.
                    holder = Character.isUpperCase(strArr[leftCount]) ? 
                            Character.toUpperCase(holder) : Character.toLowerCase(holder);
                    
                    //Once the holder's case has been updated, the LHS vowel's case can
                    //be changed to match whether the RHS vowel is upper/lowercase.
                    strArr[leftCount] = Character.isUpperCase(strArr[strLen - rightCount]) ? 
                            Character.toUpperCase(strArr[leftCount]) : Character.toLowerCase(strArr[leftCount]);
                    
                    //The case-updated LHS vowel is then placed in the RHS vowel's position.
                    //The position of the LHS vowel is then replaced by the case-updated holder value.
                    strArr[strLen - rightCount] = strArr[leftCount];
                    strArr[leftCount] = holder;
                    
                    //The boolean is then set to false to halt execution of the
                    //inner loop, so that the outer loop can move forward
                    //and find another vowel.
                    leftVowel = false;
                }
                rightCount++;
            }
            leftCount++;
        }
        
        //A new string is returned, composed of the rearranged
        //elements of the char array.
        return new String(strArr);
    }
    
public static boolean subsetSum(int[] items, int n, int goal) {
        /*
        This method uses a recursive solution to determine whether
        an array of numbers spanning from index 0 - range n
        contains a subset  that adds up to the specified goal.
        
        If we consider the number at the end of the range (the 
        nth term - ie items[n-1]), then we can simplify the problem
        to a simple question:
            - Does the goal sum of numbers include the nth term?
        
        If no:
            We can discard the nth term and keep looking for subsets.
            -> ie - the goal remains the same, and we can decrease
                 the range by 1.
        
        If yes:
            We can subtract the nth term from the goal, update our goal 
            to the result and then reduce the range by 1, so that we can 
            find another term to subtract from the newly reduced goal.
        
        If the range does contain a subset that sums up to the goal,
        then eventually this subtraction of terms will result in the 
        goal being equal to zero.
        
        If we run out of numbers to check (n = 0) without 
        successfully reducing the goal to exactly 0, we then 
        know that the range contains no subset that can
        sum up to the goal.
        
        Thus, to find the solution for any array range and goal,
        we must reduce either the goal or the range to zero,
        thus giving us our base cases for recursion.
        
        To do this, the method will first check to see if the
        given goal and range parameters are equal to 0.
        
        If either one is zero, the method will return the 
        corresponding base case answer.
        
        If neither parameter is 0, the method will return a 
        branched call to itself.
        
        One of the calls will have the range reduced by 1 
        with the goal being the same.
        The other will have the range reduced by 1 with the
        goal being reduced by the nth term.
         */

        //Base cases
        if (goal == 0) {
            return true;
        }
        //If the range is equal to zero, we must check
        //to see if the goal has also been reduced to 
        //zero, before returning true or false.
        if (n == 0) {
            return goal == 0 ? true : false;
        }

        //We will automatically discard the nth term if
        //it is larger than the desired goal.
        if (items[n - 1] > goal) {
            return subsetSum(items, n - 1, goal);
        }

        return subsetSum(items, n - 1, goal)
                || subsetSum(items, n - 1, goal - items[n - 1]);
    }
}
