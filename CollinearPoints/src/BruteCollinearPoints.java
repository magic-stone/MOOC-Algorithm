/*****************************************************************************
 *  Author:         Yan Xu
 *  Written:        9/14/2016
 *  Last updated:   9/14/2016
 *
 *  Compilation:    javac BruteCollinearPoints.java;
 *  Execution:      java BruteCollinearPoints;
 *  Dependency:     Point.java
 *                  java.util.Arrays
 *
 *  BruteCollinearPoints is a brute-force method to find every(maximal) line
 *  segment that connects a subset of 4 of the points.
 *****************************************************************************/
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private final LineSegment[] Segment;
    private final int count;
    /**
     * constructor: finds all lines segments containing 4 points
     *
     * @param points: all the points in the array
     * @throws IllegalArgumentException if the argument to the constructor contains a repeated point.
     */
    public BruteCollinearPoints(Point[] points){
        int count_temp = 0;
        int length = points.length;
        LineSegment[] Segment_temp = new LineSegment[50 * length];
        // sort the points array;
        Arrays.sort(points);
        // find collinear points using brute force method;
        for(int n = 0; n < length - 1; n++){
            if(points[n].slopeTo(points[n + 1]) == Double.NEGATIVE_INFINITY)
                throw new java.lang.IllegalArgumentException();
        }
        for(int i = 0; i < length - 3; i++){
            for(int j = i + 1; j < length - 2; j++) {
                for (int k = j + 1; k < length - 1; k++) {
                    for (int l = k + 1; l < length; l++) {
                        double slope_1 = points[i].slopeTo(points[j]);
                        double slope_2 = points[i].slopeTo(points[k]);
                        double slope_3 = points[i].slopeTo(points[l]);
                        if (slope_1 == slope_2 && slope_2 == slope_3
                                && points[i].compareTo(points[j]) < 1
                                && points[j].compareTo(points[k]) < 1
                                && points[k].compareTo(points[l]) < 1) {
                            Segment_temp[count_temp] = new LineSegment(points[i], points[l]);
                            count_temp++;
                        }
                    }
                }
            }
        }
        // resize the Segment array;
        Segment = new LineSegment[count_temp];
        for(int k = 0; k < count_temp; k++){
            Segment[k] = Segment_temp[k];
        }
        count = count_temp;
    }

    public int numberOfSegments(){
        return count;
    }

    public LineSegment[] segments(){
        return Segment;
    }
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);      // input file
        int n = in.readInt();         // n-by-n percolation system
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments() ) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
