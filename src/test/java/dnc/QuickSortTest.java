package dnc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {

    @Test
    void testSmallRandomArray() {
        int[] a = SortTestUtils.generateRandomArray(10);

        QuickSort.sort(a, new Metrics());

        assertTrue(SortTestUtils.isSorted(a));
    }

    @Test
    void testAdversarialArray() {
        int[] a = SortTestUtils.generateAdversarialArray(500);

        QuickSort.sort(a, new Metrics());

        assertTrue(SortTestUtils.isSorted(a));
    }

    @Test
    void testLargeArrayAndDepth() {
        int N = 50000;
        int[] a = SortTestUtils.generateRandomArray(N);
        Metrics metrics = new Metrics();

        long startTime = System.nanoTime();
        QuickSort.sort(a, metrics);
        long endTime = System.nanoTime();

        assertTrue(SortTestUtils.isSorted(a));

        int expectedMaxDepth = (int) (2 * Math.log(N) / Math.log(2)) + 5;
        assertTrue(metrics.maxDepth < expectedMaxDepth,
                "Max depth: " + metrics.maxDepth + " is too high for N=" + N);

        CSVWriter.log("QuickSort", N, endTime - startTime, metrics);
    }
}