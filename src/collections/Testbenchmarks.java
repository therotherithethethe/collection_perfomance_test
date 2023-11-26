package collections;

import collections.DataType;
import collections.benchmarks.BenchmarkArrayList;
import collections.benchmarks.BenchmarkHashSet;
import collections.benchmarks.BenchmarkLinkedHashSet;
import collections.benchmarks.BenchmarkLinkedList;
import collections.benchmarks.BenchmarkTreeMap;
import java.util.Map;
import java.util.TreeMap;

public class Testbenchmarks {
    public static void createTest(int numberOfElements, DataType dataType) {
        Map<Long, String> testingTime = new TreeMap<>();
        testingTime.put(BenchmarkHashSet.createElementsTest(numberOfElements, dataType), "HashSet");
        testingTime.put(BenchmarkArrayList.createElementsTest(numberOfElements, dataType), "ArrayList");
        testingTime.put(BenchmarkLinkedHashSet.createElementsTest(numberOfElements, dataType), "LinkedHashSet");
        testingTime.put(BenchmarkLinkedList.createElementsTest(numberOfElements, dataType), "LinkedList");
        testingTime.put(BenchmarkTreeMap.createElementsTest(numberOfElements, dataType), "TreeMap");
        printMap(testingTime);
    }
    public static void readTest(int numberOfElements, DataType dataType) {
        Map<Long, String> testingTime = new TreeMap<>();
        testingTime.put(BenchmarkArrayList.readElementsTest(numberOfElements, dataType), "ArrayList");
        testingTime.put(BenchmarkTreeMap.readElementsTest(numberOfElements, dataType), "TreeMap");
        printMap(testingTime);
    }
    public static void updateTest(int numberOfElements, DataType dataType) {
        Map<Long, String> testingTime = new TreeMap<>();
        testingTime.put(BenchmarkArrayList.updateElementsTest(numberOfElements, dataType), "ArrayList");
        testingTime.put(BenchmarkTreeMap.updateElementsTest(numberOfElements, dataType), "TreeMap");
        printMap(testingTime);
    }
    public static void deleteTest(int numberOfElements, DataType dataType) {
        Map<Long, String> testingTime = new TreeMap<>();
        testingTime.put(BenchmarkHashSet.deleteElementsTest(numberOfElements, dataType), "HashSet");
        testingTime.put(BenchmarkArrayList.deleteElementsTest(numberOfElements, dataType), "ArrayList");
        testingTime.put(BenchmarkLinkedHashSet.deleteElementsTest(numberOfElements, dataType), "LinkedHashSet");
        testingTime.put(BenchmarkLinkedList.deleteElementsTest(numberOfElements, dataType), "LinkedList");
        testingTime.put(BenchmarkTreeMap.deleteElementsTest(numberOfElements, dataType), "TreeMap");
        printMap(testingTime);
    }
    public static void filterTest(int numberOfElements, DataType dataType) {
        Map<Long, String> testingTime = new TreeMap<>();
        testingTime.put(BenchmarkHashSet.filterTest(numberOfElements, dataType), "HashSet");
        testingTime.put(BenchmarkArrayList.filterTest(numberOfElements, dataType), "ArrayList");
        testingTime.put(BenchmarkLinkedHashSet.filterTest(numberOfElements, dataType), "LinkedHashSet");
        testingTime.put(BenchmarkLinkedList.filterTest(numberOfElements, dataType), "LinkedList");
        printMap(testingTime);
    }
    public static void sortTest(int numberOfElements, DataType dataType) {
        Map<Long, String> testingTime = new TreeMap<>();
        testingTime.put(BenchmarkArrayList.sortTest(numberOfElements, dataType), "ArrayList");
        testingTime.put(BenchmarkLinkedList.sortTest(numberOfElements, dataType), "LinkedList");
        printMap(testingTime);
    }
    public static <T> void findTest(int numberOfElements, DataType dataType, T target) {
        Map<Long, String> testingTime = new TreeMap<>();
        testingTime.put(BenchmarkArrayList.findTest(numberOfElements, dataType, target), "ArrayList");
        testingTime.put(BenchmarkLinkedList.findTest(numberOfElements, dataType, target), "LinkedList");
        testingTime.put(BenchmarkHashSet.findTest(numberOfElements, dataType, target), "HashSet");
        testingTime.put(BenchmarkLinkedHashSet.findTest(numberOfElements, dataType, target), "LinkedHashSet");
        testingTime.put(BenchmarkTreeMap.findTest(numberOfElements, dataType, target), "TreeMap");
        printMap(testingTime);
    }
    public static void concatTest(int numberOfElements, DataType dataType) {
        Map<Long, String> testingTime = new TreeMap<>();
        testingTime.put(BenchmarkArrayList.concatTest(numberOfElements, dataType), "ArrayList");
        testingTime.put(BenchmarkLinkedHashSet.concatTest(numberOfElements, dataType), "LinkedHashSet");
        testingTime.put(BenchmarkLinkedList.concatTest(numberOfElements, dataType), "LinkedList");
        testingTime.put(BenchmarkTreeMap.concatTest(numberOfElements, dataType), "TreeMap");
        printMap(testingTime);
    }
    public static void reduceTest(int numberOfElements, DataType dataType) {
        Map<Long, String> testingTime = new TreeMap<>();
        testingTime.put(BenchmarkHashSet.reduceTest(numberOfElements, dataType), "HashSet");
        testingTime.put(BenchmarkArrayList.reduceTest(numberOfElements, dataType), "ArrayList");
        testingTime.put(BenchmarkLinkedHashSet.reduceTest(numberOfElements, dataType), "LinkedHashSet");
        testingTime.put(BenchmarkLinkedList.reduceTest(numberOfElements, dataType), "LinkedList");
        testingTime.put(BenchmarkTreeMap.reduceTest(numberOfElements, dataType), "TreeMap");
        printMap(testingTime);
    }

    private static void printMap(Map<Long, String> testingTime) {
        testingTime.keySet().stream() //без паралелСтрім, бо виводиться без сортування
            .map(value -> testingTime.get(value) + ": " + value + " ms")
            .forEach(System.out::println);
    }

}
