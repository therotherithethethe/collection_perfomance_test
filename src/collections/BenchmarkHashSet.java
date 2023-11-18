package collections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BenchmarkHashSet {
    private static HashSet initializeHashSet(int numberOfElements, DataType dataType) {
        HashSet set = new HashSet<>();
        if (dataType == DataType.INTEGER) {
            set.addAll(IntStream.range(0, numberOfElements)
                .boxed()
                .collect(Collectors.toSet()));
        } else if (dataType == DataType.STRING) {
            Random random = new Random();
            set.addAll(IntStream.range(0, numberOfElements)
                .mapToObj(i -> random.ints('a', 'z' + 1)
                    .limit(4)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString())
                .collect(Collectors.toSet()));
        } else if (dataType == DataType.FLOAT) {
            Random random = new Random();
            set.addAll(IntStream.range(0, numberOfElements)
                .mapToObj(i -> random.nextFloat() * numberOfElements)
                .collect(Collectors.toSet()));
        }

        return set;
    }
    public static Long createElementsTest(int numberOfElements, DataType dataType) {
        HashSet set;

        long startTimeForAddingElements = System.currentTimeMillis();
        list = InitializeList(numberOfElements, dataType);
        long endTimeForAddingElements = System.currentTimeMillis();

        return endTimeForAddingElements - startTimeForAddingElements;
    }
}
