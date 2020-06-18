import java.util.*;

public class WordCount extends FileProcessor<List<Integer>> {

    //Instance variables
    int charCount, wordCount, lineCount;

    @Override
    protected void startFile() {
        charCount = 0;
        wordCount = 0;
        lineCount = 0;
    }

    @Override
    protected void processLine(String line) {
        lineCount++;
        charCount += line.length();

        for (int i = 0; i < line.length(); i++) {
            if (i == 0 && !Character.isWhitespace(line.charAt(0))) {
                wordCount++;
            } else if (Character.isWhitespace(line.charAt(i - 1)) 
                    && !Character.isWhitespace(line.charAt(i))) {
                wordCount++;
                }
            }
        }

        @Override
        protected List<Integer> endFile
        
            () {
        ArrayList<Integer> result = new ArrayList<>();
            result.add(charCount);
            result.add(wordCount);
            result.add(lineCount);

            return result;
        }
    }
