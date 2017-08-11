package percolation;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/*****************************************************************************
 *  Author:         Yan Xu
 *  Written:        9/7/2016
 *  Last updated:   9/10/2016
 *
 *  Compilation:    javac Percolation.java;
 *  Execution:      java Percolation;
 *  Dependency:     QuickFindUF.java
 *
 *  Percolation models a percolation system. It will create a n-by-n grid and
 *  show whether the system percolate or not.
 *****************************************************************************/

public class Percolation{
    private WeightedQuickUnionUF g; // Percolation data type using quick find algorithm
    private WeightedQuickUnionUF gplus;
    private Boolean[][] grid = null; // grid with each site open(True) or close(False)
    private int n = 0; // number of rows in the grid

    /**
     * constructor: create n-by-n grid, with all sites blocked
     *
     * @param n: number of rows in the grid
     * @throws IllegalArgumentException if(n <=0)
     */
    public Percolation(int n){
        if (n <= 0) throw new IllegalArgumentException("n should be larger than 0");
        grid = new Boolean[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                grid[i][j] = false; // every site is blocked
        this.n = n;
        // Create a union-find data structure with n*n from 1 to n*n
        // Also create a top(0) and bottom(n*n+1) position
        g = new WeightedQuickUnionUF(n * n + 2);
        gplus = new WeightedQuickUnionUF(n * n + 1);
    }

    /**
     * open site(row i, column j) if it is not open already
     * if its up or down or left or right site is also open, union them
     *
     * @param i: row
     * @param j: column
     * @throws IndexOutOfBoundsException if (i,j) is outside the range from (0,0) to (N-1,N-1)
     */
    public void open(int i, int j){
        if (j < 1||i < 1||i > n||j > n)
            throw new IndexOutOfBoundsException("index out of bounds");

        grid[i-1][j-1] = true;

        // if it is in the first row, union it to the top
        if ((i-1) == 0) {
            g.union((i-1) * n + (j-1) + 1, 0);
            gplus.union((i-1) * n + (j-1) + 1, 0);
        }

        // for the last row site, union it to the bottom
        if ((i-1) == n - 1) g.union((i-1) * n + j-1 + 1, n * n + 1);

        // if its up site is open, union them.
        if ((i-1) > 0 && grid[i-1 - 1][j-1]) {
            g.union((i-1) * n + j-1 + 1, (i-1 - 1) * n + j-1 + 1);
            gplus.union((i-1) * n + j-1 + 1, (i-1 - 1) * n + j-1 + 1);
        }

        // if its down site is open, union them.
        if ((i-1) < n - 1 && grid[i-1 + 1][j-1]) {
            g.union((i-1 + 1) * n + j-1 + 1, (i-1) * n + (j-1) + 1);
            gplus.union((i-1 + 1) * n + j-1 + 1, (i-1) * n + (j-1) + 1);
        }

        // if its left site is open, union them.
        if ((j-1) > 0 && grid[i-1][j-1 - 1]) {
            g.union((i-1) * n + (j-1) + 1, (i-1) * n + (j-1));
            gplus.union((i-1) * n + j-1 + 1, (i-1) * n + j-1);
        }

        // if its right site is open, union them.
        if ((j-1) < n - 1 && grid[i-1][j-1 + 1]) {
            g.union((i-1) * n + j-1 + 1, (i-1) * n + j-1 + 2);
            gplus.union((i-1) * n + j-1 + 1, (i-1) * n + j-1 + 2);
        }
    }

    /**
     * show whether site(row i, column j) is open
     *
     * @param i: row
     * @param j: column
     * @return Boolean value: true(open) or false(block)
     * @throws IndexOutOfBoundsException if (i,j) is outside the range from (0,0) to (N-1,N-1)
     */
    public boolean isOpen(int i, int j){
        if (i < 1||j < 1||i > n||j > n)
            throw new IndexOutOfBoundsException("index out of bounds");
        return grid[i-1][j-1];
    }

    /**
     * show whether site(row i, column j) is full
     *
     * @param x: row
     * @param y: column
     * @return Boolean value: true(full) or false(not full)
     * @throws IndexOutOfBoundsException if (i,j) is outside the range from (0,0) to (N-1,N-1)
     */
    public boolean isFull(int x, int y){
        if (x < 1 || y < 1 || x > n || y > n )
            throw new IndexOutOfBoundsException("index out of bounds");
        // if the site is connected to its up,down,left or right, then it is full;

        if (gplus.connected((x-1) * n + y-1 + 1, 0)) return true;
        return false;

    }

    /**
     * show whether the system percolate
     * @return Boolean value: true(full) or false(not full)
     * @throws IndexOutOfBoundsException if (i,j) is outside the range from (0,0) to (N-1,N-1)
     */
    public boolean percolates() {
        // if the top and bottom connected then it percolates
        return g.connected(0, n * n + 1);
    }
}
