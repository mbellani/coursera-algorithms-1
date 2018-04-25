import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final int n;
    private final int trials;
    private final double[] results;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.trials = trials;
        this.results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            int result = 0;
            while (!percolation.percolates()) {
                openSite(percolation);
                result++;

            }
            results[i] = ((double) result / (double) (n * n));
        }
    }

    private void openSite(Percolation percolation) {
        while (true) {
            int siteX = StdRandom.uniform(1, n + 1);
            int siteY = StdRandom.uniform(1, n + 1);
            if (!percolation.isOpen(siteX, siteY)) {
                percolation.open(siteX, siteY);
                break;
            }
        }
    }

    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        return mean() - ((CONFIDENCE_95 * stddev()) / Math.sqrt(trials));
    }

    public double confidenceHi() {
        return mean() + ((CONFIDENCE_95 * stddev()) / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean = " + percolationStats.mean());
        System.out.println("stddev = " + percolationStats.stddev());
        System.out.println("confidence interval hi: " + percolationStats.confidenceHi() + " lo: " + percolationStats.confidenceLo());
    }
}
