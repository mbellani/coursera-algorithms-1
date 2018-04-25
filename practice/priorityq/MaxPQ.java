package priorityQ;

import edu.princeton.cs.algs4.StdRandom;

public class MaxPQ<E extends Comparable> {
    private E[] pq;
    private int n;

    public MaxPQ(int length) {
        pq = (E[]) new Comparable[length];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void insert(E e) {
        pq[++n] = e;
        swim(n);
    }

    public E delMax() {
        E max = max();
        exch(1, n--);
        sink(1);
        pq[n + 1] = null;
        return max;
    }

    public E delMedian() {
        E median = median();
        int k = (n - 1) / 2;
        exch(k, n--);
        sink(k);
        pq[n + 1] = null;
        return median;
    }

    private E median() {
        return pq[(n - 1) / 2];
    }


    public E max() {
        return pq[1];
    }

    public E sample() {
        int i = randomIndex();
        return pq[i];
    }

    private int randomIndex() {
        return n == 1 ? 1 : StdRandom.uniform(1, n);
    }

    public E delRandom() {
        int i = randomIndex();
        E e = pq[i];
        exch(i, n--);
        sink(i);
        pq[n + 1] = null;
        return e;
    }

    private void swim(int i) {
        while (i > 1 && less(i / 2, i)) {
            exch(i, i / 2);
            i = i / 2;
        }
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void sink(int i) {
        while (2 * i <= n) {
            int k = 2 * i;
            if (k < n && less(k, k + 1)) k++;
            if (less(i, k)) {
                exch(i, k);
                i = k;
            } else {
                break;
            }
        }
    }

    private void exch(int i, int j) {
        E tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
    }


    public static void main(String[] args) {
        MaxPQ pq = new MaxPQ(6);
        pq.insert(1);
        pq.insert(2);
        pq.insert(3);
        pq.insert(4);
        pq.insert(5);
        System.out.println(pq.delRandom());
        System.out.println(pq.delRandom());
        System.out.println(pq.delRandom());
        System.out.println(pq.delRandom());
        System.out.println(pq.delRandom());
        System.out.println(pq.isEmpty());
    }
}
