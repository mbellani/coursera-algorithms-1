import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private LineSegment[] segments = new LineSegment[1];
    private int segmentCount;

    public FastCollinearPoints(Point[] points) {
        checkNotNull(points);
        Point[] input = new Point[points.length];
        copy(points, input);
        Arrays.sort(input);
        validate(input);
//        SegmentContainer previousSegmentContainer = null;
        for (int i = 0; i < input.length; i++) {
            Point p = input[i];
            if (p == null) {
                throw new IllegalArgumentException("none of the points elements can be null");
            }
            Arrays.sort(input, i + 1, input.length, p.slopeOrder());
            SegmentContainer segmentContainer = findRelativeSegments(p, input, i + 1);
            if (segmentContainer != null) {
                if (segmentCount >= segments.length) {
                    resizeSegments(segments.length * 2);
                }
                segments[segmentCount] = segmentContainer.segment;
//                previousSegmentContainer = segmentContainer;
                segmentCount++;
            }
        }
        resizeSegments(segmentCount);
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

    private boolean isAlreadyPresent(SegmentContainer segmentContainer, SegmentContainer previousSegmentContainer) {
        return previousSegmentContainer != null && previousSegmentContainer.encompasses(segmentContainer);

    }

    private SegmentContainer findRelativeSegments(Point p, Point[] points, int startIndex) {
        int segmentsFound = 0;
        Point min = p;
        Point max = p;
        for (int j = startIndex; j < points.length; j++) {
            Point q = points[j];
            if (min.slopeTo(q) == max.slopeTo(q)) {
                min = min(min, q);
                max = max(max, q);
                segmentsFound++;
            } else {
                break;
            }
        }
        return segmentsFound >= 3 ? new SegmentContainer(new LineSegment(min, max), min, max) : null;
    }

    private Point max(Point max, Point q) {
        return max != null && max.compareTo(q) > 0 ? max : q;
    }

    private Point min(Point min, Point q) {
        return min != null && min.compareTo(q) <= 0 ? min : q;

    }

    public int numberOfSegments() {
        return segmentCount;
    }

    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[segments.length];
        copy(segments, result);
        return result;
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

    private void validate(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (i != 0 && (points[i].slopeTo(points[i - 1]) == Double.NEGATIVE_INFINITY)) {
                throw new IllegalArgumentException("found duplicates");
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    private class SegmentContainer {
        private final LineSegment segment;
        private final Point max;
        private final Point min;

        public SegmentContainer(LineSegment segment, Point min, Point max) {
            this.segment = segment;
            this.min = min;
            this.max = max;
        }

        public boolean encompasses(SegmentContainer segmentContainer) {
            return this.min == segmentContainer.min || this.min.slopeTo(segmentContainer.min) == this.min.slopeTo(this.max);
        }
    }

}
