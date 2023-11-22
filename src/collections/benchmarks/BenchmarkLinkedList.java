package collections.benchmarks;

import collections.DataType;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BenchmarkLinkedList {
    private static LinkedList<Object> initializeList(int numberOfElements, DataType dataType) {
        LinkedList<Object> list = new LinkedList<>();
        if (dataType == DataType.INTEGER) {
            list.addAll(IntStream.range(0, numberOfElements)
                .boxed()
                .collect(Collectors.toList()));
        } else if (dataType == DataType.STRING) {
            Random random = new Random();
            IntStream.range(0, numberOfElements)
                .mapToObj(i -> random.ints('a', 'z' + 1)
                    .limit(40)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString())
                .forEach(list::add);
        } else if (dataType == DataType.FLOAT) {
            Random random = new Random();
            IntStream.range(0, numberOfElements)
                .mapToObj(i -> random.nextFloat() * numberOfElements)
                .forEach(list::add);
        }

        return list;
    }

    public static Long createElementsTest(int numberOfElements, DataType dataType) {
        long startTime = System.currentTimeMillis();
        initializeList(numberOfElements, dataType);
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    public static Long deleteElementsTest(int numberOfElements, DataType dataType) {
        LinkedList<Object> list = initializeList(numberOfElements, dataType);

        long startTime = System.currentTimeMillis();
        while (!list.isEmpty()) {
            list.removeFirst(); // or list.removeLast()
        }
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }
    public static Long filterTest(int numberOfElements, DataType dataType) {
        LinkedList<Object> list = initializeList(numberOfElements, dataType);

        long startTime, endTime;

        switch (dataType) {
            case INTEGER:
                startTime = System.currentTimeMillis();
                list = new LinkedList<>(list.parallelStream()
                    .filter(number -> (int)number % 2 == 0)
                    .collect(Collectors.toList()));
                endTime = System.currentTimeMillis();
                break;

            case FLOAT:
                startTime = System.currentTimeMillis();
                list = new LinkedList<>(list.parallelStream()
                    .filter(number -> (float)number > 30.0f)
                    .collect(Collectors.toList()));
                endTime = System.currentTimeMillis();
                break;

            case STRING:
                startTime = System.currentTimeMillis();
                list = new LinkedList<>(list.parallelStream()
                    .filter(word -> ((String)word).startsWith("a"))
                    .collect(Collectors.toList()));
                endTime = System.currentTimeMillis();
                break;

            default:
                return null;
        }

        return endTime - startTime;
    }

    public static Long sortTest(int numberOfElements, DataType dataType) {
        LinkedList<Object> list = initializeList(numberOfElements, dataType);

        long startTime = System.currentTimeMillis();
        list = new LinkedList<>(list.parallelStream()
            .sorted()
            .collect(Collectors.toList()));
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    public static <T> Long findTest(int numberOfElements, DataType dataType, T target) {
        LinkedList<T> list = (LinkedList<T>) initializeList(numberOfElements, dataType);

        long startTime, endTime;

        Predicate<T> matchPredicate = element -> Objects.equals(element, target);

        startTime = System.currentTimeMillis();
        boolean found = list.parallelStream().anyMatch(matchPredicate);
        endTime = System.currentTimeMillis();

        return found ? (endTime - startTime) : -1L;
    }

    public static <T> Long concatTest(int numberOfElements, DataType dataType) {
        LinkedList<T> list = (LinkedList<T>) initializeList(numberOfElements, dataType);

        long startTime = System.currentTimeMillis();
        String concatenated = list.parallelStream()
            .map(Object::toString)
            .collect(Collectors.joining());
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }
    public static Long reduceTest(int numberOfElements, DataType dataType) {
        LinkedList<?> list = initializeList(numberOfElements, dataType);

        long startTime, endTime;

        switch (dataType) {
            case INTEGER:
                startTime = System.currentTimeMillis();
                list.parallelStream()
                    .map(Integer.class::cast)
                    .reduce(0, Integer::sum);
                endTime = System.currentTimeMillis();
                break;

            case FLOAT:
                startTime = System.currentTimeMillis();
                list.parallelStream()
                    .map(Float.class::cast)
                    .reduce(0f, Float::sum);
                endTime = System.currentTimeMillis();
                break;

            case STRING:
                startTime = System.currentTimeMillis();
                list.parallelStream()
                    .map(Object::toString)
                    .reduce("", String::concat);
                endTime = System.currentTimeMillis();
                break;

            default:
                return null;
        }

        return endTime - startTime;
    }
}

