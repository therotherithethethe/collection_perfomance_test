package gui;

import java.util.Scanner;
import testingCollections.Testbenchmarks;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);
    public static void printMenu() {
        System.out.println("Виберіть потрібну вам операцію:"
            + "\n1. CreateBenchmark"
            + "\n2. ReadBenchmark"
            + "\n3. UpdateBenchmark"
            + "\n4. DeleteBenchmark");

        int operationChoose = scanner.nextInt();
    }
}
