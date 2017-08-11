package percolation;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

/*****************************************************************************
 *  Author:         Yan Xu
 *  Written:        9/8/2016
 *  Last updated:   9/10/2016
                        *
 *  Compilation:    javac PercolationStats.java;
 *  Execution:      java PercolationStats;
 *  Dependency:     Percolation1.java
 *                  StdRandom.java
 *                  StdStats.java
 *
 *  PercolationStats performs a series of computational experiments on a n-by-n grid
 *  percolation system. It performs trials independent computational experiments on an
 *  n-by-n grid and prints the mean, standard deviation and the 95% confidence interval
 *  for the percolation threshold.
 *****************************************************************************/

public class PercolationStats {

    private double[] threshold;//percolation threshold
    private int trials;//number of trials

    /**
     *constructor: create a trials independent experiments on an n-by-n grid. In each
     *trial, open the site randomly until the system percolates and then record the number
     *of sites opened at this point.
     *
     *@param n: number of rows in the grid
     *@param trials: number of independent computational experiment
     *@throws IllegalArgumentException if(n <= 0 or trials <= 0)
     */
    public PercolationStats(int n, int trials){
        if(n <= 0 || trials <= 0){
            throw new IllegalArgumentException("n or trials should larger than 0");
        }
        Percolation grid; //n-by-n grid percolation system
        this.trials = trials;
        threshold = new double[trials];
        for(int i = 0; i < trials; i++){
            grid = new Percolation(n);
            int count = 0;
            while(!grid.percolates()){
                int x = StdRandom.uniform(n) + 1;
                int y = StdRandom.uniform(n) + 1;
                if(!grid.isOpen(x,y)){
                    grid.open(x,y);
                    count++;
                    if(grid.percolates()){
                        threshold[i] = count/((double)n * n);
                        break;
                    }
                }

            }
        }
    }

    /**
     *sample mean of percolation threshold
     *@return double value: mean of percolation threshold
     */

    public double mean(){
        return StdStats.mean(threshold);
    }

    /**
     * sample standard deviation of percolation threshold
     * @return double value: standard deviation of percolation threshold
     */

    public double stddev(){
        return StdStats.stddev(threshold);
    }


    /**
     *low endpoint of 95% confidence interval
     *@return double value: low  endpoint of 95% confidence interval
     */

    public double confidenceLo(){
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    /**
     *high endpoint of 95% confidence interval
     *@return double value: low  endpoint of 95% confidence interval
     */

    public double confidenceHi(){
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    public static void main(String[] args){
        Stopwatch time;

        time = new Stopwatch();
        PercolationStats test = new PercolationStats(200,100);
        System.out.println("% java PercolationStats 200 100");
        System.out.println("mean                    = " + test.mean());
        System.out.println("stddev                  = " + test.stddev());
        System.out.println("95% confidence interval = " + test.confidenceLo()
                + "," + test.confidenceHi());
        System.out.println("total running time" + time.elapsedTime());
        System.out.println("");

        time = new Stopwatch();
        test = new PercolationStats(200,100);
        System.out.println("% java PercolationStats 200 100");
        System.out.println("mean                    = " + test.mean());
        System.out.println("stddev                  = " + test.stddev());
        System.out.println("95% confidence interval = " + test.confidenceLo()
                + "," + test.confidenceHi());
        System.out.println("total running time" + time.elapsedTime());
        System.out.println("");

        time = new Stopwatch();
        test = new PercolationStats(2,10000);
        System.out.println("% java PercolationStats 2 10000");
        System.out.println("mean                    = " + test.mean());
        System.out.println("stddev                  = " + test.stddev());
        System.out.println("95% confidence interval = " + test.confidenceLo()
                + "," + test.confidenceHi());
        System.out.println("total running time" + time.elapsedTime());
        System.out.println("");

        time = new Stopwatch();
        test = new PercolationStats(2,10000);
        System.out.println("% java PercolationStats 2 10000");
        System.out.println("mean                    = " + test.mean());
        System.out.println("stddev                  = " + test.stddev());
        System.out.println("95% confidence interval = " + test.confidenceLo()
                + "," + test.confidenceHi());
        System.out.println("total running time" + time.elapsedTime());
        System.out.println("");
    }
}
