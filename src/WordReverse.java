import java.util.Arrays;
import java.util.stream.Collectors;

public class WordReverse {

    public static String spinWords(String input) {
        return Arrays.stream(input.split(" "))
            .map(word -> word.length() >= 5 ? new StringBuilder(word).reverse().toString() : word)
            .collect(Collectors.joining(" "));
    }
}