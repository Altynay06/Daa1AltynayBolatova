package dnc;

public class Metrics {
    public long comparisons = 0;
    public long allocations = 0;
    public int maxDepth = 0;

    public void incrementComparison() {
        comparisons++;
    }

    public void incrementAllocations(long count) {
        allocations += count;
    }

    public void updateDepth(int currentDepth) {
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }

    public void reset() {
        comparisons = 0;
        allocations = 0;
        maxDepth = 0;
    }

    @Override
    public String toString() {
        return "Comparisons: " + comparisons + ", Allocations: " + allocations + ", MaxDepth: " + maxDepth;
    }
}