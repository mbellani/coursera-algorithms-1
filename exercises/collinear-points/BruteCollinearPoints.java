import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LineSegment[] segments = new LineSegment[1];
    private int segCount;


    public BruteCollinearPoints(Point[] points) {
        checkNotNull(points);
        Point[] input = new Point[points.length];
        copy(points, input);
        Arrays.sort(input);
        validate(input);
        for (int p = 0; p < input.length; p++) {
            for (int q = p + 1; q < input.length; q++) {
                for (int r = q + 1; r < input.length; r++) {
                    for (int s = r + 1; s < input.length; s++) {
                        Point pp = input[p];
                        Point pq = input[q];
                        Point pr = input[r];
                        Point ps = input[s];
                        if ((pp.slopeTo(pq) == pq.slopeTo(pr)) && (pq.slopeTo(pr) == pr.slopeTo(ps))) {
                            LineSegment lineSegment = new LineSegment(pp, ps);
                            if (segCount >= segments.length) {
                                resizeSegments(segCount * 2);
                            }
                            segments[segCount] = lineSegment;
                            segCount++;
                        }
                    }
                }
            }
        }
        resizeSegments(segCount);

    }

    private void resizeSegments(int size) {
        LineSegment[] oldSegments = segments;
        segments = new LineSegment[size];
        copy(oldSegments, segments);
    }

    private <T> void copy(T[] src, T[] target) {
        int len = src.length < target.length ? src.length : target.length;
        for (int i = 0; i < len; i++) {
            target[i] = src[i];
        }
    }

    public int numberOfSegments() {
        return segCount;
    }

    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[segCount];
        copy(segments, result);
        return result;
    }

    private void validate(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (i != 0 && (points[i].slopeTo(points[i - 1]) == Double.NEGATIVE_INFINITY)) {
                throw new IllegalArgumentException("found duplicates");
            }
        }
    }

    private void checkNotNull(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points cannot be null");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("found a null point");
            }
        }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
