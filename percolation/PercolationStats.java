package percolation;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final int n, trials; 
    private double[] resultsOfExperiments;
    private double mean, stddev;
    private double confidenceLo, confidenceHi;
    
    public PercolationStats(int n, int trials) {
        this.n = n;
        this.trials = trials;
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        resultsOfExperiments = new double[trials];
        performTrials();
    }
    
    private void performTrials() {
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int randomRow = StdRandom.uniform(1, n+1);
                int randomCol = StdRandom.uniform(1, n+1);
                if (!(perc.isOpen(randomRow, randomCol))) {
                    perc.open(randomRow, randomCol);
                }
            }
            resultsOfExperiments[i]= (double) perc.numberOfOpenSites() / (n*n);
        }
        mean = mean();
        stddev = stddev();
        confidenceLo = confidenceLo();
        confidenceHi = confidenceHi();
        return;
    }

    public double mean() {
        mean = StdStats.mean(resultsOfExperiments);
        return mean;
    }
    
    public double stddev() {
        stddev = StdStats.stddev(resultsOfExperiments);
        return stddev;
    }
    
    public double confidenceLo() {
        confidenceLo = mean - (CONFIDENCE_95*stddev) / Math.sqrt(trials);
        return confidenceLo;
    }
    
    public double confidenceHi() {
        confidenceHi = mean + (CONFIDENCE_95*stddev) / Math.sqrt(trials);
        return confidenceHi;
    }
    
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.print("Percolation Stats ready:\n");
        StdOut.print("Mean = " + stats.mean + "\n");
        StdOut.print("Standard Deviation = " + stats.stddev + "\n");
        StdOut.print("95% ConfidenceInterval = [" + stats.confidenceLo + "," + stats.confidenceHi + "]\n");
    }
}
