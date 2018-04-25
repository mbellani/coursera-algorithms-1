import java.util.stream.IntStream;

public class UnionFind {

    public static class QuickFind {
        private int[] a;

        public QuickFind(int size) {
            a = new int[size];
            IntStream.range(0, size).forEach(i -> a[i] = i);
        }

        public boolean isConnected(int p, int q) {
            return a[p] == a[q];
        }

        public int union(int p, int q) {
            int vp = a[p];
            int vq = a[q];
            for (int i = 0; i < a.length; i++) {
                if (a[i] == vp) {
                    a[i] = vq;
                }
            }
            return q;
        }
    }

    public static class QuickUnion {
        protected int[] a;

        public QuickUnion(int n) {
            this.a = new int[n];
            IntStream.range(0, n).forEach(i -> a[i] = i);
        }

        public boolean isConnected(int p, int q) {
            return root(p) == root(q);
        }

        public int union(int p, int q) {
            int rp = root(p);
            int rq = root(q);
            a[rp] = rq;
            return rq;
        }

        protected int root(int i) {
            return a[i] == i ? i : root(a[i]);
        }
    }

    public static class WeightedQuickUnion extends QuickUnion {
        private int size[];
        private int max[];

        public WeightedQuickUnion(int n) {
            super(n);
            this.size = new int[n];
            this.max = new int[n];
            IntStream.range(0, n).forEach(i -> this.size[i] = 1);
            IntStream.range(0, n).forEach(i -> this.max[i] = 0);
        }

        public int union(int p, int q) {
            if (p == q) {
                return 0;
            }
            int rp = root(p);
            int rq = root(q);
            if (size[rp] > size[rq]) {
                a[rq] = rp;
                size[rp] += size[rq];
                max[rp] = Math.max(Math.max(p, q), max[rp]);

            } else {
                a[rp] = rq;
                size[rq] += size[rp];
                max[rq] = Math.max(Math.max(p, q), max[rq]);
            }
            return p;
        }

        public int find(int i) {
            return max[root(i)];
        }
    }

    public static class WeightedQuickUnionWithPathCompression extends QuickUnion {
        public WeightedQuickUnionWithPathCompression(int n) {
            super(n);
        }

        @Override
        protected int root(int i) {
            if (a[i] == i) {
                return i;
            } else {
                a[i] = a[a[i]];
                return root(a[i]);
            }
        }
    }

}
