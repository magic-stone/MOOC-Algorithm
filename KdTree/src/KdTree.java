/*****************************************************************************
 *  Author:         Yan Xu
 *  Written:        10/8/2016
 *  Last updated:   10/8/2016
 *
 *  Compilation:    javac KdTree.java;
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
import java.util.TreeSet;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private int size;
    private Node root;

    private static class Node {
        private final boolean isVertical;
        private Node leftBottom;
        private Node rightTop;
        private final Point2D point;
        private final RectHV rect;

        public Node(Point2D p, Node l, Node r, boolean isVertical, RectHV rect) {
            this.point = p;
            this.leftBottom = l;
            this.rightTop = r;
            this.isVertical = isVertical;
            this.rect = rect;
        }
    }

    private static final RectHV CONTAINER = new RectHV(0, 0, 1, 1);

    public KdTree() {
        size = 0;
        root = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (root == null) {
            root = new Node(p, null, null, true, CONTAINER);
            size++;
        }
        Node node = root;
        while (true) {
            if (node.point.equals(p)) return;
            if (node.isVertical) {
                if (p.x() < node.point.x()) {
                    if (node.leftBottom != null) {
                        node = node.leftBottom;
                    } else {
                        node.leftBottom = new Node(p, null, null, false, leftRect(node.rect, node));
                        size++;
                        return;
                    }
                } else {
                    if (node.rightTop != null) {
                        node = node.rightTop;
                    } else {
                        node.rightTop = new Node(p, null, null, false, rightRect(node.rect, node));
                        size++;
                        return;
                    }
                }
            } else {
                if (p.y() < node.point.y()) {
                    if (node.leftBottom != null) {
                        node = node.leftBottom;
                    } else {
                        node.leftBottom = new Node(p, null, null, true, leftRect(node.rect, node));
                        size++;
                        return;
                    }
                } else {
                    if (node.rightTop != null) {
                        node = node.rightTop;
                    } else {
                        node.rightTop = new Node(p, null, null, true, rightRect(node.rect, node));
                        size++;
                        return;
                    }
                }
            }
        }
    }

    public boolean contains(Point2D p) {
        Node node = root;
        if (node == null) return false;
        while (true) {
            if (node.point.equals(p)) return true;
            if (node.isVertical) {
                if (p.x() < node.point.x()) {
                    if (node.leftBottom != null) {
                        node = node.leftBottom;
                    } else {
                        return false;
                    }
                } else {
                    if (node.rightTop != null) {
                        node = node.rightTop;
                    } else {
                        return false;
                    }
                }
            } else {
                if (p.y() < node.point.y()) {
                    if (node.leftBottom != null) {
                        node = node.leftBottom;
                    } else {
                        return false;
                    }
                } else {
                    if (node.rightTop != null) {
                        node = node.rightTop;
                    } else {
                        return false;
                    }
                }
            }
        }
    }

    // draw all of the points to standard draw
    public void draw() {
        StdDraw.setScale(0, 1);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        CONTAINER.draw();

        draw(root, CONTAINER);
    }

    private void draw(final Node node, final RectHV rect) {
        if (node == null) {
            return;
        }

        // draw the point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        new Point2D(node.point.x(), node.point.y()).draw();

        // get the min and max points of division line
        Point2D min, max;
        if (node.isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            min = new Point2D(node.point.x(), rect.ymin());
            max = new Point2D(node.point.x(), rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            min = new Point2D(rect.xmin(), node.point.y());
            max = new Point2D(rect.xmax(), node.point.y());
        }

        // draw that division line
        StdDraw.setPenRadius();
        min.drawTo(max);

        // recursively draw children
        draw(node.leftBottom, node.leftBottom.rect);
        draw(node.rightTop, node.rightTop.rect);
    }

    private RectHV leftRect(final RectHV rect, final Node node) {
        if (node.isVertical) {
            return new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax());
        } else {
            return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());
        }
    }

    private RectHV rightRect(final RectHV rect, final Node node) {
        if (node.isVertical) {
            return new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
        } else {
            return new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());
        }
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(final RectHV rect) {
        final TreeSet<Point2D> rangeSet = new TreeSet<Point2D>();
        range(root, CONTAINER, rangeSet);

        return rangeSet;
    }

    private void range(final Node node, final RectHV rect,
                       final TreeSet<Point2D> rangeSet) {
        if (node == null)
            return;

        if (rect.intersects(node.rect)) {
            final Point2D p = new Point2D(node.point.x(), node.point.y());
            if (rect.contains(p))
                rangeSet.add(p);
            range(node.leftBottom, rect, rangeSet);
            range(node.rightTop, rect, rangeSet);
        }
    }

    public Point2D nearest(final Point2D p){
        return nearest(p, root, root.point);
    }

    private Point2D nearest(Point2D queryPoint, Node node, Point2D champion){
        if (node == null) return champion;
        // if node point is closer than champion, it becomes the new champion
        if (queryPoint.distanceSquaredTo(node.point) < queryPoint.distanceSquaredTo(champion)) {
            champion = node.point;
        }
        // only explore node if it can contain a point closer than the champion
        if (node.rect.distanceSquaredTo(queryPoint) < queryPoint.distanceSquaredTo(champion)) {
            // query point is left or below node point
            if ((node.isVertical && queryPoint.x() < node.point.x()) ||
                    ((!node.isVertical && queryPoint.y() < node.point.y()))){
                // explore left bottom first
                champion = nearest(queryPoint, node.leftBottom, champion);
                champion = nearest(queryPoint, node.rightTop, champion);
            } else {
                // query point is right, above, or equal to node point
                // explore right top first
                champion = nearest(queryPoint, node.rightTop, champion);
                champion = nearest(queryPoint, node.leftBottom, champion);
            }
        }
        return champion;
    }

    
}
