package sort;

public class MergeSortPractice {

    public static void sort(int[] a) {
        int aux[] = new int[a.length / 2];
        sort(a, aux, 0, a.length - 1);
    }

    private static void sort(int[] a, int[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge_creative(a, aux, lo, mid, hi);
    }

    private static void merge(int[] a, int[] aux, int lo, int mid, int hi) {
        int i = lo;
        int j = mid + 1;

        copy(a, aux, lo, hi);

        for (int k = lo; k <= hi; k++) {
            if (i > mid) { //left side exhausted
                a[k] = aux[j++];
            } else if (j > hi) { //right side exhausted
                a[k] = aux[i++];
            } else if (aux[i] <= aux[j]) { //compare left and right
                a[k] = aux[i++]; //assign left if left is less or equal
            } else { //otherwise assign right.
                a[k] = aux[j++];
            }
        }
    }

    private static void merge_creative(int a[], int aux[], int lo, int mid, int hi) {
        for (int i = lo; i < mid; i++) {
            aux[i] = a[i];
        }
        int i = lo;
        int j = mid;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                break;
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (aux[i] <= a[j]) {
                a[k] = aux[i++];
            } else {
                a[k] = a[j];
            }
        }

    }

    private static void copy(int[] a, int[] aux, int lo, int hi) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
    }

    public static void main(String[] args) {
        int[] a = {5, 4, 3, 2, 1};
        sort(a);

        for (int i : a) System.out.println(i);
    }
}
