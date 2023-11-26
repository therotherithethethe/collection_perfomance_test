package gui;

import collections.DataType;
import collections.Testbenchmarks;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, BenchmarkStrategy> benchmarkStrategies = new HashMap<>();
    private static final String tfinal = "Тест закінчено";
    public static void printMenu() {
        initializeDictionary();
        System.out.println("Виберіть потрібну вам операцію:"
            + "\n1. CreateBenchmark"
            + "\n2. ReadBenchmark"
            + "\n3. UpdateBenchmark"
            + "\n4. DeleteBenchmark"
            + "\n5. FilterBenchmark"
            + "\n6. SortBenchmark"
            + "\n7. Findbenchmark"
            + "\n8. ConcatBenchmark"
            + "\n9. reduce");

        String operationChoose = scanner.next();
        switch (operationChoose) {
            case "1" -> printBenchmark("create");
            case "2" -> printBenchmark("read");
            case "3" -> printBenchmark("update");
            case "4" -> printBenchmark("delete");
            case "5" -> printBenchmark("filter");
            case "6" -> printBenchmark("sort");
            case "7" -> printBenchmark("find");
            case "8" -> printBenchmark("concat");
            case "9" -> printBenchmark("reduce");
            default -> printMenu();
        }
    }
    private static DataType dataTypeChoice() throws InputMismatchException {
        System.out.println("choice type of data:");
        Arrays.stream(DataType.values()).forEach(System.out::println);
        int n = scanner.nextInt() -1;
        return DataType.values()[n];
    }
    private static void initializeDictionary() {
        benchmarkStrategies.put("read", Testbenchmarks::readTest);
        benchmarkStrategies.put("update", Testbenchmarks::updateTest);
        benchmarkStrategies.put("create", Testbenchmarks::createTest);
        benchmarkStrategies.put("delete", Testbenchmarks::deleteTest);
        benchmarkStrategies.put("filter", Testbenchmarks::filterTest);
        benchmarkStrategies.put("sort", Testbenchmarks::sortTest);
       // benchmarkStrategies.put("find", Testbenchmarks::findTest);
        benchmarkStrategies.put("concat", Testbenchmarks::concatTest);
        benchmarkStrategies.put("reduce", Testbenchmarks::reduceTest);
    }
    private static void printBenchmark(String benchmarkType) {
        try {
            System.out.println("Введіть кількість елементів");
            int numberOfElements = scanner.nextInt();
            DataType dataType = dataTypeChoice();
            BenchmarkStrategy strategy = benchmarkStrategies.get(benchmarkType);

            if (strategy != null) {
                strategy.performTest(numberOfElements, dataType);
            } else {
                System.out.println("Непізнаний тип бенчмарку: " + benchmarkType);
            }

            System.out.println(tfinal);
            printMenu();
        } catch (InputMismatchException e) {
            System.out.println("Неправильний ввід. Спробуйте ще раз.");
        }
    }

}
@FunctionalInterface
interface BenchmarkStrategy {
    void performTest(int numberOfElements, DataType dataType);
}
