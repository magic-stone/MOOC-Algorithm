/*****************************************************************************
 *  Author:         Yan Xu
 *  Written:        10/8/2016
 *  Last updated:   10/8/2016
 *
 *  Compilation:    javac PointSET.java;
 *  Execution:      java Point;
 *  Dependency:     Point2D.java
 *                  RectHV.java
 *                  SET.java
 *                  StdDraw.java;
 *                  Stack.java;
 *
 *  Range search and nearest neighbor search with set of points
 *****************************************************************************/
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Stack;

public class PointSET {
    private SET<Point2D> point;

    // construct an empty set of points
    public PointSET(){
        point = new SET<Point2D>();
    }

    // is the set Empty?
    public boolean isEmpty(){
        return point.isEmpty();
    }

    // number of points in the set
    public int size(){
        return point.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p){
        point.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p){
        return point.contains(p);
    }

    // draw all points to standard draw
    public void draw(){
        for(Point2D p : point){
            p.draw();
        }
        StdDraw.show();
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect){
        Stack<Point2D> stack = new Stack<Point2D>();
        for(Point2D p : point) {
            if (rect.contains(p)) {
                stack.push(p);
            }
        }
        return stack;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p){
        if(point.isEmpty()) return null;
        Double distance_min = Double.MAX_VALUE;
        Point2D nearest = null;
        for(Point2D m : point){
            if(m.distanceTo(p) < distance_min){
                distance_min = p.distanceTo(m);
                nearest = m;
            }
        }
        return nearest;
    }
}
