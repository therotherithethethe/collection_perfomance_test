package collections;

import java.util.HashSet;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.Random;

public class BenchmarkHashSet {
    private static HashSet initializeSet(int numberOfElements, DataType dataType) {
        HashSet set = new HashSet<>();
        if (dataType == DataType.INTEGER) {
            set.addAll((HashSet) IntStream.range(0, numberOfElements).parallel()
                .boxed()
                .collect(Collectors.toSet()));
        } else if (dataType == DataType.STRING) {
            Random random = new Random();
            set.addAll((HashSet) IntStream.range(0, numberOfElements).parallel()
                .mapToObj(i -> random.ints('a', 'z' + 1)
                    .limit(40)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString())
                .collect(Collectors.toSet()));
        } else if (dataType == DataType.FLOAT) {
            Random random = new Random();
            set.addAll((HashSet) IntStream.range(0, numberOfElements).parallel()
                .mapToObj(i -> random.nextFloat() * numberOfElements)
                .collect(Collectors.toSet()));
        }

        return set;
    }

    public static Long createElementsTest(int numberOfElements, DataType dataType) {
        HashSet set;

        long startTimeForAddingElements = System.currentTimeMillis();
        set = initializeSet(numberOfElements, dataType);
        long endTimeForAddingElements = System.currentTimeMillis();

        return endTimeForAddingElements - startTimeForAddingElements;
    }

    // Read test is not applicable for HashSet as it does not support get by index

    public static Long updateElementsTest(int numberOfElements, DataType dataType) {
        HashSet set = initializeSet(numberOfElements, dataType);
        // Updating elements in a HashSet is not straightforward as in ArrayList
        // The usual approach would be to remove the old element and add the updated one

        long startTimeForUpdateElements = System.currentTimeMillis();
        // Example update logic for INTEGER type
        if (dataType == DataType.INTEGER) {
            set.forEach(item -> {
                set.remove(item);
                set.add((Integer) item + 1);
            });
        }
        long endTimeForUpdateElements = System.currentTimeMillis();
        return endTimeForUpdateElements - startTimeForUpdateElements;
    }

    public static Long deleteElementsTest(int numberOfElements, DataType dataType) {
        HashSet set = initializeSet(numberOfElements, dataType);

        long startTimeForDeleteElements = System.currentTimeMillis();
        set.clear(); // Clearing the whole set
        long endTimeForDeleteElements = System.currentTimeMillis();
        return endTimeForDeleteElements - startTimeForDeleteElements;
    }

    public static Long filterTest(int numberOfElements, DataType dataType) {
        HashSet set = initializeSet(numberOfElements, dataType);

        long startTimeForFilterElements, endTimeForFilterElements;

        switch (dataType) {
            case INTEGER:
                startTimeForFilterElements = System.currentTimeMillis();
                set = (HashSet) set.parallelStream()
                    .filter(number -> (int) number % 2 == 0)
                    .collect(Collectors.toSet());
                endTimeForFilterElements = System.currentTimeMillis();
                return endTimeForFilterElements - startTimeForFilterElements;

            case FLOAT:
                startTimeForFilterElements = System.currentTimeMillis();
                set = (HashSet) set.parallelStream()
                    .filter(number -> (float) number > 30.0f)
                    .collect(Collectors.toSet());
                endTimeForFilterElements = System.currentTimeMillis();
                return endTimeForFilterElements - startTimeForFilterElements;

            case STRING:
                startTimeForFilterElements = System.currentTimeMillis();
                set = (HashSet) set.parallelStream()
                    .filter(word -> ((String) word).startsWith("a"))
                    .collect(Collectors.toSet());
                endTimeForFilterElements = System.currentTimeMillis();
                return endTimeForFilterElements - startTimeForFilterElements;

            default:
                return null;
        }
    }
    public static <T> Long findTest(int numberOfElements, DataType dataType, T target) {
        HashSet<T> set = initializeSet(numberOfElements, dataType);

        long startTimeForFind, endTimeForFind;

        Predicate<T> matchPredicate;
        switch (dataType) {
            case INTEGER, STRING, FLOAT:
                matchPredicate = element -> Objects.equals(element, target);
                break;
            default:
                return null;
        }

        startTimeForFind = System.currentTimeMillis();
        boolean found = set.parallelStream().anyMatch(matchPredicate);
        endTimeForFind = System.currentTimeMillis();

        return found ? (endTimeForFind - startTimeForFind) : -1L;
    }
    public static Long reduceTest(int numberOfElements, DataType dataType) {
        HashSet<?> set = initializeSet(numberOfElements, dataType);

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