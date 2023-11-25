package collections.benchmarks;

import collections.DataType;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BenchmarkArrayList {
    private static ArrayList initializeList(int numberOfElements, DataType dataType) {
        ArrayList List = new ArrayList<>();
        if (dataType == DataType.INTEGER) {
            List.addAll((ArrayList) IntStream.range(0, numberOfElements).parallel()
                .boxed()
                .collect(Collectors.toList()));
        } else if (dataType == DataType.STRING) {
            Random random = new Random();
            List.addAll((ArrayList) IntStream.range(0, numberOfElements).parallel()
                .mapToObj(i -> random.ints('a', 'z' + 1)
                    .limit(40)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString())
                .collect(Collectors.toList()));
        } else if (dataType == DataType.FLOAT) {
            Random random = new Random();
            List.addAll((ArrayList) IntStream.range(0, numberOfElements).parallel()
                .mapToObj(i -> random.nextFloat() * numberOfElements)
                .collect(Collectors.toList()));
        }

        return List;

    }
    public static Long createElementsTest(int numberOfElements, DataType dataType) {
        ArrayList list;

        long startTimeForAddingElements = System.currentTimeMillis();
        initializeList(numberOfElements, dataType);
        long endTimeForAddingElements = System.currentTimeMillis();

        return endTimeForAddingElements - startTimeForAddingElements;
    }
    public static Long readElementsTest(int numberOfElements, DataType dataType) {
        ArrayList list = initializeList(numberOfElements, dataType);

        long startTimeForReadElements = System.currentTimeMillis();
        IntStream.range(0, numberOfElements).parallel().forEach(list::get);
        long endTimeForReadElements = System.currentTimeMillis();

        return endTimeForReadElements - startTimeForReadElements;
    }
    public static Long updateElementsTest(int numberOfElements, DataType dataType) {
        ArrayList list = initializeList(numberOfElements, dataType);

        long startTimeForUpdateElements = System.currentTimeMillis();
        IntStream.range(0, numberOfElements).parallel().forEach(i -> list.set(i, i + 1));
        long endTimeForUpdateElements = System.currentTimeMillis();
        return endTimeForUpdateElements - startTimeForUpdateElements;
    }
    public static Long deleteElementsTest(int numberOfElements, DataType dataType) {
        ArrayList integers = initializeList(numberOfElements, dataType);

        long startTimeForDeleteElements = System.currentTimeMillis();
        IntStream.range(0, numberOfElements).parallel().map(i -> numberOfElements - 1 - i)
            .forEach(integers::remove);
        long endTimeForDeleteElements = System.currentTimeMillis();
        return endTimeForDeleteElements - startTimeForDeleteElements;
    }
    public static Long filterTest(int numberOfElements, DataType dataType) {
        ArrayList list = initializeList(numberOfElements, dataType);

        long startTimeForFilterElements, endTimeForFilterElements;

        switch (dataType) {
            case INTEGER:
                startTimeForFilterElements = System.currentTimeMillis();
                list = (ArrayList)list.parallelStream()
                    .filter(number -> (int)number % 2 == 0)
                    .toList();
                endTimeForFilterElements = System.currentTimeMillis();
                return endTimeForFilterElements - startTimeForFilterElements;

            case FLOAT:
                startTimeForFilterElements = System.currentTimeMillis();
                list = (ArrayList)list.parallelStream()
                    .filter(number -> (float)number > 30.0f)
                    .toList();
                endTimeForFilterElements = System.currentTimeMillis();
                return endTimeForFilterElements - startTimeForFilterElements;

            case STRING:
                startTimeForFilterElements = System.currentTimeMillis();
                list = (ArrayList)list.parallelStream()
                    .filter(word -> ((String)word).startsWith("a"))
                    .toList();
                endTimeForFilterElements = System.currentTimeMillis();
                return endTimeForFilterElements - startTimeForFilterElements;

            default:
                return null;
        }
    }
    public static Long sortTest(int numberOfElements, DataType dataType) {
        ArrayList list = initializeList(numberOfElements, dataType);

        long startTimeForFilterElements, endTimeForFilterElements;

        startTimeForFilterElements = System.currentTimeMillis();
        list = (ArrayList)list.parallelStream()
            .sorted()
            .collect(Collectors.toList());
        endTimeForFilterElements = System.currentTimeMillis();
        return endTimeForFilterElements - startTimeForFilterElements;
    }

    public static <T> Long findTest(int numberOfElements, DataType dataType, T target) {
        ArrayList<T> list = initializeList(numberOfElements, dataType);

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
        boolean found = list.parallelStream().anyMatch(matchPredicate);
        endTimeForFind = System.currentTimeMillis();

        return found ? (endTimeForFind - startTimeForFind) : -1L;
    }
    public static <T> Long concatTest(int numberOfElements, DataType dataType) {
        ArrayList<T> list = initializeList(numberOfElements, dataType);

        long startTimeForConcat, endTimeForConcat;

        Function<Object, String> mapper;
        switch (dataType) {
            case INTEGER, FLOAT, STRING:
                mapper = Object::toString;
                break;
            default:
                return null;
        }

        startTimeForConcat = System.currentTimeMillis();
        list.parallelStream()
            .map(mapper)
            .collect(Collectors.joining(", "));
        endTimeForConcat = System.currentTimeMillis();

        return endTimeForConcat - startTimeForConcat;
    }
    public static Long reduceTest(int numberOfElements, DataType dataType) {
        ArrayList<?> list = initializeList(numberOfElements, dataType);

        long startTimeForReduce, endTimeForReduce;

        switch (dataType) {
            case INTEGER:
                startTimeForReduce = System.currentTimeMillis();
                list.parallelStream()
                    .map(Integer.class::cast)
                    .reduce(0, Integer::sum);
                endTimeForReduce = System.currentTimeMillis();
                return endTimeForReduce - startTimeForReduce;

            case FLOAT:
                startTimeForReduce = System.currentTimeMillis();
                list.parallelStream()
                    .map(Float.class::cast)
                    .reduce(0f, Float::sum);
                endTimeForReduce = System.currentTimeMillis();
                return endTimeForReduce - startTimeForReduce;

            case STRING:
                startTimeForReduce = System.currentTimeMillis();
                list.parallelStream()
                    .map(Object::toString)
                    .reduce("", String::concat);
                endTimeForReduce = System.currentTimeMillis();
                return endTimeForReduce - startTimeForReduce;

            default:
                return null;
        }
    }

}
