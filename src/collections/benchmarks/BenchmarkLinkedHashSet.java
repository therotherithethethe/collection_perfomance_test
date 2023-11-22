package collections.benchmarks;

import collections.DataType;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BenchmarkLinkedHashSet {
    private static LinkedHashSet initializeSet(int numberOfElements, DataType dataType) {
        LinkedHashSet set = new LinkedHashSet<>();
        if (dataType == DataType.INTEGER) {
            set.addAll(IntStream.range(0, numberOfElements).parallel()
                .boxed()
                .collect(Collectors.toSet()));
        } else if (dataType == DataType.STRING) {
            Random random = new Random();
            set.addAll(IntStream.range(0, numberOfElements).parallel()
                .mapToObj(i -> random.ints('a', 'z' + 1)
                    .limit(40)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString())
                .collect(Collectors.toSet()));
        } else if (dataType == DataType.FLOAT) {
            Random random = new Random();
            set.addAll(IntStream.range(0, numberOfElements).parallel()
                .mapToObj(i -> random.nextFloat() * numberOfElements)
                .collect(Collectors.toSet()));
        }

        return set;
    }

    public static Long createElementsTest(int numberOfElements, DataType dataType) {
        long startTimeForAddingElements = System.currentTimeMillis();
        initializeSet(numberOfElements, dataType);
        long endTimeForAddingElements = System.currentTimeMillis();

        return endTimeForAddingElements - startTimeForAddingElements;
    }

    public static Long deleteElementsTest(int numberOfElements, DataType dataType) {
        LinkedHashSet set = initializeSet(numberOfElements, dataType);

        long startTimeForDeleteElements = System.currentTimeMillis();
        set.clear();
        long endTimeForDeleteElements = System.currentTimeMillis();

        return endTimeForDeleteElements - startTimeForDeleteElements;
    }
    public static Long filterTest(int numberOfElements, DataType dataType) {
        LinkedHashSet set = initializeSet(numberOfElements, dataType);

        long startTimeForFilterElements, endTimeForFilterElements;

        switch (dataType) {
            case INTEGER:
                startTimeForFilterElements = System.currentTimeMillis();
                set = (LinkedHashSet)set.parallelStream()
                    .filter(number -> (int)number % 2 == 0)
                    .collect(Collectors.toSet());
                endTimeForFilterElements = System.currentTimeMillis();
                return endTimeForFilterElements - startTimeForFilterElements;

            case FLOAT:
                startTimeForFilterElements = System.currentTimeMillis();
                set = (LinkedHashSet)set.parallelStream()
                    .filter(number -> (float)number > 30.0f)
                    .collect(Collectors.toSet());
                endTimeForFilterElements = System.currentTimeMillis();
                return endTimeForFilterElements - startTimeForFilterElements;

            case STRING:
                startTimeForFilterElements = System.currentTimeMillis();
                set = (LinkedHashSet)set.parallelStream()
                    .filter(word -> ((String)word).startsWith("a"))
                    .collect(Collectors.toSet());
                endTimeForFilterElements = System.currentTimeMillis();
                return endTimeForFilterElements - startTimeForFilterElements;

            default:
                return null;
        }
    }


    public static <T> Long findTest(int numberOfElements, DataType dataType, T target) {
        LinkedHashSet<T> set = initializeSet(numberOfElements, dataType);

        long startTimeForFind, endTimeForFind;

        startTimeForFind = System.currentTimeMillis();
        boolean found = set.contains(target);
        endTimeForFind = System.currentTimeMillis();

        return found ? (endTimeForFind - startTimeForFind) : -1L;
    }

    public static <T> Long concatTest(int numberOfElements, DataType dataType) {
        LinkedHashSet<T> set = initializeSet(numberOfElements, dataType);

        long startTimeForConcat, endTimeForConcat;

        startTimeForConcat = System.currentTimeMillis();
        String concatenated = set.parallelStream()
            .map(Object::toString)
            .collect(Collectors.joining(", "));
        endTimeForConcat = System.currentTimeMillis();

        return endTimeForConcat - startTimeForConcat;
    }
    public static Long reduceTest(int numberOfElements, DataType dataType) {
        LinkedHashSet<?> set = initializeSet(numberOfElements, dataType);

        long startTimeForReduce, endTimeForReduce;

        switch (dataType) {
            case INTEGER:
                startTimeForReduce = System.currentTimeMillis();
                set.parallelStream()
                    .map(Integer.class::cast)
                    .reduce(0, Integer::sum);
                endTimeForReduce = System.currentTimeMillis();
                return endTimeForReduce - startTimeForReduce;

            case FLOAT:
                startTimeForReduce = System.currentTimeMillis();
                set.parallelStream()
                    .map(Float.class::cast)
                    .reduce(0f, Float::sum);
                endTimeForReduce = System.currentTimeMillis();
                return endTimeForReduce - startTimeForReduce;

            case STRING:
                startTimeForReduce = System.currentTimeMillis();
                set.parallelStream()
                    .map(Object::toString)
                    .reduce("", String::concat);
                endTimeForReduce = System.currentTimeMillis();
                return endTimeForReduce - startTimeForReduce;

            default:
                return null;
        }
    }
}
