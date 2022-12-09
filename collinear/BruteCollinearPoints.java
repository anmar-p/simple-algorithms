package collinear;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class BruteCollinearPoints {
    
    private int numberOfSegments = 0;
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
      
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
            if (pointsCopy[i].compareTo(pointsCopy[i-1]) == 0) {
                throw new java.lang.IllegalArgumentException();
            }
            
        }
        ArrayList<Point[]> quadruples = new ArrayList<Point[]>();
        for (int i = 0; i < (points.length-3); i++) {
            for (int j = i+1; j < (points.length-2); j++) {
                for (int k = j+1; k < (points.length-1); k++) {
                    for (int m = k+1; m < points.length; m++) {
                        Point[] segment = new Point[4];
                        segment[0] = points[i];
                        segment[1] = points[j];
                        segment[2] = points[k];
                        segment[3] = points[m];
                        if (segment[0].slopeTo(segment[1]) == segment[0].slopeTo(segment[2]) && segment[0].slopeTo(segment[2]) == segment[0].slopeTo(segment[3])) {
                            Arrays.sort(segment);    
                            quadruples.add(segment);
                            numberOfSegments++;
                        }
                    }
                }
            }
        }
        
        ArrayList<LineSegment> segs = new ArrayList<>();
        for (int i = 0; i < quadruples.size(); i++){
            Point[] line = quadruples.get(i);
            LineSegment segment = new LineSegment(line[0], line[3]);
            boolean found = false;
            Iterator<LineSegment> it = segs.iterator();
            while (it.hasNext()) {
                if (segment.toString().equals(it.next().toString())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                segs.add(segment);
            }
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
