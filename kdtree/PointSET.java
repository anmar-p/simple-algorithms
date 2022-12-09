package kdtree;
import java.awt.Color;
import java.util.Iterator;
import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class PointSET {

    private TreeSet<Point2D> tree;

    public PointSET() {
        tree = new TreeSet<Point2D>();
    }

    public boolean isEmpty() {
        return tree.size() == 0;
    }

    public int size() {
        return tree.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        tree.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        return tree.contains(p);
    }

    public void draw() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        for (Point2D p : tree) {
            p.draw();
        }
        StdDraw.show();
    }

    public Iterable<Point2D> range(RectHV rect){
        if (rect == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Queue<Point2D> intersections = new Queue<Point2D>();
        if (tree != null) {
            Iterator<Point2D> it = tree.iterator();
            while (it.hasNext()) {
                Point2D p = it.next();
                if (rect.contains(p)) {
                    intersections.enqueue(p);
                }
            }
        }
        return intersections;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Point2D nearest = null;
        if(tree != null && !tree.isEmpty()) {
            Point2D point = tree.first();
            double minDistance = point.distanceSquaredTo(p);
            for (Point2D pt : tree) {
                double distance = pt.distanceSquaredTo(p);
                if (distance <= minDistance) {
                    minDistance = distance;
                    nearest = pt;
                };
            }
        }
        return nearest;
    }


    public static void main(String[] args) {
        In in = new In(args[0]);
        //        In in = new In("C://Users//amrpa//Downloads//kdtree//input10K.txt");
        Stopwatch timer1 = new Stopwatch();
        PointSET set = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            set.tree.add(p);
        }
        double time1 = timer1.elapsedTime();
        StdOut.println("time to add points to tree "+ time1);
        //        for(Point2D p : set.tree) {
        //            StdOut.println(p.toString());
        //        }
        System.out.println("size = "+set.size());

        //        set.draw();
        RectHV rect = new RectHV(0.350, 0.200, 0.570, 0.760); 
        rect.draw();
        StdDraw.show();
        Stopwatch timer2 = new Stopwatch();
        Iterable<Point2D> q = set.range(rect);
        double time2 = timer2.elapsedTime();
        StdOut.println("time to find points in range "+time2);
        //        for (Point2D p : q) {
        //            StdOut.println(p.toString());
        //        }

        Point2D test = new Point2D(0.417, 0.154);
        StdDraw.setPenColor(Color.MAGENTA);
        StdDraw.setPenRadius(.01);
        test.draw();
        StdDraw.show();
        Stopwatch timer3 = new Stopwatch();
        System.out.println("closest neighbor: "+set.nearest(test));
        double time3 = timer3.elapsedTime();
        StdOut.println("time to find closest neighbor "+time3);
    }
}
