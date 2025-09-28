package dnc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {

    @Test
    void testSmallRandomArray() {
        int[] a = SortTestUtils.generateRandomArray(10);

        MergeSort.sort(a, new Metrics());

        assertTrue(SortTestUtils.isSorted(a));
    }

    @Test
    void testAdversarialArray() {
        int[] a = SortTestUtils.generateAdversarialArray(500);

        MergeSort.sort(a, new Metrics());

        assertTrue(SortTestUtils.isSorted(a));
    }

    @Test
    void testLargeArray() {
        int N = 50000;
        int[] a = SortTestUtils.generateRandomArray(N);
        Metrics metrics = new Metrics();

        long startTime = System.nanoTime();
        MergeSort.sort(a, metrics);
        long endTime = System.nanoTime();

        assertTrue(SortTestUtils.isSorted(a));

        int expectedMaxDepth = (int) (Math.log(N) / Math.log(2)) + 5;
        assertTrue(metrics.maxDepth < expectedMaxDepth,
                "Max depth: " + metrics.maxDepth + " is too high for N=" + N);

        CSVWriter.log("MergeSort", N, endTime - startTime, metrics);
    }
}