package collections.benchmarks;

import collections.DataType;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Random;

public class BenchmarkTreeMap {
    private static TreeMap<Integer, Object> initializeMap(int numberOfElements, DataType dataType) {
        TreeMap<Integer, Object> map = new TreeMap<>();
        Random random = new Random();

        if (dataType == DataType.INTEGER) {
            IntStream.range(0, numberOfElements).forEach(i -> map.put(i, i));
        } else if (dataType == DataType.STRING) {
            IntStream.range(0, numberOfElements).forEach(i ->
                map.put(i, random.ints('a', 'z' + 1)
                    .limit(10)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString()));
        } else if (dataType == DataType.FLOAT) {
            IntStream.range(0, numberOfElements).forEach(i -> map.put(i, random.nextFloat() * numberOfElements));
        }

        return map;
    }

    public static Long createElementsTest(int numberOfElements, DataType dataType) {
        long startTime = System.currentTimeMillis();
        TreeMap<Integer, Object> map = initializeMap(numberOfElements, dataType);
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    public static Long readElementsTest(int numberOfElements, DataType dataType) {
        TreeMap<Integer, Object> map = initializeMap(numberOfElements, dataType);

        long startTime = System.currentTimeMillis();
        map.forEach((key, value) -> {});
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    public static Long updateElementsTest(int numberOfElements, DataType dataType) {
        TreeMap<Integer, Object> map = initializeMap(numberOfElements, dataType);

        long startTime = System.currentTimeMillis();
        map.keySet().forEach(key -> map.put(key, key + 1));
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    public static Long deleteElementsTest(int numberOfElements, DataType dataType) {
        TreeMap<Integer, Object> map = initializeMap(numberOfElements, dataType);

        long startTime = System.currentTimeMillis();
        map.clear();
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    public static <T> Long findTest(int numberOfElements, DataType dataType, T target) {
        TreeMap<Integer, Object> map = initializeMap(numberOfElements, dataType);

        long startTimeForFind, endTimeForFind;

        Predicate<Object> matchPredicate;
        switch (dataType) {
            case INTEGER, STRING, FLOAT:
                matchPredicate = element -> Objects.equals(element, target);
                break;
            default:
                return null;
        }

        startTimeForFind = System.currentTimeMillis();
        boolean found = map.values().parallelStream().anyMatch(matchPredicate);
        endTimeForFind = System.currentTimeMillis();

        return found ? (endTimeForFind - startTimeForFind) : -1L;
    }

    public static Long concatTest(int numberOfElements, DataType dataType) {
        TreeMap<Integer, Object> map = initializeMap(numberOfElements, dataType);

        long startTimeForConcat, endTimeForConcat;

        startTimeForConcat = System.currentTimeMillis();
        String concatenated = map.values().parallelStream()
            .map(Object::toString)
            .collect(Collectors.joining(", "));
        endTimeForConcat = System.currentTimeMillis();

        return endTimeForConcat - startTimeForConcat;
    }

    public static Long reduceTest(int numberOfElements, DataType dataType) {
        TreeMap<Integer, Object> map = initializeMap(numberOfElements, dataType);

        long startTimeForReduce, endTimeForReduce;

        switch (dataType) {
            case INTEGER:
                startTimeForReduce = System.currentTimeMillis();
                int sum = map.values().parallelStream()
                    .mapToInt(value -> (Integer) value)
                    .reduce(0, Integer::sum);
                endTimeForReduce = System.currentTimeMillis();
                return endTimeForReduce - startTimeForReduce;

            case FLOAT:
                startTimeForReduce = System.currentTimeMillis();
                float sumFloat = (float)map.values().parallelStream()
                    .mapToDouble(value -> (Float) value)
                    .reduce(0d, Double::sum);
                endTimeForReduce = System.currentTimeMillis();
                return endTimeForReduce - startTimeForReduce;

            case STRING:
                startTimeForReduce = System.currentTimeMillis();
                String concatenatedStrings = map.values().parallelStream()
                    .map(Object::toString)
                    .reduce("", String::concat);
                endTimeForReduce = System.currentTimeMillis();
                return endTimeForReduce - startTimeForReduce;

            default:
                return null;
        }
    }
}
