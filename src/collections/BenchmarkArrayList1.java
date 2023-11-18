package collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BenchmarkArrayList1 {

    // Enum for specifying the data type of the ArrayList elements
    public enum DataType1 {
        INTEGER, STRING, FLOAT
    }

    // Initialize an ArrayList with specified number of elements of a certain data type
    private static <T> ArrayList<T> initializeList(int numberOfElements, DataType dataType) {
        ArrayList<T> list = new ArrayList<>();

        switch (dataType) {
            case INTEGER:
                list.addAll((Collection<? extends T>) IntStream.range(0, numberOfElements)
                    .boxed()
                    .collect(Collectors.toList()));
                break;
            case STRING:
                list.addAll((Collection<? extends T>) IntStream.range(0, numberOfElements)
                    .mapToObj(i -> generateRandomString(10))
                    .collect(Collectors.toList()));
                break;
            case FLOAT:
                list.addAll((Collection<? extends T>) IntStream.range(0, numberOfElements)
                    .mapToObj(i -> new Random().nextFloat())
                    .collect(Collectors.toList()));
                break;
        }
        return list;
    }

    // Method to create a random string of specified length
    private static String generateRandomString(int length) {
        Random random = new Random();
        return random.ints('a', 'z' + 1)
            .limit(length)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

    // Method to benchmark initialization time of the ArrayList
    public static long benchmarkInitialization(int numberOfElements, DataType dataType) {
        long startTime = System.currentTimeMillis();
        ArrayList<?> list = initializeList(numberOfElements, dataType);
        return System.currentTimeMillis() - startTime;
    }

    // Method to benchmark reading elements from the ArrayList
    public static long benchmarkRead(int numberOfElements, DataType dataType) {
        ArrayList<?> list = initializeList(numberOfElements, dataType);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < list.size(); i++) {
            Object element = list.get(i);
        }

        return System.currentTimeMillis() - startTime;
    }

    // Method to benchmark sorting of the ArrayList
    public static long benchmarkSort(int numberOfElements, DataType dataType) {
        ArrayList<?> list = initializeList(numberOfElements, dataType);
        long startTime = System.currentTimeMillis();
        Collections.sort((ArrayList<Comparable>) list);
        return System.currentTimeMillis() - startTime;
    }

    // Method to benchmark concatenation of elements in the ArrayList
    public static long benchmarkConcatenation(int numberOfElements, DataType dataType) {
        ArrayList<?> list = initializeList(numberOfElements, dataType);
        long startTime = System.currentTimeMillis();

        StringBuilder sb = new StringBuilder();
        for (Object element : list) {
            sb.append(element.toString());
        }

        return System.currentTimeMillis() - startTime;
    }

    // Method to benchmark reduction operation on the ArrayList
    public static long benchmarkReduction(int numberOfElements, DataType dataType) {
        ArrayList<?> list = initializeList(numberOfElements, dataType);
        long startTime = System.currentTimeMillis();

        if (dataType == DataType.INTEGER) {
            int sum = list.stream()
                .mapToInt(element -> (Integer) element)
                .sum();
        } else if (dataType == DataType.FLOAT) {
            float sum = (float) list.stream()
                .mapToDouble(element -> (Float) element)
                .sum();
        } else if (dataType == DataType.STRING) {
            String concatenated = list.stream()
                .map(Object::toString)
                .reduce("", String::concat);
        }

        return System.currentTimeMillis() - startTime;
    }
}