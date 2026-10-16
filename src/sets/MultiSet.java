package sets;

import java.util.Arrays;

public class MultiSet {
    private int[] counts;

    public MultiSet(int[] counts) {
        if (counts == null) {
            throw new IllegalArgumentException("Counts cannot be null");
        }
        this.counts = Arrays.copyOf(counts, counts.length);
    }

    public int size() {
        int total = 0;
        for (int count : counts) {
            total += count;
        }
        return total;
    }

    public int get(int index) {
        if (index < 0 || index >= counts.length) {
            return 0;
        }
        return counts[index];
    }

    public String display(Set universe) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < universe.length(); i++) {
            if (universe.contains(i)) {
                sb.append(counts[i]);
                if (i < universe.length() - 1 && universe.contains(i + 1)) {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }

    public MultiSet union(MultiSet other) {
        int[] result = new int[Math.max(this.counts.length, other.counts.length)];
        for (int i = 0; i < result.length; i++) {
            result[i] = Math.max(this.get(i), other.get(i));
        }
        return new MultiSet(result);
    }

    public MultiSet intersection(MultiSet other) {
        int[] result = new int[Math.max(this.counts.length, other.counts.length)];
        for (int i = 0; i < result.length; i++) {
            result[i] = Math.min(this.get(i), other.get(i));
        }
        return new MultiSet(result);
    }

    public MultiSet difference(MultiSet other) {
        int[] result = new int[Math.max(this.counts.length, other.counts.length)];
        for (int i = 0; i < result.length; i++) {
            result[i] = Math.max(0, this.get(i) - other.get(i));
        }
        return new MultiSet(result);
    }

    public MultiSet sum(MultiSet other) {
        int[] result = new int[Math.max(this.counts.length, other.counts.length)];
        for (int i = 0; i < result.length; i++) {
            result[i] = this.get(i) + other.get(i);
        }
        return new MultiSet(result);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > 0) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(i).append("=").append(counts[i]);
                first = false;
            }
        }
        sb.append("}");
        return sb.toString();
    }
}

