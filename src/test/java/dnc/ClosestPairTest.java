package dnc;

import org.junit.jupiter.api.Test;
import java.awt.Point;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class ClosestPairTest {

    private final Random random = new Random();

    private ClosestPair.Point[] generatePoints(int n) {
        ClosestPair.Point[] points = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new ClosestPair.Point(random.nextInt(10000), random.nextInt(10000));
        }
        return points;
    }

    private ClosestPair.Pair bruteForce(ClosestPair.Point[] points) {
        ClosestPair.Pair best = new ClosestPair.Pair();
        best.distance = Double.POSITIVE_INFINITY;

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double dist = points[i].distanceTo(points[j]);
                if (dist < best.distance) {
                    best.distance = dist;
                    best.p1 = points[i];
                    best.p2 = points[j];
                }
            }
        }
        return best;
    }

    @Test
    void testAgainstBruteForce() {
        int N = 200;
        ClosestPair.Point[] points = generatePoints(N);

        ClosestPair.Pair expected = bruteForce(points);

        ClosestPair.Pair actual = ClosestPair.findClosestPair(points, new Metrics());

        assertEquals(expected.distance, actual.distance, 1e-9,
                "Mismatch in closest distance for N=" + N);
    }
}