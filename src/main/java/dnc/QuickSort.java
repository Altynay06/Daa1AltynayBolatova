package dnc;

import java.util.Random;

public class QuickSort {
    private static final Random RANDOM = new Random();

    public static void sort(int[] a, Metrics metrics) {
        shuffle(a);
        sort(a, 0, a.length - 1, 1, metrics);
    }

    private static void sort(int[] a, int lo, int hi, int depth, Metrics metrics) {
        metrics.updateDepth(depth);

        if (hi <= lo) return;

        int j = partition(a, lo, hi, metrics);

        int sizeLeft = j - lo;
        int sizeRight = hi - j;

        if (sizeLeft < sizeRight) {
            sort(a, lo, j - 1, depth + 1, metrics);
            lo = j + 1;
            sort(a, lo, hi, depth, metrics);
        } else {
            sort(a, j + 1, hi, depth + 1, metrics);
            lo = lo;
            hi = j - 1;
            sort(a, lo, hi, depth, metrics);
        }
    }

    private static int partition(int[] a, int lo, int hi, Metrics metrics) {
        int pivotIndex = lo + RANDOM.nextInt(hi - lo + 1);
        swap(a, lo, pivotIndex);

        int i = lo;
        int j = hi + 1;
        int v = a[lo]; // pivot

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

    private static void shuffle(int[] a) {
        for (int i = 0; i < a.length; i++) {
            int r = RANDOM.nextInt(i + 1);
            swap(a, i, r);
        }
    }
}