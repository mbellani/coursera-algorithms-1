package sort;

import edu.princeton.cs.algs4.StdRandom;

public class QuickSortPractice {

    public void sort(int a[]) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    public void sort(int a[], int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int j = partition(a, lo, hi);
        sort(a, lo, j);
        sort(a, j + 1, hi);
    }

    public int partition(int a[], int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        while (true) {
            while (a[lo] >= a[++i]) if (i >= hi) break;

            while (a[lo] <= a[--j]) if (j <= lo) break;

            if (i >= j) break;

            exchg(a, i, j);
        }
        exchg(a, lo, j);
        return j;
    }

    private void exchg(int[] a, int i, int j) {
        int temp = a[j];
        a[j] = a[i];
        a[i] = temp;
    }

    public static void main(String[] args) {
        QuickSortPractice qsp = new QuickSortPractice();
        int[] a = {5, 4, 3, 7, 8, 2, 9, 15, 6, 12, 6};
        qsp.sort(a);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }
}
