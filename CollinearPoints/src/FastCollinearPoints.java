
/*****************************************************************************
 *  Author:         Yan Xu
 *  Written:        9/14/2016
 *  Last updated:   9/14/2016
 *
 *  Compilation:    javac FastCollinearPoints.java;
 *  Execution:      java FastCollinearPoints;
 *  Dependency:     Point.java
 *                  java.util.Arrays
 *                  java.util.LinkedList
 *
 *  FastCollinearPoints is a fast method to find every(maximal) line segment that
 *  connects a subset of 4 or more of the points.
 *****************************************************************************/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private final LineSegment[] Segment;
    private final int count;
    private double[][] Slope;
    private LineSegment[] Segment_temp;
    private int count_temp = 0;

    /**
     * constructor: finds all lines segments containing 4(or more)points
     *
     * @param points: all the points in the array
     * @throws NullPointerException     either the argument to the constructor is null or if
     *                                  any point in the array is null;
     * @throws IllegalArgumentException if the argument to the constructor contains a repeated point.
     * ???? How to control the total length of the LineSegment? Resize?
     *
     */
    public FastCollinearPoints(Point[] points) {
        int length = points.length;
        Segment_temp = new LineSegment[50 * length];

        // sort the points array;
        Arrays.sort(points);
        for(int n = 0; n < length - 1; n++){
            if(points[n].slopeTo(points[n + 1]) == Double.NEGATIVE_INFINITY)
                throw new java.lang.IllegalArgumentException();
        }
        // loop through each naturally sorted point
        for (Point naturalPoint : points) {
            // clone natural order, then sort to slope order based on slope to first point
            Point[] slopeOrderPoints = points.clone();
            Arrays.sort(slopeOrderPoints, naturalPoint.slopeOrder());
            // create new segment
            LinkedList<Point> segment = new LinkedList<Point>();
            // add point i as origin
            segment.add(naturalPoint);
            // index 0 in slope order sorted is always the origin point (slope negative infinity)
            // this loop compares i to j and i to j+1. j only needs to loop through N-2
            for (int j = 1; j < points.length - 1; j++) {

                Point slopePoint = slopeOrderPoints[j];
                Point nextSlopePoint = slopeOrderPoints[j + 1];

                double slope = naturalPoint.slopeTo(slopePoint);
                double nextSlope = naturalPoint.slopeTo(nextSlopePoint);

                if (slope == nextSlope) {
                    if (segment.peekLast() != slopePoint) {
                        segment.add(slopePoint);
                    }
                    segment.add(nextSlopePoint);
                }

                // clear segment if no match (end of segment or loop)
                if (slope != nextSlope || j == points.length - 2) {
                    // first output segment if it is large enough
                    if (segment.size() > 3) {
                        outputSegment(segment);
                    }
                    segment.clear();
                    segment.add(naturalPoint);
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

    private void outputSegment(LinkedList<Point> segment) {
        // to remove sub-segments, we rely on the following logic:
        // the outer loop's array is sorted via natural order
        // the inner loop is sorted in slope order according
        // to the current number in the outer loop
        // a discovered segment should always start at
        // its naturally lowest point
        // in the case of a sub-segment, the outer loop will
        // start the segment at somewhere other than its lowest
        // in this case, we can discover this by comparing
        // whether the first point of the segment is in fact the lowest

        Point first = segment.removeFirst();
        Point second = segment.removeFirst();
        Point last = segment.removeLast();
        if (first.compareTo(second) < 0) {
            Segment_temp[count_temp] = new LineSegment(first,last);
            count_temp++;
        }
    }

    public int numberOfSegments() {
        return count;
    }

    public LineSegment[] segments() {
        return Segment;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}



