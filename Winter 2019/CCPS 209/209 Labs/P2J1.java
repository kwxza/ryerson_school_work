public class P2J1
{
    public static int fallingPower(int n, int k){
        int result = 1;
        if(k == 0){
            return result;
        } else {
            int step = n;
            for(int i = k; i > 0; i--){
              result *= step;
              step --;
            }
        }
        return result;
    }
    
    public static int[] everyOther(int[] arr){
        int arrLength = (arr.length % 2 == 0) ? (arr.length/2) : (arr.length-1)/2 + 1;
        int[] output = new int[arrLength];
        
        for(int i = 0; i < arr.length; i++){
            if(i%2==0){
                output[i/2] = arr[i];
            }
        }
        return output;
    }
    
    public static int[][] createZigZag(int rows, int cols, int start){
        int currentNumber = start;
        int[][] zigzag = new int[rows][cols];
        
        // this line counts through the rows
        for(int rowNum = 0; rowNum < rows; rowNum++){
            int colNum;
            // The following logic checks to see if the row
            // index is odd or even. If it is even, it will 
            // store the current number starting from the
            // beginning of the row. If it is odd, it will
            // store starting from the end of the row.
            if(rowNum % 2 == 0){
                for(colNum = 0; colNum < cols; colNum++){
                    zigzag[rowNum][colNum] = currentNumber;
                    currentNumber++;
                }
            } else{
                for(colNum = cols - 1; colNum >= 0; colNum--){
                    zigzag[rowNum][colNum] = currentNumber;
                    currentNumber++;
                }
            }
        }
        
        return zigzag;
    }
    
    public static int countInversions(int[] arr){
        // We assume the number of inversions is 0
        int numOfInversions = 0;
        
        // The 1st for loop selects the current number in
        // the array that is being compared to see if it is 
        // less than any subsequent numbers in the array
        for(int i = 0; i < arr.length; i++){
            // The 2nd for loop selects numbers in the
            // array that come after the current
            // comparison number
            for(int j = i+1; j < arr.length; j++){
                // The if statement will update the 
                // inversion count if the comparison
                // number is greater than any number
                // that follows it in the array
                if(arr[i] > arr[j]){
                    numOfInversions++;
                }
            }
        }
        
        return numOfInversions;
    }
}
