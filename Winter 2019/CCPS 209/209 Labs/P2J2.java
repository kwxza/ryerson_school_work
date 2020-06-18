import java.util.HashSet;
public class P2J2
{
    public static String removeDuplicates(String text){
        String noDuplicates;
        
        //Check if string is empty
        if(text.length() == 0){
            noDuplicates = "";
        } else {
            StringBuilder newString = new StringBuilder();
            //Initialize StringBuilder with first
            //character of string
            newString.append(text.charAt(0));
            
            //Loop to identify adjacent duplicates
            for(int i = 0; i < text.length() - 1; i++){
                if(text.charAt(i) != text.charAt(i+1)){
                    newString.append(text.charAt(i+1));
                }
            }
            
            noDuplicates = newString.toString();
        }
        
        return noDuplicates;
    }
    
    public static String uniqueCharacters(String text){
        String newString;
        
        if(text.length() == 0){
            newString = "";
        } else {
            HashSet<Character> uniqueChars = new HashSet();
            StringBuilder uniqueString = new StringBuilder();
            
            for(int i = 0; i < text.length(); i++){
                if(uniqueChars.add(text.charAt(i))){
                    uniqueString.append(text.charAt(i));
                }
            }
            
            newString = uniqueString.toString();
        }
        
        return newString;
    }
    
    public static int countSafeSquaresRooks(int n, boolean[][] rooks){
        int riskyRows = 0;
        boolean rookInRow;
        //HashSet will store indices of columns
        //where rooks have already been found
        HashSet<Integer> unsafeColumns = new HashSet();
        
        //This for loop iterates through the rows
        for(int i = 0; i < n; i++){
            //Sets the rookInRow boolean to false
            //before a new row is checked for rooks
            rookInRow = false;
            
            //This for loop iterates through 
            //the squares in a row
            for(int j = 0; j < n; j++){
                //The if statement conditional executes
                //if rook[i][j] returns true, meaning
                //that there is a rook on that square
                if(rooks[i][j]){
                    //The index of the column will be added
                    //to the unsafeColumn set if it is
                    //not already an element
                    unsafeColumns.add(j);
                    //The rookInRow boolean will be set to
                    //true because a rook has been found
                    rookInRow = true;
                }
            }
            //This if statement will execute if the
            //rookInRow boolean has been set to true
            //after checking every square in the row
            if(rookInRow) riskyRows++;
        }
        
        //The following calculation will determine how
        //many safe squares are left on the chessboard
        int safeSquares = (n-riskyRows)*(n-unsafeColumns.size());
        
        return safeSquares;
    }
    
    public static int recaman(int n){
        int an = 0;
        //The boolean array starts out being false.
        //Each time a new sequence number is found,
        //tracker[an] will be changed to true to
        //keep track of what is already in the sequence
        boolean[] tracker = new boolean[10*n];
        
        for(int i = 0; i <= n; i++){
            //The if/else logic will subtract the current
            //step number from the last sequence number 
            //found if and only if the resulting number
            //is positive and has not been marked as
            //true in the tracker array
            if(an - i > 0 && !tracker[an - i]){
                an = an - i;
            } else {
                an = an + i;  
            }
            //The cell matching the index of the current
            //sequence number found will be changed to
            //true in the tracker array
            tracker[an] = true;
        }
        
        return an;
    }
}
