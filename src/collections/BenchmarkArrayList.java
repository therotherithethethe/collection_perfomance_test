package collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BenchmarkArrayList {
    private static ArrayList InitializeList(int numberOfElements, DataType dataType) {
        ArrayList List = new ArrayList<>();
        if (dataType == DataType.INTEGER) {
            List.addAll((ArrayList) IntStream.range(0, numberOfElements)
                .boxed()
                .collect(Collectors.toList()));
        } else if (dataType == DataType.STRING) {
            Random random = new Random();
            List.addAll((ArrayList) IntStream.range(0, numberOfElements)
                .mapToObj(i -> random.ints('a', 'z' + 1)
                    .limit(4)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString())
                .collect(Collectors.toList()));
        } else if (dataType == DataType.FLOAT) {
            Random random = new Random();
            List.addAll((ArrayList) IntStream.range(0, numberOfElements)
                .mapToObj(i -> random.nextFloat() * numberOfElements)
                .collect(Collectors.toList()));
        }

        return List;

    }
    public static Long createElementsTest(int numberOfElements, DataType dataType) {
        ArrayList list;

        long startTimeForAddingElements = System.currentTimeMillis();
        list = InitializeList(numberOfElements, dataType);
        long endTimeForAddingElements = System.currentTimeMillis();

        return endTimeForAddingElements - startTimeForAddingElements;
    }
    public static Long ReadElementsTest(int numberOfElements, DataType dataType) {
        ArrayList list = InitializeList(numberOfElements, dataType);

        long startTimeForReadElements = System.currentTimeMillis();
        IntStream.range(0, numberOfElements).forEach(list::get);
        long endTimeForReadElements = System.currentTimeMillis();

        return endTimeForReadElements - startTimeForReadElements;
    }
    public static Long UpdateElementsTest(int numberOfElements, DataType dataType) {
        ArrayList list = InitializeList(numberOfElements, dataType);

        long startTimeForUpdateElements = System.currentTimeMillis();
        IntStream.range(0, numberOfElements).forEach(i -> list.set(i, i + 1));
        long endTimeForUpdateElements = System.currentTimeMillis();
        return endTimeForUpdateElements - startTimeForUpdateElements;
    }
    public static Long DeleteElementsTest(int numberOfElements, DataType dataType) {
        ArrayList integers = InitializeList(numberOfElements, dataType);

        long startTimeForDeleteElements = System.currentTimeMillis();
        IntStream.range(0, numberOfElements).map(i -> numberOfElements - 1 - i)
            .forEach(integers::remove);
        long endTimeForDeleteElements = System.currentTimeMillis();
        return endTimeForDeleteElements - startTimeForDeleteElements;
    }
    public static Long FilterTest(int numberOfElements, DataType dataType) {
        ArrayList list = InitializeList(numberOfElements, dataType);

        long startTimeForFilterElements, endTimeForFilterElements;

        switch (dataType) {
            case INTEGER:
                startTimeForFilterElements = System.currentTimeMillis();
                list = (ArrayList)list.stream()
                    .filter(number -> (int)number % 2 == 0)
                    .toList();
                endTimeForFilterElements = System.currentTimeMillis();
                return endTimeForFilterElements - startTimeForFilterElements;

            case FLOAT:
                startTimeForFilterElements = System.currentTimeMillis();
                list = (ArrayList)list.stream()
                    .filter(number -> (float)number > 30.0f)
                    .toList();
                endTimeForFilterElements = System.currentTimeMillis();
                return endTimeForFilterElements - startTimeForFilterElements;

            case STRING:
                startTimeForFilterElements = System.currentTimeMillis();
                list = (ArrayList)list.stream()
                    .filter(word -> ((String)word).startsWith("a"))
                    .toList();
                endTimeForFilterElements = System.currentTimeMillis();
                return endTimeForFilterElements - startTimeForFilterElements;

            default:
                return null;
        }
    }
    public static Long SortTest(int numberOfElements, DataType dataType) {
        ArrayList list = InitializeList(numberOfElements, dataType);

        long startTimeForFilterElements, endTimeForFilterElements;
        switch (dataType) {
            case INTEGER:
                startTimeForFilterElements = System.currentTimeMillis();
                list = (ArrayList)list.stream()
                    .sorted()
                    .collect(Collectors.toList());
                endTimeForFilterElements = System.currentTimeMillis();
                return endTimeForFilterElements - startTimeForFilterElements;

            case FLOAT:
                startTimeForFilterElements = System.currentTimeMillis();
                list = (ArrayList)list.stream()
                    .sorted()
                    .collect(Collectors.toList());
                endTimeForFilterElements = System.currentTimeMillis();
                return endTimeForFilterElements - startTimeForFilterElements;

            case STRING:
                startTimeForFilterElements = System.currentTimeMillis();
                Collections.sort(list);
                endTimeForFilterElements = System.currentTimeMillis();
                return endTimeForFilterElements - startTimeForFilterElements;

            default:
                return null;
        }
    }
    public static Long findTest(int numberOfElements, DataType dataType, Object target) {
        ArrayList list = InitializeList(numberOfElements, dataType);

        long startTimeForFind, endTimeForFind;

        switch (dataType) {
            case INTEGER:
                startTimeForFind = System.currentTimeMillis();
                boolean foundInteger = list.stream()
                    .anyMatch(number -> (int)number == (int)target);
                endTimeForFind = System.currentTimeMillis();
                return foundInteger ? (endTimeForFind - startTimeForFind) : -1L;

            case FLOAT:
                startTimeForFind = System.currentTimeMillis();
                boolean foundFloat = list.stream()
                    .anyMatch(number -> (float)number == (float)target);
                endTimeForFind = System.currentTimeMillis();
                return foundFloat ? (endTimeForFind - startTimeForFind) : -1L;

            case STRING:
                startTimeForFind = System.currentTimeMillis();
                boolean foundString = list.contains(target);
                endTimeForFind = System.currentTimeMillis();
                return foundString ? (endTimeForFind - startTimeForFind) : -1L;

            default:
                return null;
        }
    }
    public static Long ConcatTest(int numberOfElements, DataType dataType) {
        ArrayList list = InitializeList(numberOfElements, dataType);

        long startTimeForConcat, endTimeForConcat;

        switch (dataType) {
            case INTEGER:
                startTimeForConcat = System.currentTimeMillis();
                String concatenatedInteger = (String) list.stream()
                    .map(number -> String.valueOf((int)number))
                    .collect(Collectors.joining(", "));
                endTimeForConcat = System.currentTimeMillis();
                return endTimeForConcat - startTimeForConcat; // Return the time taken for concatenation

            case FLOAT:
                startTimeForConcat = System.currentTimeMillis();
                String concatenatedFloat = (String) list.stream()
                    .map(number -> String.valueOf((float)number))
                    .collect(Collectors.joining(", "));
                endTimeForConcat = System.currentTimeMillis();
                return endTimeForConcat - startTimeForConcat; // Return the time taken for concatenation

            case STRING:
                startTimeForConcat = System.currentTimeMillis();
                String concatenatedString = String.join(", ", list);
                endTimeForConcat = System.currentTimeMillis();
                return endTimeForConcat - startTimeForConcat; // Return the time taken for concatenation

            default:
                return null;
        }
    }
    public static Long ReduceTest(int numberOfElements, DataType dataType) {
        ArrayList list = InitializeList(numberOfElements, dataType);

        long startTimeForReduce, endTimeForReduce;

        switch (dataType) {
            case INTEGER:
                startTimeForReduce = System.currentTimeMillis();
                int sumInteger = list.stream()
                    .mapToInt(number -> (int)number)
                    .sum();
                endTimeForReduce = System.currentTimeMillis();
                return endTimeForReduce - startTimeForReduce; // Return the time taken for reduction

            case FLOAT:
                startTimeForReduce = System.currentTimeMillis();
                double sumFloat = list.stream()
                    .mapToDouble(number -> (float)number)
                    .sum();
                endTimeForReduce = System.currentTimeMillis();
                return endTimeForReduce - startTimeForReduce; // Return the time taken for reduction

            case STRING:
                startTimeForReduce = System.currentTimeMillis();
                String concatenatedString = (String) list.stream()
                    .map(String::valueOf)
                    .reduce("", (s1, s2) -> ((String)s1) + ((String)s2));
                endTimeForReduce = System.currentTimeMillis();
                return endTimeForReduce - startTimeForReduce; // Return the time taken for reduction

            default:
                return null;
        }
    }
}
