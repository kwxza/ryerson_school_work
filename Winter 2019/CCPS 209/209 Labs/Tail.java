import java.util.*;

public class Tail extends FileProcessor<List<String>> {

    int tailGoal;
    LinkedList<String> tailLines;

    public Tail(int n) {
        tailGoal = n;
    }

    @Override
    protected void startFile() {
        tailLines = new LinkedList<String>();
    }

    @Override
    protected void processLine(String line) {

        if (tailLines.size() == tailGoal) {
            tailLines.removeFirst();
        }

        tailLines.add(line);
    }

    @Override
    protected List<String> endFile() {
        return tailLines;
    }

}

