/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  Author:         Yan Xu
 *  Written:        9/14/2016
 *  Last updated:   9/14/2016
 *
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the x-coordinate of the point
     * @param  y the y-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (this.x == that.x && this.y == that.y) {
            /* treat the slope of a degenerate line segment as negative infinity. */
            return Double.NEGATIVE_INFINITY;
        } else if (this.x == that.x) {
            /* treat the slope of a vertical line segment as positive infinity */
            return Double.POSITIVE_INFINITY;
        } else if (this.y == that.y) {
            /* treat the slope of a horizontal line segment as positive zero */
            return +0.0;
        }
        return (double)(that.y - this.y)/(that.x - this.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value 0 if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if(this == null || that == null)
            throw new java.lang.NullPointerException();
        if(this.y < that.y) return -1;
        if(this.y == that.y) {
            if (this.x < that.x) {
                return -1;
            } else if (this.x == that.x) {
                return 0;
            } else return 1;
        }
        return 1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point>{
        public int compare(Point a, Point b){
            if(a == null || b == null)
                throw new java.lang.NullPointerException();
            double slopeA = slopeTo(a);
            double slopeB = slopeTo(b);
            // if a or b, is the same as the original point, return -32768,
            // means, can not compare
            if(slopeA > slopeB) return 1;
            if(slopeA < slopeB) return -1;
            return 0;
        }
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }




    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point a = new Point(4,4);
        Point b = new Point(11,11);
        Point c = new Point(11,6);
        Point e = new Point(10,4);
        StdOut.println("Test standard draw method");
        b.draw();
        StdOut.println("Test drawTo method,from b to c");
        b.drawTo(c);
        StdOut.println("Test compareTo method, compare a to b, should be -1");
        StdOut.println(a.compareTo(b));
        StdOut.println("Test compareTo method, compare b to c, should be 1");
        StdOut.println(b.compareTo(c));
        StdOut.println("Test slopeTo method, slope of a,b should be 1");
        StdOut.println(a.slopeTo(b));
        StdOut.println("Test slopeTo method, slope of b,c should be Positive_Infinity");
        StdOut.println(b.slopeTo(c));
        StdOut.println("Test slopeTo method, slope of c,c should be Negative_Infinity");
        StdOut.println(c.slopeTo(c));
        StdOut.println("Test slopeTo method, slope of a,e should be +0.0");
        StdOut.println(a.slopeTo(e));
        StdOut.println("Test slopeorder method, set a as origin, compare b,e, shoule be 1");
        StdOut.println(a.slopeOrder().compare(b,e));
    }
}