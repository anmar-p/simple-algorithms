package kdtree;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class KdTree {
    private Node root;
    private int size;
    private RectHV rootRect;

    public KdTree() {
        root = null;
        size = 0;
        rootRect = new RectHV(0.0, 0.0, 1.0, 1.0);
    }

    private class Node {
        private Point2D p;
        private Node lb;
        private Node rt;
        private boolean vertical;

        public Node(Point2D p, Node lb, Node rt, boolean vertical) {
            this.p = p;
            this.lb = lb;
            this.rt = rt;
            this.vertical = vertical;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        root = insertNode(root, p, false);
    }

    private Node insertNode(Node node, Point2D p, boolean vertical) {
        if (node == null) {                  // first node in tree
            size++;
            return new Node(p, null, null, vertical);
        }
        if (node.p.x() == p.x() && node.p.y() == p.y()) {
            node.p = p;
        } else if ((node.vertical && p.x() < node.p.x()) || (!node.vertical && p.y() < node.p.y())) {
            node.lb = insertNode(node.lb, p,!vertical);
        } else {
            node.rt = insertNode(node.rt, p, !vertical);
        }
        return node;
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Node node = root;
        while (node != null) {
            if (node.p.x() == p.x() && node.p.y() == p.y()) {
                return true;
            } else if ((node.vertical && node.p.x() > p.x()) || (!node.vertical && node.p.y() > p.y())) {
                node = node.lb;
            } else {
                node = node.rt;
            }
        }
        return false;
    }

    public void draw() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        drawPoints(root, rootRect);
    }

    private void drawPoints(Node node, RectHV rect) {
        if (node != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            node.p.draw();
            StdDraw.setPenRadius();

            if (node.vertical) {
                StdDraw.setPenColor(StdDraw.RED);
                Point2D init = new Point2D(node.p.x(), rect.ymin());
                Point2D end = new Point2D(node.p.x(), rect.ymax());
                init.drawTo(end);
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                Point2D init = new Point2D(rect.xmin(), node.p.y());
                Point2D end = new Point2D(rect.xmax(), node.p.y());
                init.drawTo(end);
            }
            StdDraw.show();
            drawPoints(node.lb, calculateRect(rect, node, "lb"));
            drawPoints(node.rt, calculateRect(rect, node, "rt"));
        }
    }

    private RectHV calculateRect(RectHV rect, Node node, String orientation) {
        if (orientation == "lb") {
            if (!node.vertical) {
                return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.p.y());
            } else {
                return new RectHV(rect.xmin(), rect.ymin(), node.p.x(), rect.ymax());
            }
        } else {
            if (!node.vertical) {
                return new RectHV(rect.xmin(), node.p.y(), rect.xmax(), rect.ymax());
            } else {
                return new RectHV(node.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
            }
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Queue<Point2D> points = new Queue<Point2D>();
        inRange(root, rootRect, rect, points);
        return points;
    }

    private void inRange(Node node, RectHV nodeRect, RectHV rect, Queue<Point2D> queue) {
        if (node != null) {
            if (nodeRect.intersects(rect)) {
                if (rect.contains(node.p)) {
                    queue.enqueue(node.p);
                }
                inRange(node.lb, calculateRect(nodeRect, node, "lb"), rect, queue);
                inRange(node.rt, calculateRect(nodeRect, node, "rt"), rect, queue);
            }
        }
    }
    
    public Point2D nearest(Point2D p) {
        return findNearest(root, rootRect, p, root.p);
    }

    private Point2D findNearest(Node node, RectHV rect, Point2D point, Point2D closest) {
        Point2D nearestPoint = closest;
        if (node != null) {      
            if (nearestPoint.distanceSquaredTo(point) > rect.distanceSquaredTo(point)) {
                if (node.p.distanceSquaredTo(point) < nearestPoint.distanceSquaredTo(point)) {
                    nearestPoint = node.p;
                }
                RectHV leftRect = calculateRect(rect, node, "lb");
                RectHV rightRect = calculateRect(rect, node, "rt");
                if (node.vertical) {    
                    if (point.x() <= node.p.x()) {
                        nearestPoint = findNearest(node.lb, leftRect, point, nearestPoint);
                        nearestPoint = findNearest(node.rt, rightRect, point, nearestPoint);
                    } else {
                        nearestPoint = findNearest(node.rt, rightRect, point, nearestPoint);
                        nearestPoint = findNearest(node.lb, leftRect, point, nearestPoint);
                    }
                } else {
                    if (point.y() <= node.p.y()) {
                        nearestPoint = findNearest(node.lb, leftRect, point, nearestPoint);
                        nearestPoint = findNearest(node.rt, rightRect, point, nearestPoint);
                    } else {
                        nearestPoint = findNearest(node.rt, rightRect, point, nearestPoint);
                        nearestPoint = findNearest(node.lb, leftRect, point, nearestPoint);
                    }
                }
            }
        }
        return nearestPoint;
    }


    // unit testing of the methods (optional)
    public static void main(String[] args) {
        In in = new In("C://Users//amrpa//Downloads//kdtree//input10K.txt");
        Stopwatch timer1 = new Stopwatch();
        KdTree set = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            set.insert(p);
        }
        set.draw();
        double time1 = timer1.elapsedTime();
        StdOut.println("time to add points to tree "+ time1);
        Point2D p1 = new Point2D(0.144, 0.179);
        Point2D p2 = new Point2D(0.417, 0.154);
        Point2D p3 = new Point2D(0.674, 0.51);  
        Point2D p4 = new Point2D(0.085, 0.53);
        Point2D p5 = new Point2D(0.785, 0.725);
        Point2D p6 = new Point2D(0.555, 0.565);  
        Stopwatch timer2 = new Stopwatch();
        System.out.println("point: "+p1.toString()+" "+set.contains(p1));
        System.out.println("point: "+p2.toString()+" "+set.contains(p2));
        System.out.println("point: "+p3.toString()+" "+set.contains(p3));
        System.out.println("point: "+p4.toString()+" "+set.contains(p4));
        System.out.println("point: "+p5.toString()+" "+set.contains(p5));
        System.out.println("point: "+p6.toString()+" "+set.contains(p6));
        double time2 = timer2.elapsedTime();
        StdOut.println("time to find if tree contains 6 points "+ time2);
        Stopwatch timer3 = new Stopwatch();
        //        set.draw();
        double time3 = timer3.elapsedTime();
        StdOut.println("time to draw "+ time3);
        Stopwatch timer4 = new Stopwatch();
        for(Point2D p : set.range(new RectHV(0.2, 0.200, 0.2300, 0.2300))) {
            StdOut.println(p.toString());
        }
        double time4 = timer4.elapsedTime();
        StdOut.println("time to FIND RANGE "+ time4);
        System.out.println("nearest to "+p3.toString()+": "+set.nearest(p3));
        System.out.println("nearest to "+p2.toString()+": "+set.nearest(p2));
        System.out.println("nearest to "+p4.toString()+": "+set.nearest(p4));
        System.out.println("nearest to "+p6.toString()+": "+set.nearest(p6));
    }

}

