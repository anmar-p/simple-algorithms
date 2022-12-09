package collinear;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {

    private int numberOfSegments = 0;
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {

        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }

        for (int n = 0; n < points.length; n++) {
            if (points[n] == null) {
                throw new java.lang.IllegalArgumentException();
            }
        }

        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);

        for (int i = 1; i < pointsCopy.length; i++) {
            if (pointsCopy[i].compareTo(pointsCopy[i - 1]) == 0) {
                throw new java.lang.IllegalArgumentException();
            }
        }

        ArrayList<Point[]> combinations = new ArrayList<Point[]>();
        for (int i = 0; i < points.length - 3; i++) { // create a list of arrays where each array has a point and all
                                                      // other points it has connections with
            Point[] connections = new Point[points.length - i];
            connections[0] = pointsCopy[i];
            for (int j = i + 1; j < points.length; j++) {
                connections[j - i] = pointsCopy[j];
            }
            combinations.add(connections);
        }

        ArrayList<Point[]> lines = new ArrayList<>();
        for (int i = 0; i < combinations.size(); i++) { // sort list arrays based on the slopes they make with the
                                                        // origin
            Point[] spline = combinations.get(i);
            Point origin = spline[0];
            Comparator<Point> comparator = origin.slopeOrder();
            Arrays.sort(spline, comparator);
            double[] slopes = new double[spline.length - 1];
            for (int j = 1; j < spline.length; j++) {
                slopes[j - 1] = origin.slopeTo(spline[j]);
            }
            int collinear = 2;
            for (int k = 0; k < slopes.length - 2; k++) {
                if (slopes[k] == slopes[k + 1] && slopes[k] == slopes[k + 2]) {
                    collinear = 4;
                    outer: for (int l = k + 3; l < slopes.length; l++) {
                        if (slopes[l] == slopes[k]) {
                            collinear++;
                            l++;
                        } else {
                            break;
                        }
                    }
                    
                }
                if (collinear > 3) {
                    Point[] line = new Point[collinear];
                    line[0] = origin;
                    for (int p = 1; p < collinear; p++) {
                        line[p] = spline[p + k];
                    }
                    collinear = 2;
                    boolean found = false;
                    for (int q = 0; q < lines.size(); q++) {
                        if (Arrays.asList(lines.get(q)).containsAll(Arrays.asList(line))) {
                            found = true;
                            break;
                        }
                        if (Arrays.asList(line).containsAll(Arrays.asList(lines.get(q)))) {
                            found = true;
                            lines.set(q, line);
                            break;
                        }
                        
                    }
                    
                    if (!found) {
                        lines.add(line);
                    } 
                }

            }
        }

    ArrayList<LineSegment> segs = new ArrayList<>();
    for (int i = 0; i < lines.size(); i++) {
        Point[] line = lines.get(i);
        LineSegment segment = new LineSegment(line[0], line[line.length - 1]);
        segs.add(segment);
        numberOfSegments++;
    }
    segs.trimToSize();
    segments = segs.toArray(new LineSegment[segs.size()]);

    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {

        return segments;

    }

}
