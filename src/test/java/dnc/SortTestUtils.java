package dnc;

import java.util.Arrays;
import java.util.Random;

public class SortTestUtils {
    public static int[] generateRandomArray(int n) {
        Random random = new Random();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = random.nextInt(n * 2);
        }
        return a;
    }

    public static int[] generateAdversarialArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = n - i;
        }
        return a;
    }

    public static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[i - 1]) return false;
        }
        return true;
    }
}