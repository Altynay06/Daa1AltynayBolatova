package dnc;

import java.awt.Point;
import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {

    public static class Pair {
        public Point p1, p2;
        public double distance;
    }

    public static class Point implements Comparable<Point> {
        public final int x;
        public final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public double distanceTo(Point that) {
            return Math.sqrt(Math.pow(this.x - that.x, 2) + Math.pow(this.y - that.y, 2));
        }

        @Override
        public int compareTo(Point that) {
            return Integer.compare(this.x, that.x);
        }
    }

    public static Pair findClosestPair(Point[] points, Metrics metrics) {
        Point[] pointsSortedByX = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsSortedByX);

        Point[] pointsSortedByY = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsSortedByY, Comparator.comparingInt(p -> p.y));

        return findClosest(pointsSortedByX, pointsSortedByY, metrics);
    }

    private static Pair findClosest(Point[] pointsByX, Point[] pointsByY, Metrics metrics) {
        int n = pointsByX.length;
        if (n <= 3) {
            return bruteForce(pointsByX, metrics);
        }

        int mid = n / 2;
        Point median = pointsByX[mid];

        Point[] leftX = Arrays.copyOfRange(pointsByX, 0, mid);
        Point[] rightX = Arrays.copyOfRange(pointsByX, mid, n);

        Point[] leftY = new Point[mid];
        Point[] rightY = new Point[n - mid];
        int leftYCount = 0;
        int rightYCount = 0;

        for (Point p : pointsByY) {
            metrics.incrementAllocations(8L);
            if (p.x < median.x || (p.x == median.x && p.y <= median.y)) {
                if (leftYCount < mid) {
                    leftY[leftYCount++] = p;
                } else {
                    rightY[rightYCount++] = p;
                }
            } else {
                rightY[rightYCount++] = p;
            }
        }

        if (leftYCount < mid) {
            for(int i = 0; i < rightYCount; i++) {
                if (rightY[i].x == median.x && rightY[i].y == median.y) {
                    leftY[leftYCount++] = rightY[i];
                    rightY[i] = rightY[--rightYCount];
                    rightY[rightYCount] = null;
                    break;
                }
            }
        }

        Pair leftPair = findClosest(leftX, leftY, metrics);
        Pair rightPair = findClosest(rightX, rightY, metrics);

        Pair bestPair = (leftPair.distance < rightPair.distance) ? leftPair : rightPair;
        double minDistance = bestPair.distance;

        Point[] strip = new Point[n];
        int stripCount = 0;
        for (Point p : pointsByY) {
            metrics.incrementComparison();
            if (Math.abs(p.x - median.x) < minDistance) {
                strip[stripCount++] = p;
            }
        }

        for (int i = 0; i < stripCount; i++) {
            for (int j = i + 1; j < stripCount && (strip[j].y - strip[i].y) < minDistance; j++) {
                double dist = strip[i].distanceTo(strip[j]);
                metrics.incrementComparison();
                if (dist < minDistance) {
                    minDistance = dist;
                    bestPair.p1 = strip[i];
                    bestPair.p2 = strip[j];
                    bestPair.distance = minDistance;
                }
            }
        }

        return bestPair;
    }

    public static Pair bruteForce(Point[] points, Metrics metrics) {
        Pair bestPair = new Pair();
        bestPair.distance = Double.POSITIVE_INFINITY;

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                metrics.incrementComparison();
                double dist = points[i].distanceTo(points[j]);
                if (dist < bestPair.distance) {
                    bestPair.distance = dist;
                    bestPair.p1 = points[i];
                    bestPair.p2 = points[j];
                }
            }
        }
        return bestPair;
    }
}