package dnc;

import java.util.Arrays;

public class DeterministicSelect {

    public static int select(int[] a, int k, Metrics metrics) {
        return select(a, 0, a.length - 1, k, 1, metrics);
    }

    private static int select(int[] a, int lo, int hi, int k, int depth, Metrics metrics) {
        metrics.updateDepth(depth);

        if (lo == hi) {
            return a[lo];
        }

        int pivotIndex = medianOfMedians(a, lo, hi, metrics);


        swap(a, lo, pivotIndex);
        int j = partition(a, lo, hi, metrics);

        // 3. Рекурсия
        if (k == j) {
            return a[k];
        } else if (k < j) {
            return select(a, lo, j - 1, k, depth + 1, metrics);
        } else {
            return select(a, j + 1, hi, k, depth + 1, metrics);
        }
    }

    private static int medianOfMedians(int[] a, int lo, int hi, Metrics metrics) {
        int n = hi - lo + 1;
        if (n <= 5) {
            return findMedianIndex(a, lo, hi, metrics);
        }

        int numGroups = (n + 4) / 5;
        int[] medians = new int[numGroups];
        metrics.incrementAllocations(numGroups * 4L);

        for (int i = 0; i < numGroups; i++) {
            int start = lo + i * 5;
            int end = Math.min(start + 4, hi);
            int medianValue = findMedian(a, start, end, metrics);
            medians[i] = medianValue;
        }

        int mom = select(medians, 0, medians.length - 1, (medians.length - 1) / 2, 1, metrics);

        for (int i = lo; i <= hi; i++) {
            if (a[i] == mom) {
                return i;
            }
        }
        return -1;
    }

    private static int findMedian(int[] a, int lo, int hi, Metrics metrics) {
        insertionSort(a, lo, hi, metrics);
        return a[lo + (hi - lo) / 2];
    }

    private static int findMedianIndex(int[] a, int lo, int hi, Metrics metrics) {
        insertionSort(a, lo, hi, metrics);
        return lo + (hi - lo) / 2;
    }

    private static void insertionSort(int[] a, int lo, int hi, Metrics metrics) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo; j--) {
                metrics.incrementComparison();
                if (a[j] < a[j - 1]) {
                    swap(a, j, j - 1);
                } else {
                    break;
                }
            }
        }
    }

    private static int partition(int[] a, int lo, int hi, Metrics metrics) {
        int i = lo;
        int j = hi + 1;
        int v = a[lo]; // pivot уже в lo

        while (true) {
            while (less(a[++i], v, metrics)) {
                if (i == hi) break;
            }
            while (less(v, a[--j], metrics)) {
                if (j == lo) break;
            }

            if (i >= j) break;
            swap(a, i, j);
        }

        swap(a, lo, j);
        return j;
    }

    private static boolean less(int v, int w, Metrics metrics) {
        metrics.incrementComparison();
        return v < w;
    }

    private static void swap(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
}