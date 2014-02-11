/**
 * Created by kgp on 10/02/2014.
 */
public class PercolationStats {

    private int numberExperiments;
    private double[] results;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException("Given N <= 0 || T <= 0");
        }
        this.numberExperiments = t;
        results = new double[numberExperiments];
        for (int numExp = 0; numExp < numberExperiments; numExp++) {
            Percolation percolation = new Percolation(n);
            int openedSites = 0;
            while (!percolation.percolates()) {
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(i, j)) {
                    percolation.open(i, j);
                    openedSites++;
                }
            }
            results[numExp] = (double) openedSites / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(numberExperiments));
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(numberExperiments));
    }

    // test client, described below
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N, T);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }

}
