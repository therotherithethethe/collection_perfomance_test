import collections.BenchmarkArrayList;
import collections.DataType;
import java.util.Set;
import java.util.SortedSet;

public class Main {
    public static void main(String[] args) {
        System.out.println(BenchmarkArrayList.ReduceTest(500000000, DataType.INTEGER));
    }
}