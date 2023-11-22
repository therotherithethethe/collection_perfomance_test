import collections.benchmarks.BenchmarkArrayList;
import collections.DataType;

public class Main {
    public static void main(String[] args) {
        System.out.println(BenchmarkArrayList.reduceTest(500000000, DataType.INTEGER));
    }
}