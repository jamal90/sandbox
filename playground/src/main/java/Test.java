import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) throws IOException {
        int[] test = new int[] {1, 2,3, 4, 1,2, 3,4};

        Map<Integer, Long> collect = Arrays.stream(test).mapToObj(i -> (Integer) i).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map<Integer, Integer> map = new HashMap<>();

        for (int n: test) {
            map.computeIfAbsent(n, n1 -> 0);
            map.put(n, map.get(n) + 1);
        }

        System.out.println(collect);

    }
}
