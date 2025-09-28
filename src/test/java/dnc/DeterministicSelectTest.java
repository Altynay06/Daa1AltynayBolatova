package dnc;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class DeterministicSelectTest {

    private final Random random = new Random();

    @Test
    void testSelectAgainstArraysSort() {
        int N = 10000;
        int trials = 100;

        for (int i = 0; i < trials; i++) {
            int[] a = SortTestUtils.generateRandomArray(N);
            int[] a_copy = a.clone();

            int k = random.nextInt(N);

            Arrays.sort(a_copy);
            int expected = a_copy[k];

            int result = DeterministicSelect.select(a, k, new Metrics());

            assertEquals(expected, result, "Mismatch for k=" + k + " in trial " + i);
        }
    }

    @Test
    void testComplexity() {
        int N = 50000;
        int[] a = SortTestUtils.generateRandomArray(N);
        int k = N / 2;
        Metrics metrics = new Metrics();

        long startTime = System.nanoTime();
        DeterministicSelect.select(a, k, metrics);
        long endTime = System.nanoTime();

        int expectedMaxDepth = (int) (Math.log(N) / Math.log(1.0/0.7)) + 5;

        assertTrue(metrics.maxDepth < N / 10,
                "Max depth: " + metrics.maxDepth + " is too high for O(n) select.");

        CSVWriter.log("Select(MoM5)", N, endTime - startTime, metrics);
    }
}