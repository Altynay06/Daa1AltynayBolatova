package dnc;

import java.util.Arrays;

public class MergeSort {

    private static final int CUTOFF = 7;

    public static void sort(int[] a, Metrics metrics) {
        int[] aux = new int[a.length];
        metrics.incrementAllocations(a.length * 4L); // 4 байта на int
        sort(a, aux, 0, a.length - 1, 1, metrics);
    }

    private static void sort(int[] a, int[] aux, int lo, int hi, int depth, Metrics metrics) {
        metrics.updateDepth(depth);

        if (hi - lo + 1 <= CUTOFF) {
            insertionSort(a, lo, hi, metrics);
            return;
        }

        if (hi <= lo) return;

        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid, depth + 1, metrics);
        sort(a, aux, mid + 1, hi, depth + 1, metrics);
        merge(a, aux, lo, mid, hi, metrics);
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

    private static void merge(int[] a, int[] aux, int lo, int mid, int hi, Metrics metrics) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i], metrics)) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
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