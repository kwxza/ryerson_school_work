import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.*;

public class StreamExercises {

    public int countLines(Path path, int thres) throws IOException {
        return Files.lines(path)
                .filter(line -> line.length() >= thres)
                .map(line -> 1)
                .reduce(0, Integer::sum);
    }

    public List<String> collectWords(Path path) throws IOException {
        return Files.lines(path)
                .map(String::toLowerCase)
                .flatMap(line -> Stream.of(line.split("[^a-z]+")))
                .filter(word -> word.length() > 0)
                .sorted()
                .distinct()
                .collect(Collectors.toList());
    }
}